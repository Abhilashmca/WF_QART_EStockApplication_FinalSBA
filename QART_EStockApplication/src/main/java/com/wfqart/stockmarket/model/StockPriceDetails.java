package com.wfqart.stockmarket.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="StockPriceDetails")
public class StockPriceDetails implements Serializable {

	private static final long serialVersionUID = 3721936374836041464L;

	@GeneratedValue(strategy=GenerationType.AUTO)
	@javax.persistence.Id
	private Long id;
	
	
	private Long companyCode;

	@Column(precision=10, scale=2)
	private Double currentStockPrice;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate stockPriceDate;
	
	@Column(columnDefinition = "TIME")
	private LocalTime stockPriceTime;
	
	 @ManyToOne
	    @JoinColumn(name="companyCode",insertable = false, updatable = false)
	    private CompanyDetails company;
	    
	    public CompanyDetails getCompany() {
			return company;
		}
		public void setCompany(CompanyDetails company) {
			this.company = company;
		}

	//---------------------------------------------------------------------------------------------------------------------------------
	public StockPriceDetails() {
		super();
	}	
	public StockPriceDetails(Long id, Long companyCode, Double currentStockPrice, LocalDate stockPriceDate,	LocalTime stockPriceTime) {
		super();
		this.id = id;
		this.companyCode = companyCode;
		this.currentStockPrice = currentStockPrice;
		this.stockPriceDate = stockPriceDate;
		this.stockPriceTime = stockPriceTime;
	}

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
	public String toString() {
		return "StockPriceDetails [Id=" + id + ", companyCode=" + companyCode + ", currentStockPrice="
				+ currentStockPrice + ", stockPriceDate=" + stockPriceDate + ", stockPriceTime=" + stockPriceTime
				+ ", company=" + company + "]";
	}
}