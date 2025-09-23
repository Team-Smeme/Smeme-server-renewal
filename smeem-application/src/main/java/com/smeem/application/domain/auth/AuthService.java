package com.smeem.application.domain.auth;

import com.smeem.application.domain.member.Member;
import com.smeem.application.port.input.AuthUseCase;
import com.smeem.application.port.input.dto.request.auth.SignInRequest;
import com.smeem.application.port.input.dto.request.member.WithdrawRequest;
import com.smeem.application.port.input.dto.response.auth.GenerateTokenResponse;
import com.smeem.application.port.input.dto.response.auth.SignInResponse;
import com.smeem.application.port.output.oauth.OauthPort;
import com.smeem.application.port.output.persistence.BookmarkPort;
import com.smeem.application.port.output.persistence.CorrectionPort;
import com.smeem.application.port.output.persistence.MemberPort;
import com.smeem.common.logger.HookLogger;
import com.smeem.common.logger.LoggingMessage;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements AuthUseCase {

    private final MemberPort memberPort;
    private final CorrectionPort correctionPort;
    private final BookmarkPort bookmarkPort;
    private final OauthPort oauthPort;
    private final TokenGenerator tokenGenerator;
    private final HookLogger hookLogger;

    @Transactional
    public SignInResponse signIn(String socialAccessToken, SignInRequest request) {
        val social = oauthPort.login(request.socialType(), socialAccessToken);
        val signedMember = signIn(social, request);
        signedMember.updateTokenInLogin(
                tokenGenerator.generateRefreshToken(signedMember.getId()),
                request.fcmToken());
        memberPort.update(signedMember);
        return SignInResponse.of(tokenGenerator.generateAccessToken(signedMember.getId()), signedMember);
    }

    private Member signIn(Member.Social social, SignInRequest request) {
        return memberPort.findBySocial(social)
                .orElseGet(() -> memberPort.save(request.toDomain(social.socialId())));
    }

    @Transactional
    public GenerateTokenResponse generateToken(long memberId) {
        val member = memberPort.findById(memberId);
        return GenerateTokenResponse.of(tokenGenerator.generateAccessToken(member.getId()), member.getRefreshToken());
    }

    @Transactional
    public void signOut(long memberId) {
        val member = memberPort.findById(memberId);
        memberPort.update(member.emptyRefreshToken());
    }

    @Transactional
    public void withdraw(long memberId, WithdrawRequest request) {
        bookmarkPort.deleteByMemberId(memberId);
        correctionPort.deleteByMember(memberId);
        memberPort.deleteById(memberId);
        createWithdrawal(request);
    }

    private void createWithdrawal(WithdrawRequest request) {
        if (request != null) {
            memberPort.saveWithdraw(request.toDomain());
            hookLogger.send(LoggingMessage.withdraw(
                    String.format("[%s] %s", request.withdrawType(), request.withdrawType().getDescription()),
                    request.reason()));
        }
    }
}
