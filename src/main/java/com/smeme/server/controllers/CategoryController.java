package com.smeme.server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.dtos.category.CategoriesResponseDto;
import com.smeme.server.services.CategoryService;
import com.smeme.server.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<ApiResponse> findAllCategories() {
		CategoriesResponseDto response = categoryService.findAllCategories();

		ApiResponse apiResponse = ApiResponse.of(
			HttpStatus.OK.value(), true, "카테고리 조회 성공", response);

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
