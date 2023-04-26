package com.smeme.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.util.ApiResponse;

@RestController
@RequestMapping("/api/v2/test")
public class TestController {

	@GetMapping
	public ResponseEntity<ApiResponse> test() {
		return ResponseEntity.ok(ApiResponse.success(true, "server connect"));
	}
}
