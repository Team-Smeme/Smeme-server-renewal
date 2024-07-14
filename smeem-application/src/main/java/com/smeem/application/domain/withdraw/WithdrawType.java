package com.smeem.application.domain.withdraw;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WithdrawType {
    INSTABILITY("이용이 어렵고 서비스가 불안정해요."),
    LACK("일기 작성을 도와주는 기능이 부족해요."),
    BOTHER("일기 쓰기가 귀찮아요."),
    COMPARISON("다른 앱이 사용하기 더 편해요."),
    ETC("기타 의견"),
    ;

    private final String description;
}
