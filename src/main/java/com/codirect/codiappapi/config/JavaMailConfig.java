package com.codirect.codiappapi.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.codirect.codiappapi.properties.JavaMailProperty;

@Configuration
public class JavaMailConfig {

	@Autowired
	private JavaMailProperty javaMailProperty;
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties props = mailSender.getJavaMailProperties();
		mailSender.setHost(javaMailProperty.getHost());
		mailSender.setPort(javaMailProperty.getPort());
		mailSender.setUsername(javaMailProperty.getEmail());
		mailSender.setPassword(javaMailProperty.getPassword());
		props.put(javaMailProperty.getProtocolKey(), javaMailProperty.getProtocolValue());
		props.put(javaMailProperty.getAuthKey(), javaMailProperty.getAuthValue());
		props.put(javaMailProperty.getStartTlsEnableKey(), javaMailProperty.getStartTlsEnableValue());
		props.put(javaMailProperty.getDebugKey(), javaMailProperty.getDebugValue());
		return mailSender;
	}
}
