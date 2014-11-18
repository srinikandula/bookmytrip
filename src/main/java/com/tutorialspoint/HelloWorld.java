package com.tutorialspoint;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class HelloWorld extends PropertyPlaceholderConfigurer {
	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	public void getMessage() {
		System.out.println("Your Message : " + message);
	}
}