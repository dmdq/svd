package com.company.myapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.company.myapp.entity.MessageSystem;

public interface MessageSystemDao extends JpaRepository<MessageSystem, Long>,
		JpaSpecificationExecutor<MessageSystem> {

}
