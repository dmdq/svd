package com.company.myapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.company.myapp.entity.User;

public interface UserDao extends JpaRepository<User, Long>,
		JpaSpecificationExecutor<User> {

}
