package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {

	@JsonProperty
	private String username;
	@JsonProperty
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@JsonProperty
	private String passwordConfirm;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
