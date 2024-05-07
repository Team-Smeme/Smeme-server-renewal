package com.smeem.api.auth.service;

import com.smeem.api.auth.jwt.UserAuthentication;
import com.smeem.api.auth.service.dto.request.SignInServiceRequest;
import com.smeem.api.auth.service.dto.response.SignInServiceResponse;
import com.smeem.api.member.service.MemberBadgeService;
import com.smeem.api.member.service.TrainingTimeService;
import com.smeem.domain.diary.adapter.DiaryDeleter;
import com.smeem.domain.member.adapter.member.MemberDeleter;
import com.smeem.domain.member.adapter.member.MemberFinder;
import com.smeem.domain.member.adapter.member.MemberSaver;
import com.smeem.external.oauth.exception.TokenException;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.SocialType;
import com.smeem.external.oauth.apple.AppleService;
import com.smeem.external.oauth.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.smeem.common.code.failure.AuthFailureCode.INVALID_TOKEN;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberFinder memberFinder;
    private final MemberSaver memberSaver;
    private final MemberDeleter memberDeleter;
    private final DiaryDeleter diaryDeleter;

    private final TokenService tokenService;
    private final AppleService appleService;
    private final KakaoService kakaoService;
    private final MemberBadgeService memberBadgeService;
    private final TrainingTimeService trainingTimeService;

    @Transactional
    public SignInServiceResponse signIn(final String socialAccessToken, final SignInServiceRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        val socialType = request.socialType();
        val socialId = socialLogin(socialType, socialAccessToken);
        val existMember = isMemberBySocialAndSocialId(socialType, socialId);
        if (!existMember) {
            val initialMember = Member.createInitialMember(socialType, socialId, request.fcmToken());
            memberSaver.save(initialMember);
        }
        val signedMember = getMemberBySocialAndSocialId(socialType, socialId);
        val isRegistered = nonNull(signedMember.getUsername());
        val hasTrainingPlan = nonNull(signedMember.getGoal());
        val smeemToken = tokenService.generateSmeemToken(UserAuthentication.create(signedMember.getId()));
        signedMember.updateRefreshToken(smeemToken.getRefreshToken());
        return SignInServiceResponse.of(smeemToken, isRegistered, hasTrainingPlan);
    }

    @Transactional
    public void signOut(final long memberId) {
        val member = memberFinder.findById(memberId);
        member.updateRefreshToken(null);
    }

    @Transactional
    public void withdraw(final long memberId) {
        val member = memberFinder.findById(memberId);
        diaryDeleter.deleteAllByMember(member);
        trainingTimeService.deleteAllByMember(member);
        memberBadgeService.deleteAllByMember(member);
        memberDeleter.deleteById(memberId);
    }

    private Member getMemberBySocialAndSocialId(final SocialType socialType, final String socialId) {
        return memberFinder.findBySocialAndSocialId(socialType, socialId);
    }

    private boolean isMemberBySocialAndSocialId(SocialType socialType, String socialId) {
        return memberFinder.existsBySocialAndSocialId(socialType, socialId);
    }

    private String socialLogin(SocialType socialType, final String socialAccessToken) {
        return switch (socialType) {
            case APPLE -> appleService.getAppleData(socialAccessToken);
            case KAKAO -> kakaoService.getKakaoData(socialAccessToken);
            default -> throw new TokenException(INVALID_TOKEN);
        };
    }
}
