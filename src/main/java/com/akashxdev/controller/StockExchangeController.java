package com.akashxdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akashxdev.dao.UserDao;
import com.akashxdev.feign.StockFeign;
import com.akashxdev.model.StockWrapper;
import com.akashxdev.service.MyUserDetailsService;
import com.akashxdev.service.StockExchangeService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/exchange")
public class StockExchangeController {

	@Autowired
	private MyUserDetailsService userService; 
	
	@Autowired
	private StockExchangeService stockService;
	
	@GetMapping("/calculatecapital")
	public ResponseEntity<?> getRequiredCapital(@RequestParam String stockname, @RequestParam Integer qty){
		return stockService.calculateCapital(stockname, qty);
	}
	
	@PostMapping("/buy/{userID}")
	public ResponseEntity<String> buyStocks(@PathVariable Integer userID, @RequestBody StockWrapper stocks) {
		return userService.buyStocks(userID, stocks);
	}
	

	@PostMapping("/sell/{userID}")
	public ResponseEntity<String> sellStocks(@PathVariable Integer userID, @RequestBody StockWrapper stocks) {
		return userService.sellStocks(userID, stocks);
	}
	
}
