/**
 * 
 */
package com.techstack.security.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techstack.security.app.entity.User;

/**
 * @author Karthikeyan N
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
