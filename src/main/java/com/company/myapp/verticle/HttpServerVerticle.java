package com.company.myapp.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import com.company.myapp.config.ApplicationConfiguration;
import com.company.myapp.service.UserService;
import com.company.myapp.service.UserSessionService;

@Component
public class HttpServerVerticle extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory
			.getLogger(HttpServerVerticle.class);

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private UserService userService;

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		router.route("/*").handler(StaticHandler.create());
		router.route("/hello").handler(this::hello);
		router.route("/info").handler(this::info);
		router.route("/demo").handler(this::demo);
		router.route("/online").handler(this::getOnline);
		router.route("/sendmsg").handler(this::sendMsg);
		router.route("/senduser").handler(this::senduser);
		final int port = applicationConfiguration.httpPort();
		vertx.createHttpServer()
				.requestHandler(router)
				.listen(port)
				.onSuccess(
						server -> LOG
								.info("Http Server start success ,listen on the port:{} ",
										server.actualPort()));
	}

	private void hello(RoutingContext context) {
		String address = context.request().connection().remoteAddress()
				.toString();
		MultiMap queryParams = context.queryParams();
		String name = queryParams.contains("name") ? queryParams.get("name")
				: "unknown";
		context.json(new JsonObject()
				.put("name", name)
				.put("address", address)
				.put("message", "@Hello " + name + " connected from " + address));
	}

	private void info(RoutingContext context) {
		context.end(Json.encodePrettily(discoveryClient
				.getInstances(applicationConfiguration.applicationName())));
	}

	private void getOnline(RoutingContext routingContext) {
		JsonObject message = new JsonObject();
		message.put("size", userSessionService.getOnlineSize());
		routingContext.response().putHeader("content-type", "application/json")
				.setStatusCode(200).end(message.toString());
	}

	private void sendMsg(RoutingContext routingContext) {
		JsonObject message = new JsonObject();
		MultiMap queryParams = routingContext.queryParams();
		if (queryParams.contains("value")) {
			String msg = queryParams.get("value");
			userSessionService.sendMessage(msg);
		}
		message.put("size", userSessionService.getOnlineSize());
		routingContext.response().putHeader("content-type", "application/json")
				.setStatusCode(200).end("{'code':1}");
	}

	private void senduser(RoutingContext routingContext) {
		JsonObject message = new JsonObject();
		MultiMap queryParams = routingContext.queryParams();
		if (queryParams.contains("value") && queryParams.contains("uid")) {
			Long uid = Long.valueOf(queryParams.get("uid"));
			String msg = queryParams.get("value");
			userSessionService.sendMessage(uid, msg);
		}
		message.put("size", userSessionService.getOnlineSize());
		routingContext.response().putHeader("content-type", "application/json")
				.setStatusCode(200).end("{\"code\":1}");
	}

	private void demo(RoutingContext context) {
		for (int i = 1; i <= 10; i++) {
			userService.addUser(String.format("%s_%d", "test", i), "pwd@6666");
		}
		context.response().putHeader("content-type", "application/json")
				.setStatusCode(200).end("{\"code\":1}");
	}
}
