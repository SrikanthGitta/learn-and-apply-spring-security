package com.techstack.repository;

import org.springframework.data.repository.CrudRepository;

import com.techstack.model.MyAppUser;

public interface UserRepository extends CrudRepository<MyAppUser, String> {

	MyAppUser findByUsername(String username);
	MyAppUser findByEmail(String email);
	
}
