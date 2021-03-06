package io.manco.maxim.sbmm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.manco.maxim.sbmm.repository.BarRepository;
import io.manco.maxim.sbmm.repository.StockRepository;
import io.manco.maxim.sbmm.domain.Bar;
import io.manco.maxim.sbmm.domain.Stock;

@Service
public class BarService {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(BarService.class);

  @Autowired
  private BarRepository barRepository;

  @Autowired
  private StockRepository stockRepository;

  public List<Bar> getBarListStock(String stockSymbol) {
    Stock stock = stockRepository.findByName(stockSymbol);
    LOGGER.info("Request to View Data for Bar chart for symbol : {} .", stockSymbol);
    return barRepository.findByStockId(stock.getId());
  }

  public Bar getSingleBar(Long barId, String instId) {
    Stock stock = stockRepository.findByName(instId);
    return barRepository.findByMdIdAndStockId(barId, stock.getId());
  }

  public Bar update(Bar bar) {
    return barRepository.save(bar);
  }

  public Bar createBar(Bar bar) {
    return barRepository.save(bar);
  }

  public void deleteBar(Bar bar) {
    barRepository.delete(bar);
  }

  public List<String> searchTickersByChars(String tickerNameId) {

    if (StringUtils.isEmpty(tickerNameId)) {
      return Collections.emptyList();
    }

    List<Stock> stocklist = stockRepository.findByNameContaining(tickerNameId);
    List<String> stockSymbols = new ArrayList<>();
    for (Stock stock : stocklist) {
      stockSymbols.add(stock.getName());
    }
    return stockSymbols;
  }

}
