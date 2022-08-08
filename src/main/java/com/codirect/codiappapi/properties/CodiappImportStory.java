package com.codirect.codiappapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "codiapp-import-story")
public class CodiappImportStory {

	private String url;
}
