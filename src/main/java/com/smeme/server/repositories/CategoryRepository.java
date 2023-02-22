package com.smeme.server.repositories;

import java.util.List;

import com.smeme.server.models.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
