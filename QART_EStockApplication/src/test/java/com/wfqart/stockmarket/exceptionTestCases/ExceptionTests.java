package com.wfqart.stockmarket.exceptionTestCases;

import static com.wfqart.stockmarket.utilTestClass.TestUtils.currentTest;
import static com.wfqart.stockmarket.utilTestClass.TestUtils.exceptionTestFile;
import static com.wfqart.stockmarket.utilTestClass.TestUtils.yakshaAssert;

import java.time.LocalDate;

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
import com.wfqart.stockmarket.exception.CompanyNotFoundException;
import com.wfqart.stockmarket.exception.ExceptionResponse;
import com.wfqart.stockmarket.exception.InvalidStockException;
import com.wfqart.stockmarket.exception.StockNotFoundException;
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
	public void testCompanyForExceptionUponAddingNewCompany() throws Exception//Done
	{
		CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		companyDto.setCompanyName("SE");
		
		Mockito.when(companyInfoService.saveCompanyDetails(companyDto)).thenReturn(companyDto);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company/add-company")
				.content(MasterData.asJsonString(companyDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), (result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? true : false), exceptionTestFile);
	}
	//--------------------------------------------------------------------------------------------
	@Test
	public void testCompanyForExceptionUponAddingCompanyWithNullValue() throws Exception//Done
	{
		CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		companyDto.setStockExchange(null);

		Mockito.when(companyInfoService.saveCompanyDetails(companyDto)).thenReturn(companyDto);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company/add-company")
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
	public void testCompanyForExceptionUponDeletingCompanyByNullValue() throws Exception//Done
	{
		Mockito.when(companyInfoService.deleteCompany(2L)).thenReturn(null);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/deleteCompany/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 404 ? true : false, exceptionTestFile);		
	}	
	
	@Test
	public void testStockForInvalidStockException() throws Exception//Done
	{
		StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
		stockDto.setCurrentStockPrice(12.0);

		Mockito.when(stockMarketService.saveStockPriceDetails(stockDto)).thenReturn(stockDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stock/add-stock")
				.content(MasterData.asJsonString(stockDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getStatus());
		yakshaAssert(currentTest(), (result.getResponse().getStatus() == 400 ? true : false), exceptionTestFile);
	}
	
	@Test
	public void testCompanyNotFoundExceptionsUponDeleting() throws Exception//Done
	{
		ExceptionResponse exResponse = new ExceptionResponse("Company with Id - 2 not Found!",
				HttpStatus.NOT_FOUND.value());

		Mockito.when(this.companyInfoService.deleteCompany(2L))
		.thenThrow(new CompanyNotFoundException("Company with Id - 2 not Found!"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/deleteCompany/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("*****************testCompanyNotFoundExceptionsUponDeleting********************************************************"
				+ "*************************************************"
				+ result.getResponse().getStatus());
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}	

	//====================================================================================================================
	//			2. Exceptions tests regarding Stock Operations
	//====================================================================================================================	

	//--------------------------------------------------------------------------------------------
	@Test
	public void testCompanyNotFoundExceptionsUponAddingStocks() throws Exception//Done
	{
		StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
		ExceptionResponse exResponse = new ExceptionResponse("Company not Found!",
				HttpStatus.NOT_FOUND.value());

		Mockito.when(this.stockMarketService.saveStockPriceDetails(stockDto))
		.thenThrow(new CompanyNotFoundException("Company not Found!"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stock/add-stock")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("*********testStockForExceptionUponFetchingStockDetailsByNullValue****************************************************************"
				+ "*************************************************"
				+ result.getResponse().getStatus());
		yakshaAssert(currentTest(),
				(result.getResponse().getStatus()==400 ? "true" : "false"),
				exceptionTestFile);
	}

	//====================================================================================================================
	//			2. Exceptions tests regarding Stock Operations
	//====================================================================================================================	
	
	//--------------------------------------------------------------------------------------------
	@Test
	public void testStockForExceptionUponAddingStockWithNullValue() throws Exception//Done
	{
		StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
		stockDto.setCurrentStockPrice(null);

		Mockito.when(stockMarketService.saveStockPriceDetails(stockDto)).thenReturn(stockDto);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stock/add-stock")
				.content(MasterData.asJsonString(stockDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse().getStatus());
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);		
	}
	//--------------------------------------------------------------------------------------------
	@Test
	public void testStockForExceptionUponFetchingStockDetailsByNullValue() throws Exception//Done
	{
		Mockito.when(stockMarketService.getStockByCode(2L)).thenReturn(null);
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stock/getStockByCompanyCode/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);		
	}
	
	@Test
	public void testStockNotFoundExceptionsUponFetchingStockPriceIndex() throws Exception//Done
	{
		ExceptionResponse exResponse = new ExceptionResponse("Stock not found for provided dates",
				HttpStatus.NOT_FOUND.value());

		LocalDate start = LocalDate.parse("2021-05-06");
		LocalDate end = LocalDate.parse("2021-06-09");

		Mockito.when(this.stockMarketService.getStockPriceIndex(2L,start,end))
		.thenThrow(new StockNotFoundException("Stock not found for provided dates"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stock/getStockPriceIndex/2/"+start+"/"+end)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getStatus());
		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == 400 ? true : false),
				exceptionTestFile);
	}
	
	/*@Test
	public void testLeaderNotFoundWhileGettingDevelopmentsWhenLeaderNotPresent() throws Exception {
		
		InvalidStockException invalidStockException = new InvalidStockException("Stock is not present to get development details");
				StockPriceDetailsDTO stockPriceDetailsDTO = MasterData.getStockPriceDetailsDTO()
				when(this.stockMarketService.saveStockPriceDetails(stockPriceDetailsDTO ))
				.thenThrow(new LeaderIdNotFoundException("Leader not present to get development details"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/politics/api/v1/development/0")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getStatus());
		testAssert(currentTest(),
				(result.getResponse().getStatus()==404? "true" : "false"),
				exceptionTestFile);

	}
*/
	
}