package com.ks4pl.oasvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@SpringBootApplication
public class OasvrApplication {

	public static Logger logger = LogManager.getLogger("ksoasvr");

	public static void main(String[] args) {
		SpringApplication.run(OasvrApplication.class, args);
		}
}
