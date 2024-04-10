package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.DepartmentDao;
import com.example.demo.domain.TweetInfo;
import com.example.demo.entity.Department;

@Service
public class DepartmentService implements BaseService<Department> {
	@Autowired
	private DepartmentDao dao;

	@Override
	public List<Department> findAll() {
		return dao.findAll();
	}

	@Override
	public Department findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void save(Department tweet) {
		dao.save(tweet);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	public List<TweetInfo> exchangeTweetInfoList(List<Department> tweets, Integer userId) {
		List<TweetInfo> tweetList = new ArrayList<TweetInfo>();
		for (var tweet : tweets) {
			tweetList.add(this.exchangeTweetInfo(tweet, userId));
		}
		return tweetList;
	}

	public TweetInfo exchangeTweetInfo(Department tweet, Integer userId) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH時mm分ss秒");
		Boolean isForvarite = false;
		TweetInfo tweetInfo = new TweetInfo();
		tweetInfo.setId(tweet.getId());
		tweetInfo.setIsFavorite(isForvarite);
		return tweetInfo;
	}
}
