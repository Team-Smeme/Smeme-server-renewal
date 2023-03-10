package com.smeme.server.controllers;

import com.smeme.server.dtos.scrap.ScrapRequestDto;
import com.smeme.server.dtos.scrap.ScrapResponseDto;
import com.smeme.server.dtos.scrap.ScrapsFindResponseDto;
import com.smeme.server.services.ScrapService;
import com.smeme.server.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/scraps")
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping
    public ResponseEntity<ApiResponse> createScrap(
        Principal principal, @RequestBody ScrapRequestDto scrapRequestDto) {
        ScrapResponseDto scrapResponseDto = scrapService
            .createScrap(Long.valueOf(principal.getName()), scrapRequestDto);

        ApiResponse apiResponse = ApiResponse.of(
                CREATED.value(), true, "스크랩 저장 성공", scrapResponseDto);

        return new ResponseEntity<>(apiResponse, CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findScrapsByUser(Principal principal) {
        ScrapsFindResponseDto scrapsResponseDto = scrapService
            .findScrapsByUser(Long.valueOf(principal.getName()));

        ApiResponse apiResponse = ApiResponse.of(
                OK.value(), true, "스크랩 리스트 조회 성공", scrapsResponseDto);

        return new ResponseEntity<>(apiResponse, OK);
    }

    @DeleteMapping("/{scrapId}")
    public ResponseEntity<ApiResponse> deleteScrap(@PathVariable("scrapId") String scrapId) {
        scrapService.deleteScrap(Long.parseLong(scrapId));

        ApiResponse apiResponse = ApiResponse.of(OK.value(), true, "스크랩 삭제 성공");

        return new ResponseEntity<>(apiResponse, OK);
    }
}
