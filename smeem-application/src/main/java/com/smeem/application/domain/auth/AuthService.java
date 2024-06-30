package com.smeem.application.domain.auth;

import com.smeem.application.domain.member.Member;
import com.smeem.application.port.input.AuthUseCase;
import com.smeem.application.port.input.dto.request.auth.SignInRequest;
import com.smeem.application.port.input.dto.response.auth.GenerateTokenResponse;
import com.smeem.application.port.input.dto.response.auth.SignInResponse;
import com.smeem.application.port.output.external.SocialUseCase;
import com.smeem.application.port.output.persistence.MemberPort;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements AuthUseCase {
    private final MemberPort memberPort;
    private final SocialUseCase socialUseCase;
    private final TokenGenerator tokenGenerator;

    @Transactional
    public SignInResponse signIn(String socialAccessToken, SignInRequest request) {
        val social = socialUseCase.login(request.socialType(), socialAccessToken);
        val signedMember = signIn(social, request);
        memberPort.update(signedMember.updateSmeemToken(tokenGenerator.generateRefreshToken(signedMember.getId())));
        return SignInResponse.of(tokenGenerator.generateAccessToken(signedMember.getId()), signedMember);
    }

    private Member signIn(Member.Social social, SignInRequest request) {
        return memberPort.findBySocial(social).orElse(memberPort.save(request.toDomain(social.socialId())));
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
    public void withdraw(long memberId) {
        memberPort.deleteById(memberId);
    }
}
