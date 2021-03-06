package com.techstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techstack.model.MyAppUser;
import com.techstack.repository.UserRepository;
import com.techstack.service.VerificationService;



@Controller

public class VerificationController {

	@Autowired
	private  VerificationService verificationService;
	@Autowired
	private  UserRepository userRepository;
	
	@GetMapping("/verify/email")
	public String verifyEmail(Model model, @RequestParam Long id) {
		String username = verificationService.getUsernameForId(id);
		if(username != null) {
			MyAppUser user = userRepository.findByUsername(username);
			user.setEnabled(true);
			userRepository.save(user);
			model.addAttribute("verified", true);
		}
		
		
		return "login";
	}
	
}
