package com.wfqart.stockmarket.functionalTestCases;

import static com.wfqart.stockmarket.utilTestClass.TestUtils.businessTestFile;
import static com.wfqart.stockmarket.utilTestClass.TestUtils.currentTest;
import static com.wfqart.stockmarket.utilTestClass.TestUtils.yakshaAssert;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@WebMvcTest({ CompanyInfoController.class, StockPriceController.class })
@AutoConfigureMockMvc
public class TestController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CompanyInfoService companyService;

	@MockBean
	private StockMarketService stockMarketService;

	// ===========================================================================================================================
	// I - Testing CompanyDetailsController Rest End Points
	// =======================================================================================================================

	@Test
	public void testAddCompany() throws Exception {
		CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();

		Mockito.when(companyService.saveCompanyDetails(companyDto)).thenReturn(companyDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company/add-company")
				.content(MasterData.asJsonString(companyDto)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(result.getResponse().getStatus());
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 200 ? true : false, businessTestFile);
	}

	/*@Test
	public void testAddStockPrice() throws Exception {
		StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();

		Mockito.when(stockMarketService.saveStockPriceDetails(stockDto)).thenReturn(stockDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stock/add-stock")
				.content(MasterData.asJsonString(stockDto)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getStatus());
		yakshaAssert(currentTest(),result.getResponse().getStatus() == 200 ? true : false,
				businessTestFile);
	}*/

	@Test
	public void testFindStockByCompanyCode() throws Exception {
		StockPriceDetailsDTO stockDto = MasterData.getStockPriceDetailsDTO();
		Long companyCode = stockDto.getCompanyCode();

		List<StockPriceDetailsDTO> stockList = new ArrayList<StockPriceDetailsDTO>();
		stockList.add(stockDto);

		Mockito.when(stockMarketService.getStockByCode(companyCode)).thenReturn(stockList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stock/getStockByCompanyCode/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		// changed 'true : false' to 'false : true' - 29-09-21

		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains("\"currentStockPrice\":55.76") ? true : false,
				businessTestFile);
	}

	/*
	 * //-- BDD Test : addCompany
	 * -------------------------------------------------------------------------
	 * -------------------------
	 * 
	 * @SuppressWarnings("unused")
	 * 
	 * @Test public void testAddCompanyBDD() throws Exception { final int
	 * count[] = new int[1];
	 * 
	 * CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
	 * 
	 * Mockito.when(companyService.saveCompanyDetails(companyDto)).then(new
	 * Answer<CompanyDetailsDTO>() {
	 * 
	 * @Override public CompanyDetailsDTO answer(InvocationOnMock invocation)
	 * throws Throwable {
	 * 
	 * count[0]++; return companyDto; } });
	 * 
	 * RequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.post("/company/add-company")
	 * .content(MasterData.asJsonString(companyDto))
	 * .contentType(MediaType.APPLICATION_JSON)
	 * .accept(MediaType.APPLICATION_JSON);
	 * 
	 * MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 * 
	 * 
	 * yakshaAssert(currentTest(), count[0] > 0 ? true : false,
	 * businessTestFile); }
	 */
	// ---------------------------------------------------------------------------------------------------------------------------
	// 2. Testing Rest End Point - /company/deleteCompany/{id}
	// -- Test 1 : deleteCompany
	// -------------------------------------------------------------------------------------------------
	@Test
	public void testDeleteCompany() throws Exception {
		CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		Long companyCode = companyDto.getCompanyCode();

		Mockito.when(companyService.deleteCompany(companyCode)).thenReturn(companyDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/deleteCompany/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(companyDto)) ? true
						: false,
				businessTestFile);
	}

	// -- BDD Test : deleteCompany
	// -----------------------------------------------------------------------------------------------
	@SuppressWarnings("unused")
	@Test
	public void testDeleteCompanyBDD() throws Exception {
		final int count[] = new int[1];

		CompanyDetailsDTO companyDto = MasterData.getCompanyDetailsDTO();
		Long companyCode = companyDto.getCompanyCode();

		Mockito.when(companyService.deleteCompany(companyCode)).then(new Answer<CompanyDetailsDTO>() {
			@Override
			public CompanyDetailsDTO answer(InvocationOnMock invocation) throws Throwable {

				count[0]++;
				return MasterData.getCompanyDetailsDTO();
			}
		});

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/company/deleteCompany/" + companyCode)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] > 0 ? true : false, businessTestFile);
	}

}