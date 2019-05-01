/**
 * 
 */
package com.techstack.security.app.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techstack.security.app.entity.User;
import com.techstack.security.app.entity.UserPrincipal;
import com.techstack.security.app.repository.UserRepository;

/**
 * @author Karthikeyan N
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		if(Objects.isNull(user))
			throw new UsernameNotFoundException("Given user not found in database");
		
		return new UserPrincipal(user);
	}

}
