package com.microservices.limitsservice.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.limitsservice.currencyexchangeservice.bean.ExchangeValue;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {

	ExchangeValue findByFromAndTo(String to, String from);
	
}
