package com.microservices.limitsservice.currencyexchangeservice;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.limitsservice.currencyexchangeservice.bean.ExchangeValue;
import com.microservices.limitsservice.currencyexchangeservice.repository.ExchangeValueRepository;


@RestController
public class CurrencyExchangeController {
	
	private Logger logger = Logger.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	Environment environment;
	
	@Autowired
	ExchangeValueRepository repo ;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchange = repo.findByFromAndTo(from, to);
		
		Integer port = environment.getProperty("local.server.port", Integer.class);
		exchange.setPort(port);
		
		logger.info("LOGG:" + exchange);
		
		return exchange;
	}

}
