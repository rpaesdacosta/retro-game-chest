package com.api.retrogamechest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RetroGameChestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetroGameChestApplication.class, args);
	}

	@GetMapping("/info")
	public String index() {
		return "Welcome to Retro Game Chest, feel free to manage your retro game collection!";
	}
}
