package com.smeme.server.controllers;

import com.smeme.server.dtos.scrap.ScrapRequestDto;
import com.smeme.server.dtos.scrap.ScrapResponseDto;
import com.smeme.server.services.ScrapService;
import com.smeme.server.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/scraps")
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping
    public ResponseEntity<ApiResponse> createScrap(@RequestBody ScrapRequestDto scrapRequestDto) {
        ScrapResponseDto scrapResponseDto = scrapService.createScrap(scrapRequestDto);

        System.out.println(scrapResponseDto.toString());

        ApiResponse apiResponse = ApiResponse.of(
                CREATED.value(), true, "스크랩 저장 성공", scrapResponseDto);

        return new ResponseEntity<>(apiResponse, CREATED);
    }
}
