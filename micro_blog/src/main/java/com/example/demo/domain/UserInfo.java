package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
public class UserInfo {
	@Id
	private Integer id;

	private String nickname;

	private Integer followCount;
	
	private Integer followerCount;
	
	private Boolean isFollow;
}

