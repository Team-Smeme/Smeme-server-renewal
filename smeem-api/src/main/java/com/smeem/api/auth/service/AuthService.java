package com.smeem.api.auth.service;

import com.smeem.api.auth.controller.dto.response.SignInResponse;
import com.smeem.api.auth.jwt.UserAuthentication;
import com.smeem.api.auth.service.dto.request.SignInServiceRequest;
import com.smeem.api.auth.service.dto.response.SignInServiceResponse;
import com.smeem.api.diary.service.DiaryService;
import com.smeem.api.member.service.MemberBadgeService;
import com.smeem.api.member.service.TrainingTimeService;
import com.smeem.common.exception.MemberException;
import com.smeem.common.exception.TokenException;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.SocialType;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.external.oauth.apple.AppleSignInService;
import com.smeem.external.oauth.kakao.KakaoSignInService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.smeem.common.code.failure.AuthFailureCode.INVALID_TOKEN;
import static com.smeem.common.code.failure.MemberFailureCode.INVALID_MEMBER;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;

    private final TokenService tokenService;
    private final AppleSignInService appleSignInService;
    private final KakaoSignInService kakaoSignInService;
    private final MemberBadgeService memberBadgeService;
    private final DiaryService diaryService;
    private final TrainingTimeService trainingTimeService;

    @Transactional
    public SignInServiceResponse signIn(final String socialAccessToken, final SignInServiceRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        val socialType = request.socialType();
        val socialId = socialLogin(socialType, socialAccessToken);
        val existMember = isMemberBySocialAndSocialId(socialType, socialId);
        if (!existMember) {
            val initialMember = Member.createInitialMember(socialType, socialId, request.fcmToken());
            memberRepository.save(initialMember);
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
        Member member = get(memberId);
        member.updateRefreshToken(null);
    }

    @Transactional
    public void withdraw(final long memberId) {
        Member member = get(memberId);
        diaryService.deleteAllByMember(member);
        trainingTimeService.deleteAllByMember(member);
        memberBadgeService.deleteAllByMember(member);
        memberRepository.deleteById(memberId);
    }

    private Member get(final long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(INVALID_MEMBER));
    }

    private Member getMemberBySocialAndSocialId(SocialType socialType, String socialId) {
        return memberRepository.findBySocialAndSocialId(socialType, socialId)
                .orElseThrow(() -> new MemberException(INVALID_MEMBER));
    }

    private boolean isMemberBySocialAndSocialId(SocialType socialType, String socialId) {
        return memberRepository.existsBySocialAndSocialId(socialType, socialId);
    }

    private String socialLogin(SocialType socialType, String socialAccessToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return switch (socialType.toString()) {
            case "APPLE" -> appleSignInService.getAppleData(socialAccessToken);
            case "KAKAO" -> kakaoSignInService.getKakaoData(socialAccessToken);
            default -> throw new TokenException(INVALID_TOKEN);
        };
    }
}
