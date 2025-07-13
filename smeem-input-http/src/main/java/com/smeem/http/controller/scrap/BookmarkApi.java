package com.smeem.http.controller.scrap;

import com.smeem.application.port.input.BookmarkUseCase;
import com.smeem.application.port.input.dto.request.bookmark.BookmarkFallbackRequest;
import com.smeem.http.controller.docs.BookmarkApiDocs;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.request.bookmark.BookmarkRequest;
import com.smeem.application.port.input.dto.response.bookmark.BookmarkResponse;
import com.smeem.http.security.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.smeem.application.domain.generic.SmeemMessage.SUCCESS_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/bookmark")
public class BookmarkApi implements BookmarkApiDocs {

    private final BookmarkUseCase bookmarkUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SmeemResponse<BookmarkResponse> bookmark(
            @MemberId long memberId,
            @RequestBody BookmarkRequest request
    ) {
        return SmeemResponse.of(bookmarkUseCase.bookmark(memberId, request), SUCCESS_REQUEST);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/fallback")
    public SmeemResponse<BookmarkResponse> bookmarkFallback(
            @MemberId long memberId,
            @RequestBody BookmarkFallbackRequest request
    ) {
        return SmeemResponse.of(bookmarkUseCase.bookmarkFallback(memberId, request), SUCCESS_REQUEST);
    }
}
