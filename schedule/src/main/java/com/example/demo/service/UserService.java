package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.PasswordHasher;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;

@Service
public class UserService implements BaseService<User> {
	@Autowired
	private UserDao dao;

	@Override
	public List<User> findAll() {
		return dao.findAll();
	}

	@Override
	public User findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	public List<User> findByIdNot() {
		User user = this.getUserInfo();
		return dao.findByIdNot(user.getId());
	}

	@Override
	public void save(User user) {
//		updateSecurityContext(user);
		dao.save(user);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	public User findByEmail(String email) throws DataNotFoundException {
		return dao.findByEmail(email);
	}

	public User auth(User user) {
		try {
			User foundUser = dao.findByEmail(user.getMail());
			if (PasswordHasher.matches(user.getPassword(), foundUser.getPassword())) {
				foundUser.setAuth(true);
				return foundUser;
			}
		} catch (DataNotFoundException e) {
		}
		user.setAuth(false);
		return user;
	}

	public Boolean isAuth() {
		Boolean isAuth = false;
		User user = this.getUserInfo();
		if (user.getAuth() == null) {
			isAuth = true;
		}
		return isAuth;
	}

	public boolean isUnique(String email) {
		try {
			dao.findByEmail(email);
			return false;
		} catch (DataNotFoundException e) {
			return true;
		}
	}

//	/*
//	 * SpringSecurity側の更新 
//	 */
//	private void updateSecurityContext(User user) {
//		UserDetails userDetails = new UserWithdDpartment(user.getMail(), user.getPassword(), user.getName(), user.getDepartment().getDepartName());
//		SecurityContext context = SecurityContextHolder.getContext();
//		context.setAuthentication(new UsernamePasswordAuthenticationToken(
//				userDetails,
//				userDetails.getPassword(),
//				userDetails.getAuthorities()));
//	}

	/*
	 * ログインのメールアドレスからユーザ情報を取得 
	 */
	public User getUserInfo() {
		User user = new User();
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			return this.findByEmail(email);
		} catch (DataNotFoundException e) {
		}
		user.setAuth(false);
		return user;
	}
}
