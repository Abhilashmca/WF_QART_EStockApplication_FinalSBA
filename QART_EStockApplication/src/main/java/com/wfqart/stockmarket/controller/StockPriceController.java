package com.wfqart.stockmarket.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wfqart.stockmarket.dto.InvalidStockExceptionResponse;
import com.wfqart.stockmarket.dto.StockPriceDetailsDTO;
import com.wfqart.stockmarket.dto.StockPriceIndexDTO;
import com.wfqart.stockmarket.exception.InvalidStockException;
import com.wfqart.stockmarket.exception.StockNotFoundException;
import com.wfqart.stockmarket.services.StockMarketService;

@RestController
@RequestMapping (value = "/stock")
public class StockPriceController {

	@Autowired
	private StockMarketService stockMarketService;
	//-------------------------------------------------------------------------------------------------------------------------------
	// SERVICE OPERATIONS
	//-------------------------------------------------------------------------------------------------------------------------------
	
	@PostMapping(value="/add-stock")																						// 2. WORKING
	public ResponseEntity<StockPriceDetailsDTO> addStockDetails(@Valid @RequestBody StockPriceDetailsDTO stockPriceDetailsDTO, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			throw new InvalidStockException("Invalid Stock Details!!!");
		}
		else
			return new ResponseEntity<>(stockMarketService.saveStockPriceDetails(stockPriceDetailsDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/getStockByCompanyCode/{companyCode}")															// 4. WORKING
	public ResponseEntity<List<StockPriceDetailsDTO>> getStockByCompanyCode(@PathVariable Long companyCode) {
		List<StockPriceDetailsDTO> list = stockMarketService.getStockByCode(companyCode);
		if( list == null)
			throw new StockNotFoundException("Invalid Company Code!! Please enter valid companyCode...");
		else
			return new ResponseEntity<>(list, HttpStatus.OK);
	}
	//-------------------------------------------------------------------------------------------------------------------------------
	@GetMapping(value = "/getStockPriceIndex/{companyCode}/{startDate}/{endDate}")										// 5. WORKING
	public ResponseEntity<StockPriceIndexDTO> displayStockPriceIndex(@PathVariable Long companyCode, @PathVariable Date startDate, @PathVariable Date endDate) {
		
		if(stockMarketService.getStockPriceIndex(companyCode, startDate.toLocalDate(), endDate.toLocalDate()) == null)
			throw new StockNotFoundException("Invalid Company Code or Date!!! Please enter valid Details...");
		else	
			return new ResponseEntity<>(stockMarketService.getStockPriceIndex(companyCode, startDate.toLocalDate(), endDate.toLocalDate()), HttpStatus.OK);
	}
	
	/*//===============================================================================================================================
	//			UTITLITY EXCEPTION HANDLERS - 2
	//===============================================================================================================================
	@ExceptionHandler(InvalidStockException.class)
	public ResponseEntity<InvalidStockExceptionResponse> companyHandler(InvalidStockException ex) {
		InvalidStockExceptionResponse resp = new InvalidStockExceptionResponse(ex.getMessage(),System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		
	}
	//------------------------------------------------------------------------------------------------
	@ExceptionHandler(StockNotFoundException.class)
	public ResponseEntity<InvalidStockExceptionResponse> companyHandler(StockNotFoundException ex) {
		InvalidStockExceptionResponse resp = new InvalidStockExceptionResponse(ex.getMessage(),System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
		
	}	*/
}