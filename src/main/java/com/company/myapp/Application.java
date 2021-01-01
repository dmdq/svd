package com.company.myapp;

import io.vertx.core.Vertx;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.company.myapp.verticle.HttpServerVerticle;
import com.company.myapp.verticle.SocketServerVerticle;

@EnableDiscoveryClient
@SpringBootApplication
public class Application {

	@Autowired
	private HttpServerVerticle httpServerVerticle;

	@Autowired
	private SocketServerVerticle socketSocketServer;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void deployVerticle() {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(httpServerVerticle);
		vertx.deployVerticle(socketSocketServer);
	}

}
