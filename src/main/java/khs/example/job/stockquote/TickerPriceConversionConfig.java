package khs.example.job.stockquote;

import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@Configuration
public class TickerPriceConversionConfig {
 
    @Autowired
    private JobBuilderFactory jobs;
 
    @Autowired
    private StepBuilderFactory steps;
 
    @Bean
    public ItemReader<TickerData> reader() throws MalformedURLException {
        FlatFileItemReader<TickerData> reader = new FlatFileItemReader<TickerData>();
        reader.setResource( new ClassPathResource("stockquotes.csv") );
        reader.setLineMapper(new DefaultLineMapper<TickerData>() {{
            setLineTokenizer(new DelimitedLineTokenizer());
            setFieldSetMapper(new TickerFieldSetMapper());
        }});
        return reader;
    }
 
    @Bean
    public ItemProcessor<TickerData, TickerData> processor() {
        return new TickerPriceProcessor();
    }
 
    @Bean
    public ItemWriter<TickerData> writer() {
        return new LogItemWriter();
    }
 
    @Bean("stockticker")
    public Job TickerPriceConversion() throws MalformedURLException {
        return jobs.get("TickerPriceConversion").start(convertPrice()).build();
    }
 
    @Bean
    public Step convertPrice() throws MalformedURLException {
        return steps.get("convertPrice")
                .<TickerData, TickerData> chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}