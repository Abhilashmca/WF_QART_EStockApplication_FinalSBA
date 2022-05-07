package com.wfqart.stockmarket.services;

import com.wfqart.stockmarket.dto.CompanyDetailsDTO;

public interface CompanyInfoService {

	public CompanyDetailsDTO saveCompanyDetails(CompanyDetailsDTO companyDetailsDTO);
	public CompanyDetailsDTO deleteCompany(Long companyCode);
	//----------------------------------------------------------------------------
	public CompanyDetailsDTO getCompanyInfoById(Long companyCode);
	
}