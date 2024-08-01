package com.akashxdev.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StockWrapper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String stockName;
	private Integer qty;
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
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	public StockWrapper(Integer id, String stockName, Integer qty) {
		super();
		this.id = id;
		this.stockName = stockName;
		this.qty = qty;
	}

	public StockWrapper() {
		super();
	}
	
	@Override
	public String toString() {
		return "StockWrapper [id=" + id + ", stockName=" + stockName + ", qty=" + qty + "]";
	}
	
	
}
