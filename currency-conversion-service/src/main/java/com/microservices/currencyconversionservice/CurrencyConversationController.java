package com.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.currencyconversionservice.bean.CurrencyConversionBean;
import com.microservices.currencyconversionservice.client.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversationController {

	private Logger logger = Logger.getLogger(CurrencyConversationController.class);

	
	@Autowired
	CurrencyExchangeServiceProxy proxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean test(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> urlVariables = new HashMap<>();
		urlVariables.put("from", from);
		urlVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> response = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				urlVariables);

		CurrencyConversionBean conversion = response.getBody();				
		
		return new CurrencyConversionBean(conversion, from, to, quantity);
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean testFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		CurrencyConversionBean conversion = proxy.retrieveExchangeValue(from, to);
		
		logger.info("LOGG: " + conversion);
		
		return new CurrencyConversionBean(conversion, from, to, quantity);
	}

}
