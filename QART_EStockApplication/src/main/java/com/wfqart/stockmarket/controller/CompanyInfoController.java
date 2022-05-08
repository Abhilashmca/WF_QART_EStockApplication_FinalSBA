package com.wfqart.stockmarket.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wfqart.stockmarket.dto.CompanyDetailsDTO;
import com.wfqart.stockmarket.dto.InvalidCompanyExceptionResponse;
import com.wfqart.stockmarket.exception.CompanyNotFoundException;
import com.wfqart.stockmarket.exception.InvalidCompanyException;
import com.wfqart.stockmarket.services.CompanyInfoService;

@RestController
@RequestMapping (value = "/company")
public class CompanyInfoController {

	@Autowired
	private CompanyInfoService companyInfoService;

	
	//-------------------------------------------------------------------------------------------------------------------------------
	@PostMapping(value="/add-company")																					// 3. WORKING
	public ResponseEntity<CompanyDetailsDTO> addCompanyDetails(@Valid @RequestBody CompanyDetailsDTO companyDetailsDTO, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			throw new InvalidCompanyException("Invalid Company Details!!!");
		}
		else
			return new ResponseEntity<>(companyInfoService.saveCompanyDetails(companyDetailsDTO), HttpStatus.OK);
	}
	//-------------------------------------------------------------------------------------------------------------------------------
	@DeleteMapping(value = "/deleteCompany/{companyCode}")																// 4. WORKING
	public ResponseEntity<CompanyDetailsDTO> deleteCompanyDetails(@PathVariable("companyCode") Long companyCode) {
	
		if(companyInfoService.deleteCompany(companyCode) == null)
			throw new CompanyNotFoundException("Invalid Company Code!! Please enter valid companyCode...");
		else	
			return new ResponseEntity<>(companyInfoService.deleteCompany(companyCode), HttpStatus.OK);
	}
	
	//================================================================================================
	//			UTITLITY EXCEPTION HANDLERS - 2
	//================================================================================================
	@ExceptionHandler(InvalidCompanyException.class)
	public ResponseEntity<InvalidCompanyExceptionResponse> companyHandler(InvalidCompanyException ex) {
		InvalidCompanyExceptionResponse resp = new InvalidCompanyExceptionResponse(ex.getMessage(),System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		
	}
	//------------------------------------------------------------------------------------------------
	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<InvalidCompanyExceptionResponse> companyHandler(CompanyNotFoundException ex){
		InvalidCompanyExceptionResponse resp = new InvalidCompanyExceptionResponse(ex.getMessage(),System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
		
	}	
}