package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer>{
	public List<Tweet> findAllByOrderByCreatedAtDesc();
	public List<Tweet> findByUserIdOrderByCreatedAtDesc(Integer userId);
}
