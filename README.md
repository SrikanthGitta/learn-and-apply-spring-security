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

___________________________________________________________________________________________________________

Spring Boot Security
====================

Authentication
	- HTTP Authentication
	- Forms Authentication
	- Certificate
	- Tokens (JWT)

Authorization 
	Privileges / Authorities
	Roles
	
- In Spring Security, granted authorities and roles are a form of expressing a privilege/permission for an authenticated user
 
- We express them using plain names
	- Granted Authorities		- Roles
		(Small action based)		(Larger scope)
		READ_PROFILE				ROLE_ADMIN
		EDIT_PROFILE				ROLE_USER
		DELETE_PROFILE				ROLE_SALES
		ACCESS_PUBLIC_API			ROLE_MANAGEMENT
		
___________________________________________________________________________________________________________

HTTP Basic Authentication

	In the context of HTTP, basic authentication is the process for browser to request a username and password when making a request in order to authentify the user
	
	Client/
	Browser  ----------- Get /home --------------------------> Server
	
			 401 Unauthorized
			 WWW-Authenticate: Basic realm="localhost"
			 <------------------------------------------------ ==> this step it will show a popup dialog to enter username and password
			 
			 Authorization: Basic sgsgasgasgaswtrw25252141=
			 ----------- Get /home -------------------------->
			 
			 <------------200 OK-------------------------------
				return some resource from server
				

	Encoding of username and password
		
		username: karthi
		password: testing
		
		- Name and Password are combined (Ex: karthi:testing)
		- Browser will encode this to Base64 format (Ex: Ssgsgsoj252522sgsS)
		- Transmit in HTTP header (Ex: Authorization:Basic Ssgsgsoj252522sgsS)
		
	Stuff you need to know about Basic Authentication
		- It is very simple. It doesn't require cookie, session identifiers, or login page
		- Transmitted credentials are not encrypted. They are encoded with Base64 in transit, but not encrypted or hashed in any way.
		- Basic Authentication is typically used in conjunction with HTTPS to provide confidentiallity.
			- In this case, user can't grab the given username and password. Becuase, it uses HTTPS layer.
		- HTTP does not provide a method for a web server to instruct the client to "log out"
		- This authentication mechanism is not handled by your app, but the browser
		
		- Simpler definition:
			- HTTP Basic is the simplest form of authentication. In conjunction with SSL, it is considered the bare minimum for protecting non-sensitive resources.
			
			- Otherwise, go with a more secure solution.
			
___________________________________________________________________________________________________________

Form Based Authentication

	It is the process of authenticating a user by presenting a custom HTML page that will collect credentials and by directing the authentication responsibility to the web application that collects the form data.
	

	Login:
	
	Client ---------------- GET /home ---------------------------> Server
	
		   <-----If not authenticated REDIRECT to login page -----	
			 Login form will be displayed
	
		   
		   ------------- POST form data (username + password) ---->	[Session][Created]
		   
		   <----- If OK create SESSION ID and return auth cookie --

		   
		   -------------- GET /employees/reports/all -------------->
		   
		   <---- 200 OK --------------------------------------------
		   
	
	
	Logout:
	
	Client ---------------- GET /logout ---------------------------> Server
	
		   <------- SESSION is invalidated & Redirected to /login -- [Session][removed]
		   
		   
	Stuff you need to know about Forms Authentication
		- The application is responsible for dealing with form data and performing the actual authorization phase.
		
		- Infrastructure handled by Spring Secutrity.
		
		- It is the most widespread form of authentication, well sutied for self-contained apps.
		
		- The user credentials are conveyed in the clear to the web application, so use SSL to keep them safe in transit.
		
		- This technique is inherently phishable, so use SSL and certificates from trusted organizations.
		
		- Not suited for public REST endpoints given to third party apps or customers. Only self-contained.
		
	Simpler definition:
		Forms Authentication is the most used method for authorizing users and works like a charm for self-contained apps that do not expose public API's to other parties.

___________________________________________________________________________________________________________

Token Based Authentication using JWT (JSON Web Token)

	JSON Web Token (JWT) is a compact and safe way to transmit data between two parties. The information can be trusted because it is digitally signed.
	
	Login:
	
	|------------------															|-------------------|
	|				  |--- User sign in (Credentials, Facebook, Google, etc) -->|	Auth Server		|
	|				  |															|					|
	| Client (Outside)|<--- Authenticated. Token {JWT} created and returned ----|___________________|
	| your app domain)|
	|				  |															|-------------------|
	|				  |--->GET /report/all HEADER Authorization: Bearer {JWT}-->|	Application		|
	|				  |															|		(REST)		|
	|				  |<------------- 200 OK -----------------------------------|					|
	|				  |															|					|
	|				  |--->POST /product HEADER Authorization: Bearer {JWT} --->|					|
	|				  |															|					|
	|				  |<------------- 200 OK -----------------------------------|___________________|
	-------------------
	
	
	JWT Structure:
		JSON Web Token consist of three parts separated by dots(.)
			1. Header
			2. Payload (Data)
			3. Signature
			
		Example: hhhh.ppppppp.ssssssss
		
		Refer: https://jwt.io
		
	When should you use them?
	
		Mobile  ----------- {JWT} ------ www.myapp.com
											Auth Server +
											REST API
		WEB	------------ {JWT} ---------
		
		3rd Party1 ----- {JWT} ---------
		
		3rd Party2 ----- {JWT} ---------
		
___________________________________________________________________________________________________________

SSL & HTTPS

	HTTP is a combination of HTTP plus SSL security layer on top of it. HTTPS is just HTTP that delivers data securely between endpoints.
	
	HTTP is not Secure
	------------------
	
	Client/Browser  <-------- http://mymailserver.com --------> MailServer
								john.does
								password

	In this above HTTP transaction, some malicious user can try to intercept/hack the given username and password.

	HTTPS is secure
	---------------
	
	Client/Browser  <-------- https://mymailserver.com --------> MailServer
								john.does
								password
								
	In this method, the malicious user can't see the given information. It will be secure.
	
	How HTTPS works?
	---------------- 
		This is where SSL Certificate come in to play.
		
		Security in HTTPS communication is ensured by using SSL certificates
		
		Self-Signed (Created by you) - Good for development
		
		Signed by Trusted Authority (Comodo, Symantec, DigiCert, etc.) - For Production
		
		Flow
		----
		
		Broswer/
		Client Machine ----------- HTTPS request ---------------------> Server
					   
					   <--- Server sends certificate with public key--
					   
					   
							SSL Verification. If OK browser sends
							back session key
						----------------------------------------------->
						
		<------------------------------------------------------------------->
			Secure communication by encrypting all data with session key
			
	SSL is mandatory for any web application, regardless of the chosen security config.
