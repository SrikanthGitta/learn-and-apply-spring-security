package com.techstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techstack.model.MyAppUser;

import com.techstack.repository.UserRepository;


@Service
public class UserRegistrationServiceImpl implements UserRegistrationService{
	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private  PasswordEncoder encoder;
	
	@Override
	public void createUser(MyAppUser user) {
		
		userRepository.save(user);
		
		System.out.println("***** User Saved  *****");
	}

	

}