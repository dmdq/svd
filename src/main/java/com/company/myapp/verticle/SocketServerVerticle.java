package com.company.myapp.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.myapp.config.ApplicationConfiguration;
import com.company.myapp.entity.User;
import com.company.myapp.model.UserSession;
import com.company.myapp.service.UserService;
import com.company.myapp.service.UserSessionService;

@Component
public class SocketServerVerticle extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory
			.getLogger(SocketServerVerticle.class);

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSessionService userSessionService;

	public SocketServerVerticle() {

	}

	@Override
	public void start() {
		NetServer server = vertx.createNetServer();
		server.exceptionHandler(handler -> {

		});
		server.connectHandler(socket -> {
			final String sid = socket.writeHandlerID();
			LOG.info("join: {}", new Object[] { socket.writeHandlerID() });
			socket.handler(buffer -> {
				JsonObject jsonRequset = buffer.toJsonObject();
				String event = jsonRequset.getString("event");
				if (null != event && "login".equals(event)) {
					JsonObject source = jsonRequset.getJsonObject("source");
					Long userId = source.getLong("uid", 0L);
					String token = source.getString("token", "token");
					User user = userService.getUserById(userId);
					if (null != user && token.equals(user.getToken())) {
						JsonObject reply = new JsonObject();
						reply.put("event", "login");
						JsonObject message = new JsonObject();
						message.put("code", 1);
						message.put("message", "Hello:" + user.getName());
						reply.put("source", message);
						socket.write(Buffer.buffer(reply.toString()));
						UserSession session = new UserSession(user, socket);
						userSessionService.addUserSession(session);
					} else {
						socket.close();
					}
				}
			});

			socket.closeHandler(close -> {
				userSessionService.removeUserSession(sid);
				LOG.debug("socket client close:{}", socket.writeHandlerID());
			});

		});
		int port = applicationConfiguration.socketPort();
		server.listen(port, "localhost", res -> {
			if (res.succeeded()) {
				LOG.info(
						"Socket Server start success ,listen on the port:{} !",
						port);
			} else {
				LOG.error("Socket Server Failed to bind:{}!", port);
			}
		});
	}
}