package com.company.myapp.service;

import com.company.myapp.entity.User;
 
public interface UserService {
	
	public User getUserById(Long id);
	
	public boolean addUser(String name,String pwd);
	
	public User getUserByToken(String token);
	
} 