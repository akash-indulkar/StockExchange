package com.akashxdev.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "StockPriceService")
public interface StockFeign {
	
	@GetMapping("/stocks/getprice/{stockname}")
	public ResponseEntity<Integer> getPriceByStockName(@PathVariable String stockname);
}
