package com.techstack.events;

import org.springframework.context.ApplicationEvent;

import com.techstack.model.MyAppUser;



public class UserRegistrationEvent extends ApplicationEvent {

	private static final long serialVersionUID = -4113549487933175429L;
	private final MyAppUser user;
	
	public UserRegistrationEvent(MyAppUser user) {
		super(user);
		this.user=user;
	}

	public MyAppUser getUser() {
		return user;
	}
	
	

}
