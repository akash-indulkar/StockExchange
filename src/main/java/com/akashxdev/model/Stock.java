package com.akashxdev.model;

public class Stock {

	private Integer id;
	private String stockName;
	private Integer stockPrice;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public Integer getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(Integer stockPrice) {
		this.stockPrice = stockPrice;
	}

	public Stock(Integer id, String stockName, Integer stockPrice) {
		super();
		this.id = id;
		this.stockName = stockName;
		this.stockPrice = stockPrice;
	}
	
	public Stock() {
		super();
	}
	
	
}
