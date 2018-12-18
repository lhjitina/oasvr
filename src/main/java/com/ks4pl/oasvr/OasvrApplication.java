package com.ks4pl.oasvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class OasvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(OasvrApplication.class, args);
		}
}
