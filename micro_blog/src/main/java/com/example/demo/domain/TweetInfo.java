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
public class TweetInfo {
	@Id
	private Integer id;

	private Integer userId;

	private String nickname;

	private String body;
	
	private Boolean isFavorite;
	
	private String createdAt;
}
