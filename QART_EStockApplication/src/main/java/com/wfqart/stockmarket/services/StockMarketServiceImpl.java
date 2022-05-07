package com.wfqart.stockmarket.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wfqart.stockmarket.dto.CompanyDetailsDTO;
import com.wfqart.stockmarket.dto.CompanyStockDetailsDTO;
import com.wfqart.stockmarket.dto.StockPriceDetailsDTO;
import com.wfqart.stockmarket.dto.StockPriceIndexDTO;
import com.wfqart.stockmarket.exception.StockNotFoundException;
import com.wfqart.stockmarket.model.CompanyDetails;
import com.wfqart.stockmarket.model.StockPriceDetails;
import com.wfqart.stockmarket.repository.CompanyInfoRepository;
import com.wfqart.stockmarket.repository.StockPriceRepository;
import com.wfqart.stockmarket.utils.StockMarketUtility;

@Service
@Transactional
public class StockMarketServiceImpl implements StockMarketService {

	@Autowired
    private StockPriceRepository stockRepository;

	@Autowired
    private CompanyInfoRepository companyRepository;
	
	//----------------------------------------------------------------------------
	public StockPriceDetailsDTO saveStockPriceDetails(StockPriceDetailsDTO stockPriceDetailsDTO) {
		
		StockPriceDetails newStock = StockMarketUtility.convertToStockPriceDetails(stockPriceDetailsDTO);

		stockRepository.save(newStock);
		
		return stockPriceDetailsDTO;
	}
	//----------------------------------------------------------------------------
	public List<StockPriceDetailsDTO> deleteStock(Long companyCode) {
		
		Integer value = stockRepository.deleteStockByCompanyCode(companyCode);

		if(value != null)
			return StockMarketUtility.convertToStockPriceDetailsDtoList(stockRepository.findStockByCompanyCode(companyCode));
		else
			throw new StockNotFoundException("Invalid Company Code. No Stock available against this company code.");
	}
	//----------------------------------------------------------------------------
	public List<StockPriceDetailsDTO> getStockByCode(Long companyCode){

		List<StockPriceDetails> stockDetails = stockRepository.findStockByCompanyCode(companyCode);
		
		if(CollectionUtils.isEmpty(stockDetails))
			return null;
		else
			return stockDetails.stream().map(StockMarketUtility::convertToStockPriceDetailsDTO).collect(Collectors.toList());
	}
	//----------------------------------------------------------------------------
	public StockPriceDetailsDTO getStockPriceDetailsDTO(StockPriceDetails stockDetails)	{
		return new StockPriceDetailsDTO(stockDetails.getId(), stockDetails.getCompanyCode(), stockDetails.getCurrentStockPrice(), stockDetails.getStockPriceDate(), stockDetails.getStockPriceTime());
	}
	//----------------------------------------------------------------------------
	public Double getMaxStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockRepository.findMaxStockPrice(companyCode, startDate, endDate);
	}
	public Double getAvgStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockRepository.findAvgStockPrice(companyCode, startDate, endDate);
	}
	public Double getMinStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockRepository.findMinStockPrice(companyCode, startDate, endDate);
	}
	
	public StockPriceIndexDTO getStockPriceIndex(Long companyCode, LocalDate startDate, LocalDate endDate) {
		
		StockPriceIndexDTO stockPriceIndexDto = new StockPriceIndexDTO();
		
		CompanyDetails companyDetails = companyRepository.findCompanyDetailsById(companyCode);
		stockPriceIndexDto.setCompanyDto(StockMarketUtility.convertToCompanyDetailsDTO(companyDetails));
		
		List<StockPriceDetailsDTO> stockPriceList = getStockByCode(companyCode);
		stockPriceIndexDto.setStockPriceList(stockPriceList);

		Double maxStockPrice = getMaxStockPrice(companyCode, startDate, endDate);
		stockPriceIndexDto.setMaxStockPrice(maxStockPrice);
		
		Double avgStockPrice = getAvgStockPrice(companyCode, startDate, endDate);
		stockPriceIndexDto.setAvgStockPrice(avgStockPrice);

		Double minStockPrice = getMinStockPrice(companyCode, startDate, endDate);
		stockPriceIndexDto.setMinStockPrice(minStockPrice);

		return stockPriceIndexDto;
	}
	@Override
	public List<StockPriceDetailsDTO> getAllStocksByCompanyCode(Long companyCode) {
		
		List<StockPriceDetails> stocks = stockRepository.findStockByCompanyCode(companyCode);
		List<StockPriceDetailsDTO> stockDtos = new ArrayList<>();
		for (StockPriceDetails stock : stocks) {
			StockPriceDetailsDTO stockDto = new StockPriceDetailsDTO();
			BeanUtils.copyProperties(stock, stockDto);
			stockDtos.add(stockDto);
		}

		return stockDtos;
	}
	public CompanyStockDetailsDTO getAllStocksDetailsByCompanyCode(Long companyCode) {

		CompanyStockDetailsDTO companyStockDto = new CompanyStockDetailsDTO();
		Optional<CompanyDetails> companyDetails = companyRepository.findById(companyCode);
		if(companyDetails.isPresent()) {
			CompanyDetailsDTO companyDetailsDTO = new CompanyDetailsDTO();
			BeanUtils.copyProperties(companyDetails.get(), companyDetailsDTO);
			companyStockDto.setCompanyDto(companyDetailsDTO);
			companyStockDto.setStockPriceDTO(getStockByCode(companyCode));
			return companyStockDto;
		}
		else
			return null;
	}
}