package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.UserDto;

@Repository
public interface UserRepository extends JpaRepository<UserDto, String>{
	Optional<UserDto> findByUserId(String userId);
}
