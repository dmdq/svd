package com.company.myapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.company.myapp.entity.MessageUser;

public interface MessageUserDao extends JpaRepository<MessageUser, Long>,
		JpaSpecificationExecutor<MessageUser> {

}
