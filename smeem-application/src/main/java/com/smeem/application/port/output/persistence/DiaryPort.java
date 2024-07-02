package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.diary.Diary;

public interface DiaryPort {
    int countByMember(long memberId);
    Diary save(Diary diary);
    boolean isExistByMemberAndYesterday(long memberId);
}
