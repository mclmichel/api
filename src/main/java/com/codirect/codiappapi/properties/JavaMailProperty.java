package com.codirect.codiappapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "java-mail")
public class JavaMailProperty {

	private String host;
	private Integer port;
	private String email;
	private String password;
	private String protocolKey;
	private String protocolValue;
	private String authKey;
	private String authValue;
	private String startTlsEnableKey;
	private String startTlsEnableValue;
	private String debugKey;
	private String debugValue;
}
