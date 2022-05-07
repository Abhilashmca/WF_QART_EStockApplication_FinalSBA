package com.wfqart.stockmarket.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StockPriceDetailsDTO {
	
	private Long id;
	
	@NotNull
	private Long companyCode;

	@NotNull(message = "Please enter Current Stock Price")
    @Column(precision=10, scale=2)
	private Double currentStockPrice;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd")
	@PastOrPresent
	private LocalDate stockPriceDate;
	
	@Temporal(TemporalType.TIME)
	@NotNull
	@PastOrPresent
	private LocalTime stockPriceTime;

	//---------------------------------------------------------------------------------------------------------------------------------
		//---------------------------------------------------------------------------------------------------------------------------------
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	public Long getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Long companyCode) {
		this.companyCode = companyCode;
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	public Double getCurrentStockPrice() {
		return currentStockPrice;
	}
	public void setCurrentStockPrice(Double currentStockPrice) {
		this.currentStockPrice = currentStockPrice;
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	public LocalDate getStockPriceDate() {
		return stockPriceDate;
	}
	public void setStockPriceDate(LocalDate stockPriceDate) {
		this.stockPriceDate = stockPriceDate;
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	public LocalTime getStockPriceTime() {
		return stockPriceTime;
	}
	public void setStockPriceTime(LocalTime stockPriceTime) {
		this.stockPriceTime = stockPriceTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((companyCode == null) ? 0 : companyCode.hashCode());
		result = prime * result + ((currentStockPrice == null) ? 0 : currentStockPrice.hashCode());
		result = prime * result + ((stockPriceDate == null) ? 0 : stockPriceDate.hashCode());
		result = prime * result + ((stockPriceTime == null) ? 0 : stockPriceTime.hashCode());
		return result;
	}
	/*@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockPriceDetailsDTO other = (StockPriceDetailsDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
		{
			return false;
		}
			
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
		{
			return false;
		}
			
		if (currentStockPrice == null) {
			if (other.currentStockPrice != null)
				return false;
		} else if (!currentStockPrice.equals(other.currentStockPrice))
		{
			return false;
		}
			
		if (stockPriceDate == null) {
			if (other.stockPriceDate != null)
				return false;
		} else if (!stockPriceDate.equals(other.stockPriceDate))
		{
			return false;
		}
			
		if (stockPriceTime == null) {
			if (other.stockPriceTime != null)
				return false;
		} else if (!stockPriceTime.equals(other.stockPriceTime))
		{
			return false;
		}
			
		return true;
	}	
	*/
	
}