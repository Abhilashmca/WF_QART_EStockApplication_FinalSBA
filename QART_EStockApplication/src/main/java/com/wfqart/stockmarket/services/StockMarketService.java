package com.wfqart.stockmarket.services;

import java.time.LocalDate;
import java.util.List;

import com.wfqart.stockmarket.dto.CompanyStockDetailsDTO;
import com.wfqart.stockmarket.dto.StockPriceDetailsDTO;
import com.wfqart.stockmarket.dto.StockPriceIndexDTO;

public interface StockMarketService 
{
	public StockPriceDetailsDTO saveStockPriceDetails(StockPriceDetailsDTO stockPriceDetailsDTO);
	public List<StockPriceDetailsDTO> getStockByCode(Long companyCode);
	public StockPriceIndexDTO getStockPriceIndex(Long companyCode, LocalDate startDate, LocalDate endDate);
	public List<StockPriceDetailsDTO> getAllStocksByCompanyCode(Long companyCode);
	public CompanyStockDetailsDTO getAllStocksDetailsByCompanyCode(Long companyCode);
}