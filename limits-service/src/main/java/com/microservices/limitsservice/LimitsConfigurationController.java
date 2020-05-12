package com.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.limitsservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigurationController {

	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimistFromConfigurations() {
		return new  LimitConfiguration(configuration.getMinimum(), configuration.getMaximum());
	}
	
	@GetMapping("/fault")
	@HystrixCommand(fallbackMethod = "fallback")
	public LimitConfiguration retrieve() {
		throw new RuntimeException("Ups..");
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	public LimitConfiguration fallback() {
		return new  LimitConfiguration(1, 111);
	}
}
