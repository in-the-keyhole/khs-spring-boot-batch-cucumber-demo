package khs.example.job.stockquote;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class CurrencyConversionService {
	
	
	 public BigDecimal convertCurrency(BigDecimal a, double b, double c) {
		 
		 return new BigDecimal(b);
		 
	 }
	

}
