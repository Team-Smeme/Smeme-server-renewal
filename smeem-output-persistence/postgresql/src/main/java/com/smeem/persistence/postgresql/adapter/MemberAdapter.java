package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.withdraw.Withdraw;
import com.smeem.application.port.output.persistence.MemberPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.persistence.postgresql.persistence.entity.MemberEntity;
import com.smeem.persistence.postgresql.persistence.entity.WithdrawEntity;
import com.smeem.persistence.postgresql.persistence.repository.*;
import com.smeem.persistence.postgresql.persistence.repository.diary.CorrectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberAdapter implements MemberPort {
    private final CorrectionRepository correctionRepository;
    private final MemberRepository memberRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final DeletedDiaryRepository deletedDiaryRepository;
    private final DiaryRepository diaryRepository;
    private final TrainingTimeRepository trainingTimeRepository;
    private final WithdrawRepository withdrawRepository;

    @Override
    public Optional<Member> findBySocial(Member.Social social) {
        return memberRepository.findBySocialTypeAndSocialId(social.socialType(), social.socialId())
                .map(MemberEntity::toDomain);
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(MemberEntity.of(member)).toDomain();
    }

    @Override
    public void update(Member member) {
        val foundMember = find(member.getId());
        foundMember.update(member);
    }

    @Override
    public Member findById(long id) {
        return find(id).toDomain();
    }

    @Override
    public void deleteById(long id) {
        memberBadgeRepository.deleteByMemberId(id);
        deletedDiaryRepository.deleteByMemberId(id);
        diaryRepository.deleteByMemberId(id);
        trainingTimeRepository.deleteByMemberId(id);
        memberRepository.deleteById(id);
        correctionRepository.deleteByMemberId(id);
    }

    @Override
    public boolean isExistByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Override
    public int countAll() {
        return (int)memberRepository.count();
    }

    @Override
    public List<Member> findByTrainingTime(LocalDateTime trainingTime) {
        return memberRepository.findByTrainingTime(trainingTime).stream().map(MemberEntity::toDomain).toList();
    }

    @Override
    public void saveWithdraw(Withdraw withdraw) {
        withdrawRepository.save(new WithdrawEntity(withdraw));
    }

    private MemberEntity find(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, "(Member ID: " + id + ")"));
    }
}
