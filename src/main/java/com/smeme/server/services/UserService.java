package com.smeme.server.services;

import org.springframework.stereotype.Service;

import com.smeme.server.models.User;
import com.smeme.server.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User getUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
	}
}
