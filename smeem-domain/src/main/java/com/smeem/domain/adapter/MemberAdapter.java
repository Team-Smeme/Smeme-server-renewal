package com.smeem.domain.adapter;

import com.smeem.api.domain.auth.vo.Social;
import com.smeem.api.domain.member.Member;
import com.smeem.api.port.output.persistence.member.MemberDeleter;
import com.smeem.api.port.output.persistence.member.MemberFinder;
import com.smeem.api.port.output.persistence.member.MemberSaver;
import com.smeem.api.port.output.persistence.member.MemberUpdater;
import com.smeem.common.exception.SmeemException;
import com.smeem.domain.diary.repository.DiaryRepository;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import com.smeem.domain.persistence.repository.member.MemberRepository;
import com.smeem.domain.persistence.entity.MemberEntity;
import com.smeem.domain.training.repository.TrainingTimeRepository;
import com.smeem.domain.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAdapter implements MemberSaver, MemberFinder, MemberUpdater, MemberDeleter {

    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final TrainingTimeRepository trainingTimeRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final VisitRepository visitRepository;

    @Override
    @Transactional
    public Member save(Member member) {
        return memberRepository.save(new MemberEntity(member)).toDomain();
    }

    @Override
    public Optional<Member> findBySocial(Social social) {
        return memberRepository.findBySocialAndSocialId(social.socialType(), social.socialId())
                .map(MemberEntity::toDomain);
    }

    @Override
    public Member findById(long id) {
        return find(id).toDomain();
    }

    @Override
    @Transactional
    public Member update(Member member) {
        val foundMember = find(member.id());
        foundMember.update(member);
        return member;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        val foundMember = find(id);
        diaryRepository.deleteAllByMember(foundMember);
        trainingTimeRepository.deleteAllByMember(foundMember);
        memberBadgeRepository.deleteAllByMember(foundMember);
        visitRepository.deleteAllByMember(foundMember);
        memberRepository.delete(foundMember);
    }

    private MemberEntity find(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new SmeemException(404, "Not found ${id}"));
    }
}
