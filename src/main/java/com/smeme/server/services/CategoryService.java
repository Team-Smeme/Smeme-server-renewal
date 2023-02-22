package com.smeme.server.services;

import static org.springframework.data.domain.Sort.Direction.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smeme.server.dtos.category.CategoriesResponseDto;
import com.smeme.server.dtos.category.CategoryResponseDto;
import com.smeme.server.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	@Transactional
	public CategoriesResponseDto findAllCategories() {
		List<CategoryResponseDto> categories = new ArrayList<>();

		categoryRepository.findAll(Sort.by(ASC, "id"))
			.stream().filter(category -> category.getId() != 0)
			.forEach(category -> categories.add(CategoryResponseDto.from(category)));

		return CategoriesResponseDto.builder().categories(categories).build();
	}
}
