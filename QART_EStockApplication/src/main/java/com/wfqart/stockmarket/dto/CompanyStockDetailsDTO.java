package com.wfqart.stockmarket.dto;

import java.util.List;

public class CompanyStockDetailsDTO {
	
	private CompanyDetailsDTO companyDetails;
	private List<StockPriceDetailsDTO> stockPriceDetails;
	public List<StockPriceDetailsDTO> getStockPriceDTO() {
		return stockPriceDetails;
	}
	public void setStockPriceDTO(List<StockPriceDetailsDTO> stockDtos) {
		this.stockPriceDetails = stockDtos;
	}
	public CompanyDetailsDTO getCompanyDto() {
		return companyDetails;
	}
	public void setCompanyDto(CompanyDetailsDTO companyDto) {
		this.companyDetails = companyDto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyDetails == null) ? 0 :companyDetails.hashCode());
		result = prime * result + ((stockPriceDetails == null) ? 0 : stockPriceDetails.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyStockDetailsDTO other = (CompanyStockDetailsDTO) obj;
		if (companyDetails == null) {
			if (other.companyDetails != null)
				return false;
		} else if (!companyDetails.equals(other.companyDetails))
		{
			return false;
		}
			
		if (stockPriceDetails == null) {
			if (other.stockPriceDetails != null)
				return false;
		} else if (!stockPriceDetails.equals(other.stockPriceDetails))
		{
			return false;
		}
			
		return true;
	}
	

}
