package com.smeem.application.port.input.dto.response.bookmark;

import com.smeem.application.domain.bookmark.Bookmark;
import com.smeem.application.domain.member.Member;
import com.smeem.application.port.output.web.scrap.ScrapInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record BookmarkResponse(
        @Schema(description = "스크랩 대상 정보")
        ScrapResponse scrapContent,

        @Schema(description = "표현식", example = "Pissed off")
        String expression,

        @Schema(description = "번역된 표현식", example = "엄청 화나다")
        String translatedExpression,

        @Schema(description = "금일 AI 스크랩한 횟수", example = "1")
        int scrapedCountPerDay
) {

        public static BookmarkResponse from(Bookmark bookmark, Member member) {
                return BookmarkResponse.builder()
                        .scrapContent(ScrapResponse.from(bookmark))
                        .expression(bookmark.getExpression())
                        .translatedExpression(bookmark.getTranslatedExpression())
                        .scrapedCountPerDay(member.getScrapedCountPerDay())
                        .build();
        }

        public static BookmarkResponse fallback(@NonNull ScrapInfo scrapInfo, int scrapedCountPerDay) {
                return BookmarkResponse.builder()
                        .scrapContent(ScrapResponse.from(scrapInfo))
                        .scrapedCountPerDay(scrapedCountPerDay)
                        .build();
        }
}
