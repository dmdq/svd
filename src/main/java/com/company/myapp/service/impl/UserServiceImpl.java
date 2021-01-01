package com.company.myapp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.myapp.dao.UserDao;
import com.company.myapp.entity.User;
import com.company.myapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User getUserById(Long id) {
		Optional<User> user = userDao.findById(id);
		return user.get();
	}

	@Override
	public boolean addUser(String name, String pwd) {
		User user = new User();
		user.setCreatedTime(System.currentTimeMillis());
		user.setName(name);
		user.setPassword(pwd);
		user.setToken("token");
		user = userDao.save(user);
		return user.getId() != 0;
	}

	@Override
	public User getUserByToken(String token) {
		return null;
	}

}
