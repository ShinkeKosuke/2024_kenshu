package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.TweetDao;
import com.example.demo.entity.Tweet;

@Service
public class TweetService implements BaseService<Tweet> {
	@Autowired
	private TweetDao dao;

	@Override
	public List<Tweet> findAll() {
		return dao.findAll();
	}

	@Override
	public Tweet findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}
	
	public List<Tweet> findByUserId(Integer userId) {
		return dao.findByUserId(userId);
	}

	@Override
	public void save(Tweet tweet) {
		dao.save(tweet);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
}

