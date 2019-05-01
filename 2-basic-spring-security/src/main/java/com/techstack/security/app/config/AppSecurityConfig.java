/**
 * 
 */
package com.techstack.security.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * In order to override Spring Security Defaults, we can use custom class.
 * 
 * @author Karthikeyan N
 *
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * TIP: Inorder to bring this method "userDetailsService"
	 * RightClick on the editor block -> Source -> Override / Implement methods 
	 * -> select userDetailsService 
	 */
	
	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		
		/**
		 * UserDetails is an Interface which intern implements User class inside Spring Security
		 */
		
		List<UserDetails> users = new ArrayList();
		users.add(User.withDefaultPasswordEncoder()
					  .username("karthi")
					  .password("admin")
					  .roles("USER")
					  .build());
		
		users.add(User.withDefaultPasswordEncoder()
					  .username("pascal")
					  .password("test1")
					  .roles("MANAGER")
					  .build());
		
		return new InMemoryUserDetailsManager(users);
	}
	
	

}
