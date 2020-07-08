package com.techstack.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.repository.UserRepository;




public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	private UserRepository repository;
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return username != null && repository.findByUsername(username) == null ;
	}

}
