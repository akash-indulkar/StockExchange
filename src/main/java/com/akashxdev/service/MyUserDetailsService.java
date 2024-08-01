package com.akashxdev.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.akashxdev.dao.StockWrapperDao;
import com.akashxdev.dao.UserDao;
import com.akashxdev.model.StockWrapper;
import com.akashxdev.model.User;
import com.akashxdev.model.UserPrincipal;
import com.netflix.discovery.converters.Auto;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private StockExchangeService stockService;
	
	@Autowired
	private StockWrapperDao wrapperDao;
	
	private BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User 404");
		}
		return new UserPrincipal(user);
	}
	
	public ResponseEntity<String> add(User user)  {
		user.setPassword(encoder.encode(user.getPassword()));
		userDao.save(user);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	public ResponseEntity<String> addBalance(Integer userID, Integer balance) {
		User user = userDao.findById(userID).orElse(null);
		if(user == null) {
			return new ResponseEntity<>("User 404", HttpStatus.BAD_REQUEST);
		}
		Integer newBalance = user.getBalance() + balance;
		user.setBalance(newBalance);
		userDao.save(user);
		return new ResponseEntity<>("Balance added successfully!", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> getBalance(Integer userID) {
		User user = userDao.findById(userID).orElse(null);
		if(user == null) {
			return new ResponseEntity<>("User 404", HttpStatus.BAD_REQUEST);
		}
		Integer balance = user.getBalance();
		return new ResponseEntity<>(balance, HttpStatus.OK);
	}

	public ResponseEntity<String> buyStocks(Integer userID, StockWrapper stocks) {
		User user = userDao.findById(userID).orElse(null);
		if(user == null) {
			return new ResponseEntity<>("User 404", HttpStatus.BAD_REQUEST);
		}
		Integer balance = user.getBalance();
		String type =  stockService.calculateCapital(stocks.getStockName(), stocks.getQty()).getBody().getClass().getName();
		if(type == "java.lang.String") {
			return new ResponseEntity<>((String)(stockService.calculateCapital(stocks.getStockName(), stocks.getQty()).getBody()), HttpStatus.BAD_REQUEST);
		}
		Integer requiredBalance = (Integer) stockService.calculateCapital(stocks.getStockName(), stocks.getQty()).getBody();
		if(balance < requiredBalance) {
			return new ResponseEntity<>("You don't have enough capital to buy stock", HttpStatus.BAD_REQUEST);
		}
		List<StockWrapper> existingStocks = user.getStocks();
			for(StockWrapper existingStock : existingStocks) {
					if(existingStock.getStockName().equals(stocks.getStockName())) {
						existingStock.setQty(existingStock.getQty() + stocks.getQty());
						user.setStocks(existingStocks);
						Integer newBalance = user.getBalance() - requiredBalance;
						user.setBalance(newBalance);
						userDao.save(user);
						return new ResponseEntity<>("Stocks bought successfully", HttpStatus.OK);
					}
		}

		StockWrapper stock = wrapperDao.save(stocks);
		existingStocks.add(stock);
		Integer newBalance = user.getBalance() - requiredBalance;
		user.setBalance(newBalance);
		userDao.save(user);
		return new ResponseEntity<>("Stocks bought successfully", HttpStatus.OK);	
	}

	public ResponseEntity<String> sellStocks(Integer userID, StockWrapper stocks) {
		User user = userDao.findById(userID).orElse(null);
		if(user == null) {
			return new ResponseEntity<>("User 404", HttpStatus.BAD_REQUEST);
		}
		List<StockWrapper> existingStocks = user.getStocks();
		for(StockWrapper stock : existingStocks) {
			if(stock.getStockName().equals(stocks.getStockName())&& stock.getQty() >= stocks.getQty()) {
				stock.setQty(stock.getQty() - stocks.getQty());
				Integer balanceToBeAdded = (Integer) stockService.calculateCapital(stocks.getStockName(), stocks.getQty()).getBody();
				user.setBalance(user.getBalance() + balanceToBeAdded);
				userDao.save(user);
				return new ResponseEntity<>("Stocks sold successfully", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Stocks not found", HttpStatus.BAD_REQUEST);
	}

}
