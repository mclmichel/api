package com.codirect.codiappapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws-s3")
public class AwsS3Property {

	private String bucketName;
	private String endpoint;
	private String accessKey;
	private String secretKey;
	
}
