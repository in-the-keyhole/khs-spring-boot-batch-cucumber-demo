package khs.example.job.stockquote;

import java.math.BigDecimal;
import java.util.Currency;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("tickerPriceProcessor")
public class TickerPriceProcessor implements ItemProcessor<TickerData, TickerData> {
 
    @Autowired
    private CurrencyConversionService conversionService;
 
    @Override
    public TickerData process(TickerData ticker) throws Exception {
 
       BigDecimal openGBP = conversionService.convertCurrency(ticker.getOpen(), 100.00, 1.0);
       BigDecimal lastTradeGBP =    conversionService.convertCurrency(ticker.getLastTrade(), 200.00, 2.0);
 
        ticker.setOpenGBP(openGBP);
        ticker.setLastTradeGBP(lastTradeGBP);
 
        return ticker;
    }
 
}
