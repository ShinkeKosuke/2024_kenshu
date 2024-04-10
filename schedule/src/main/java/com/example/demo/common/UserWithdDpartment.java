package com.example.demo.common;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class UserWithdDpartment extends User {
	private final String userName;
	private final String departmentName;

	public UserWithdDpartment(String username, String password, String userName, String departmentName) {
		super(username, password, true, true, true, true, AuthorityUtils.createAuthorityList("ADMIN"));
		this.userName = userName;
		this.departmentName = departmentName;
	}

	public String getNickname() {
		return userName;
	}

	public String getDepartmentName() {
		return departmentName;
	}
}