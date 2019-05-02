/**
 * 
 */
package com.techstack.security.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Karthikeyan N
 *
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "home.jsp";
	}

	@RequestMapping("/login")
	public String loginPage() {
		return "login.jsp";
	}

	@RequestMapping("/logout-success")
	public String logoutPage() {
		return "logout.jsp";
	}
}
