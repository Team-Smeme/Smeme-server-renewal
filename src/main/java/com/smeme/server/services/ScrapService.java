package com.smeme.server.services;

import com.smeme.server.dtos.scrap.*;
import com.smeme.server.models.Diary;
import com.smeme.server.models.Scrap;
import com.smeme.server.models.User;
import com.smeme.server.repositories.DiaryRepository;
import com.smeme.server.repositories.ScrapRepository;
import com.smeme.server.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Transactional
    public ScrapResponseDto createScrap(ScrapRequestDto scrapRequestDto) {
        User user = userRepository.findById(scrapRequestDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        Diary diary = diaryRepository.findById(scrapRequestDto.diaryId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 일기입니다."));

        Scrap scrap = scrapRepository.save(scrapRequestDto.toEntity(user, diary));

        return ScrapResponseDto.from(scrap);
    }

    @Transactional(readOnly = true)
    public ScrapsFindResponseDto findScrapsByUser(ScrapFindRequestDto scrapRequestDto) {
        User user = userRepository.findById(scrapRequestDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        List<ScrapFindResponseDto> scraps = new ArrayList<>();
        scrapRepository.findByUser(user).forEach(scrap -> scraps.add(ScrapFindResponseDto.from(scrap)));

        return ScrapsFindResponseDto.builder().scraps(scraps).build();
    }

    @Transactional
    public void deleteScrap(Long scrapId) {
        scrapRepository.findById(scrapId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 스크랩입니다."));

        scrapRepository.deleteById(scrapId);
    }
}
