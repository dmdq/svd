package com.company.myapp.model;

import io.vertx.core.net.NetSocket;

import com.company.myapp.entity.User;

public class UserSession {

	private User user;

	private NetSocket socket;

	public UserSession(User user, NetSocket socket) {
		this.user = user;
		this.socket = socket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public NetSocket getSocket() {
		return socket;
	}

	public void setSocket(NetSocket socket) {
		this.socket = socket;
	}

}
