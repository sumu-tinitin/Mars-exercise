package com.app.demo;

import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InputConfig {

	@Bean
	public InputStream appInputStream() {
		return System.in;
	}
}
