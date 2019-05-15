# LearnSpringSecurity
This repository contains various Spring Boot + Security related concepts and techniques.

1. basic-spring-security - you will learn 
	1. How to add spring security dependency? 
	2. How to use Spring default password?
	3. How to override the default username and password?

2. basic-spring-security - you will learn 
	1. How to use ```InMemoryUserDetailsManager``` to store your username and password in order to anthenticate the given user.
	
3. basic-spring-security - you will learn 
	1. How to use MySQL database to validate username and password (plain text) using ```NoOpPasswordEncoder```.
	2. How to use MySQL database to validate username and password (bcrypt encoder) using ```BCryptPasswordEncoder```
	3. How to use ```DaoAuthenticationProvider``` and ```UserDetailsService```

4. basic-spring-security - you will learn 
	1. How to customize Spring security to use your own Login Form implementation to validate the given username and password instead spring provided default implementation.
	
5. basic-spring-security - you will learn
	1. How to use Spring Boot + Security + OAuth2 token to validate the user account using Google API SSO.
