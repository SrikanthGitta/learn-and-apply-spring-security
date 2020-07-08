package com.techstack.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.repository.UserRepository;



public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private UserRepository repository;
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return email != null && repository.findByEmail(email) == null ;
	}

}
