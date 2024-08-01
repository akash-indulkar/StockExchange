package com.akashxdev.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String email;
	private String password;
	@ManyToMany
	private List<StockWrapper> stocks;
	private Integer balance = 0;
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public List<StockWrapper> getStocks() {
		return stocks;
	}
	public void setStocks(List<StockWrapper> stocks) {
		this.stocks = stocks;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public User(Integer id, String email, String password, List<StockWrapper> stocks, Integer balance) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.stocks = stocks;
		this.balance = balance;
	}
	
	public User() {
		super();
	} 
	
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", stocks=" + stocks + ", balance="
				+ balance + "]";
	}

	
	
}
