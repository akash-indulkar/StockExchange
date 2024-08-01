package com.akashxdev.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.akashxdev.model.StockWrapper;

@Repository
public interface StockWrapperDao extends JpaRepository<StockWrapper, Integer>{
	List<StockWrapper> findByStockName(String stockName);

}
