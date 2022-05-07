package com.wfqart.stockmarket.services;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfqart.stockmarket.dto.CompanyDetailsDTO;
import com.wfqart.stockmarket.exception.CompanyNotFoundException;
import com.wfqart.stockmarket.model.CompanyDetails;
import com.wfqart.stockmarket.repository.CompanyInfoRepository;
import com.wfqart.stockmarket.utils.StockMarketUtility;

@Service
@Transactional
public class CompanyInfoServiceImpl implements CompanyInfoService {
	
	@Autowired
	private CompanyInfoRepository repository; 
	
	public CompanyDetailsDTO saveCompanyDetails(CompanyDetailsDTO companyDetailsDTO) {

		CompanyDetails newCompany = StockMarketUtility.convertToCompanyDetails(companyDetailsDTO);
		
		repository.save(newCompany);
		
		return companyDetailsDTO;
	};
	//----------------------------------------------------------------------------
	public CompanyDetailsDTO deleteCompany(Long companyCode) {
		
		Integer value = repository.deleteByCompanyCode(companyCode);
		
		if(value != null)
			return (CompanyDetailsDTO) getCompanyInfoById(companyCode);
		else
			throw new CompanyNotFoundException("No Company Found in the Database...");
	};
	//----------------------------------------------------------------------------
	public CompanyDetailsDTO getCompanyInfoById(Long companyCode) {
		
		CompanyDetails companyInfo = repository.findCompanyDetailsById(companyCode);

		return StockMarketUtility.convertToCompanyDetailsDTO(companyInfo);
	};
	
	
}