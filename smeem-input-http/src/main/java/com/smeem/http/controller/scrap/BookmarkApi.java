package com.smeem.http.controller.scrap;

import com.smeem.application.port.input.bookmark.BookmarkCommandUseCase;
import com.smeem.application.port.input.bookmark.BookmarkQueryUseCase;
import com.smeem.application.port.input.bookmark.dto.*;
import com.smeem.http.controller.docs.BookmarkApiDocs;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.http.security.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.smeem.application.domain.generic.SmeemMessage.SUCCESS_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/bookmarks")
public class BookmarkApi implements BookmarkApiDocs {

    private final BookmarkCommandUseCase bookmarkCommandUseCase;
    private final BookmarkQueryUseCase bookmarkQueryUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SmeemResponse<BookmarkResponse> bookmark(
            @MemberId long memberId,
            @RequestBody BookmarkRequest request
    ) {
        return SmeemResponse.of(bookmarkCommandUseCase.bookmark(memberId, request), SUCCESS_REQUEST);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/fallback")
    public SmeemResponse<BookmarkResponse> bookmarkFallback(
            @MemberId long memberId,
            @RequestBody BookmarkFallbackRequest request
    ) {
        return SmeemResponse.of(bookmarkCommandUseCase.bookmarkFallback(memberId, request), SUCCESS_REQUEST);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/detail/{bookmarkId}")
    public SmeemResponse<BookmarkDetailResponse> getBookmarkDetail(
            @MemberId long memberId,
            @PathVariable long bookmarkId
    ) {
        return SmeemResponse.of(bookmarkQueryUseCase.getBookmarkDetail(memberId, bookmarkId), SUCCESS_REQUEST);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public SmeemResponse<BookmarkListResponse> getBookmarks(@MemberId long memberId) {
        return SmeemResponse.of(bookmarkQueryUseCase.getBookmarks(memberId), SUCCESS_REQUEST);
    }
}
