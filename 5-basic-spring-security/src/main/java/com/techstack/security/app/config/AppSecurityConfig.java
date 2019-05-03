/**
 * 
 */
package com.techstack.security.app.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * In order to override Spring Security Defaults, we can use custom class.
 * 
 * @author Karthikeyan N
 *
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	//SSO - Single Sign-On
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()
			.authorizeRequests().antMatchers("/login").permitAll()
			.anyRequest().authenticated();
	}
	
}
