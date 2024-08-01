package com.akashxdev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.akashxdev.model.StockWrapper;
import com.akashxdev.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>{

	User findByEmail(String email);

	@Query(value = "UPDATE user u SET u.balance = : balance WHERE u.id = userID", nativeQuery = true)
	void updateBalanceByUserId(Integer userID, Integer balance);

}