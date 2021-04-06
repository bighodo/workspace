package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.PartyDto;

public interface PartyRepository extends JpaRepository<PartyDto, String>{

}
