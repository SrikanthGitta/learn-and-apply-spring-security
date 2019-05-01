/**
 * 
 */
package com.techstack.security.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Karthikeyan N
 *
 */
@RestController
@RequestMapping("/")
public class HomeController {

	@GetMapping
	public String getGreetings() {
		return "Hello World!";
	}
}
