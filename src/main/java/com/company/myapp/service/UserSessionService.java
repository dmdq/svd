package com.company.myapp.service;

import com.company.myapp.model.UserSession;

public interface UserSessionService {
	
	void sendMessage(Long userId,String message);
	
	void sendMessage(String message);
	
	int getOnlineSize();
	
	void addUserSession(UserSession session);
	
	void removeUserSession(String socketId);
}
