package com.blog.services.dto;

import com.blog.entity.User;

public class LoginDto {

	private User user;
	private boolean isAuthorized;
	private String error;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isAuthorized() {
		return isAuthorized;
	}

	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
