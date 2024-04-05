package com.example.demo.common;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class UserWithNickname extends User {
	private final String nickname;

	public UserWithNickname(String username, String password, String nickname) {
		super(username, password, true, true, true, true, AuthorityUtils.createAuthorityList("ADMIN"));
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}
}