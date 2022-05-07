package com.wfqart.stockmarket.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CompanyDetailsDTO {

	private Long companyCode;

	@NotNull
	@Length(min = 5, max = 50,message = "Stock Exchange name should be minimun 5 characters and maximum 50 characters")
	private String stockExchange;

	@NotNull
	@Length(min = 5, max = 50,message = "Company name should be minimun 5 characters and maximum 50 characters")
	private String companyName;

	@NotNull
	@Length(min = 3, max = 50,message = "Company CEO name should be minimun 3 characters and maximum 50 characters")
	private String companyCEO;

	@NotNull(message = "Company turnover cannot be empty. Please provide valid details")
	@Column(precision=10, scale=2)
	private Double turnover;

	@NotNull
	@Length(min = 5, max = 200,message = "Company Directors should be minimun 5 characters and maximum 200 characters")
	private String boardOfDirectors;

	@NotNull
	@Length(min = 5, max = 255,message = "Company Profile should be minimun 5 characters and maximum 255 characters")
	private String companyProfile;
	
	//---------------------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------------------------------------------------------
	public Long getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(Long companyCode) {
		this.companyCode = companyCode;
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	public String getStockExchange() {
		return stockExchange;
	}
	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	public String getCompanyCEO() {
		return companyCEO;
	}
	public void setCompanyCEO(String companyCEO) {
		this.companyCEO = companyCEO;
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	public Double getTurnover() {
		return turnover;
	}
	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	public String getBoardOfDirectors() {
		return boardOfDirectors;
	}
	public void setBoardOfDirectors(String boardOfDirectors) {
		this.boardOfDirectors = boardOfDirectors;
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	public String getCompanyProfile() {
		return companyProfile;
	}
	public void setCompanyProfile(String companyProfile) {
		this.companyProfile = companyProfile;
	}
		
	@Override
	public String toString() {
		return "CompanyDetailsDTO [companyCode=" + companyCode + ", stockExchange=" + stockExchange + ", companyName="
				+ companyName + ", companyCEO=" + companyCEO + ", turnover=" + turnover + ", boardOfDirectors="
				+ boardOfDirectors + ", companyProfile=" + companyProfile + "]";
	}

}