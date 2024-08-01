package com.akashxdev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.akashxdev.feign.StockFeign;

@Service
public class StockExchangeService {

	@Autowired
	private StockFeign stockFeign;

	public ResponseEntity<?> calculateCapital(String stockname, Integer qty) {
		Integer stockPrice = null;
		Integer totalPrice = null;
		try {
			stockPrice = stockFeign.getPriceByStockName(stockname).getBody();
			totalPrice = stockPrice * qty;
			return new ResponseEntity<>(totalPrice, HttpStatus.OK);
		}
		catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			return new ResponseEntity<String>("Stock not found with name " + stockname, HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
