package com.example.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

	public AppUser findByEmail(String email);
}
