package com.techstack.repository;

import org.springframework.data.repository.CrudRepository;

import com.techstack.model.Verification;

public interface VerificationCodeRepository extends CrudRepository<Verification, Long>{
	
	Verification findByUsername(String username);
	boolean existsByUsername(String username);
}
