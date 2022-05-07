package com.wfqart.stockmarket.dto;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class StockPriceIndexDTO {

	@NotNull
	private CompanyDetailsDTO companyDto;

	@NotNull
	private List<StockPriceDetailsDTO> stockPriceList;

	@NotNull
	@Digits(integer = 10, fraction = 2,  message = "Stock Price must have precision 10 and factional part of 2 decimals")		//	@Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/)
	private Double maxStockPrice;

	@NotNull
	@Digits(integer = 10, fraction = 2,  message = "Stock Price must have precision 10 and factional part of 2 decimals")		//	@Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/)
	private Double minStockPrice;

	@NotNull
	@Digits(integer = 10, fraction = 2,  message = "Stock Price must have precision 10 and factional part of 2 decimals")		//	@Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/)
	private Double avgStockPrice;

	//--------------------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------------------
	public CompanyDetailsDTO getCompanyDto() {
		return companyDto;
	}
	public void setCompanyDto(CompanyDetailsDTO companyDto) {
		this.companyDto = companyDto;
	}
	//--------------------------------------------------------------------------------------------------------------------------
	public List<StockPriceDetailsDTO> getStockPriceList() {
		return stockPriceList;
	}
	public void setStockPriceList(List<StockPriceDetailsDTO> stockPriceList) {
		this.stockPriceList = stockPriceList;
	}
	//--------------------------------------------------------------------------------------------------------------------------
	public Double getMaxStockPrice() {
		return maxStockPrice;
	}
	public void setMaxStockPrice(Double maxStockPrice) {
		this.maxStockPrice = maxStockPrice;
	}
	//--------------------------------------------------------------------------------------------------------------------------
	public Double getMinStockPrice() {
		return minStockPrice;
	}
	public void setMinStockPrice(Double minStockPrice) {
		this.minStockPrice = minStockPrice;
	}
	//--------------------------------------------------------------------------------------------------------------------------
	public Double getAvgStockPrice() {
		return avgStockPrice;
	}
	public void setAvgStockPrice(Double avgStockPrice) {
		this.avgStockPrice = avgStockPrice;
	}

		
}