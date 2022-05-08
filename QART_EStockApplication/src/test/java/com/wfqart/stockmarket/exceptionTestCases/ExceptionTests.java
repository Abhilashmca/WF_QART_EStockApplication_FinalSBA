package com.wfqart.stockmarket.exceptionTestCases;

import static com.wfqart.stockmarket.utilTestClass.TestUtils.currentTest;
import static com.wfqart.stockmarket.utilTestClass.TestUtils.exceptionTestFile;
import static com.wfqart.stockmarket.utilTestClass.TestUtils.yakshaAssert;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.wfqart.stockmarket.controller.CompanyInfoController;
import com.wfqart.stockmarket.controller.StockPriceController;
import com.wfqart.stockmarket.dto.CompanyDetailsDTO;
import com.wfqart.stockmarket.dto.StockPriceDetailsDTO;
import com.wfqart.stockmarket.services.CompanyInfoService;
import com.wfqart.stockmarket.services.StockMarketService;
import com.wfqart.stockmarket.utilTestClass.MasterData;


@WebMvcTest({CompanyInfoController.class, StockPriceController.class})
@AutoConfigureMockMvc
public class ExceptionTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CompanyInfoService companyInfoService;

	@MockBean
	private StockMarketService stockMarketService;

	//====================================================================================================================
	//			1. Exceptions tests regarding Company Operations
	//====================================================================================================================	
	@Test
	public void testCompanyForExceptionUponAddingNewCompany() throws Exception
	{
		CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		companyDto.setCompanyName(null);
		
		Mockito.when(companyInfoService.saveCompanyDetails(companyDto)).thenReturn(companyDto);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company/addCompany")
				.content(MasterData.asJsonString(companyDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), (result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? true : false), exceptionTestFile);
	}
	//--------------------------------------------------------------------------------------------
	@Test
	public void testCompanyForExceptionUponAddingCompanyWithNullValue() throws Exception
	{
		CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		companyDto.setStockExchange(null);

		Mockito.when(companyInfoService.saveCompanyDetails(companyDto)).thenReturn(companyDto);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company/addCompany")
				.content(MasterData.asJsonString(companyDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		
		// changed 'true : false' to 'false : true' - 29-09-21
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);		
	}
	//--------------------------------------------------------------------------------------------
	@Test
	public void testCompanyForExceptionUponFetchingCompanyInfoByNullValue() throws Exception
	{
		Mockito.when(companyInfoService.getCompanyInfoById(2L)).thenReturn(null);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/getCompanyInfoByCode/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 404 ? true : false, exceptionTestFile);		
	}
	//--------------------------------------------------------------------------------------------
	@Test
	public void testCompanyForExceptionUponDeletingCompanyByNullValue() throws Exception
	{
		Mockito.when(companyInfoService.deleteCompany(2L)).thenReturn(null);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/deleteCompany/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 404 ? true : false, exceptionTestFile);		
	}	

	//====================================================================================================================
	//			2. Exceptions tests regarding Stock Operations
	//====================================================================================================================	
	
	//--------------------------------------------------------------------------------------------
	@Test
	public void testStockForExceptionUponAddingStockWithNullValue() throws Exception
	{
		StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
		stockDto.setCurrentStockPrice(null);

		Mockito.when(stockMarketService.saveStockPriceDetails(stockDto)).thenReturn(stockDto);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stock/addStock")
				.content(MasterData.asJsonString(stockDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);		
	}
	//--------------------------------------------------------------------------------------------
	@Test
	public void testStockForExceptionUponFetchingStockDetailsByNullValue() throws Exception
	{
		Mockito.when(stockMarketService.getStockByCode(2L)).thenReturn(null);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stock/getStockByCompanyCode/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 404 ? true : false, exceptionTestFile);		
	}
	
}