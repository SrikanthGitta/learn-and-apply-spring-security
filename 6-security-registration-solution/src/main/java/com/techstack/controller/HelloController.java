package com.techstack.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	
	@Value("${server.ssl.key-store-password}")
	private String kspwd;
	
	@GetMapping("/hello")
	public String sayHello(Model model, @RequestParam(defaultValue = "Karthi" ,required = false)String name) {
		System.out.println("kspwd: "+kspwd);
		model.addAttribute("name", name);
		return "hello";
	}
	
	
	@GetMapping("/home")
	public String home() {
		return "hello";
	}

}
