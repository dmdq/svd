package com.company.myapp.service.impl;

import io.vertx.core.json.JsonObject;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.myapp.dao.MessageSystemDao;
import com.company.myapp.dao.MessageUserDao;
import com.company.myapp.entity.MessageSystem;
import com.company.myapp.entity.MessageUser;
import com.company.myapp.model.UserSession;
import com.company.myapp.service.UserSessionService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

@Service
public class UserSessionServiceImpl implements UserSessionService {

	private Map<String, UserSession> sessions;

	private Multimap<Long, String> userMultimap;
	
	@Autowired
	private MessageSystemDao messageSystemDao;
	
	@Autowired
	private MessageUserDao messageUserDao;

	public UserSessionServiceImpl() {
		this.sessions = Maps.newConcurrentMap();
		this.userMultimap = ArrayListMultimap.create();
	}

	@Override
	public void sendMessage(Long userId, String message) {
		
		if(userMultimap.containsKey(userId)){
			Collection<String> sids = userMultimap.get(userId);
			JsonObject eventMsg = new JsonObject();
			JsonObject eventSource = new JsonObject();
			eventSource.put("code", 1);
			eventSource.put("value", message);
			eventMsg.put("event","msgUser");
			eventMsg.put("source", eventSource);
			
			for (String sid : sids) {
				sessions.get(sid).getSocket().write(eventMsg.toBuffer());
			}
			
			MessageUser msg = new MessageUser();
			msg.setCreatedTime(System.currentTimeMillis());
			msg.setType(1);
			msg.setValue(message);
			msg.setFromId(1L);
			msg.setUserId(userId);
			messageUserDao.save(msg);
		}
		
		
	}

	@Override
	public void sendMessage(String message) {
		Collection<UserSession> sids = sessions.values();
		JsonObject eventMsg = new JsonObject();
		JsonObject eventSource = new JsonObject();
		eventSource.put("code", 1);
		eventSource.put("value", message);
		eventMsg.put("event","msgSys");
		eventMsg.put("source", eventSource);
		
		for (UserSession sid : sids) {
			sid.getSocket().write(eventMsg.toBuffer());
		}
		MessageSystem msgSys = new MessageSystem();
		msgSys.setCreatedTime(System.currentTimeMillis());
		msgSys.setType(1);
		msgSys.setValue(message);
		messageSystemDao.save(msgSys);
	}

	@Override
	public int getOnlineSize() {
		return userMultimap.size();
	}

	@Override
	public void removeUserSession(String socketId) {
		UserSession session = sessions.remove(socketId);
		userMultimap.remove(session.getUser().getId(), socketId);
	}

	@Override
	public void addUserSession(UserSession session) {
		sessions.put(session.getSocket().writeHandlerID(), session);
		userMultimap.put(session.getUser().getId(), session.getSocket()
				.writeHandlerID());
	}

}
