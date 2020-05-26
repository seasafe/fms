package com.fms.event.batch;

import java.io.FileNotFoundException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.fms.event.batch.dto.EventSummaryDTO;
import com.fms.event.model.Event;


@Configuration
public class EventSummaryBatchConfig {


	@Autowired
	Environment env;
	@Bean
	public ItemReader<EventSummaryDTO> excelEventSummaryReader() throws FileNotFoundException {
		EventSummaryReader<EventSummaryDTO> reader = new EventSummaryReader<>();
		reader.setLinesToSkip(1);
		reader.setUseDataFormatter(true);
		reader.setRowMapper(eventSummaryExcelRowMapper());
		
	    return reader;
	}
	
	
	private RowMapper<EventSummaryDTO> eventSummaryExcelRowMapper() {
		return new EventSummaryExcelRowMapper();
	}
	/*
	 * private RowMapper<EventSummaryDTO> eventSummaryExcelRowMapper() {
	 * BeanWrapperRowMapper<EventSummaryDTO> rowMapper = new
	 * BeanWrapperRowMapper<>(); rowMapper.setTargetType(EventSummaryDTO.class);
	 * return rowMapper; }
	 */

    

    @Bean
    ItemProcessor<EventSummaryDTO, Event> excelEventSummaryProcessor() {
        return new EventSummaryProcessor();
    }

    @Bean
    ItemWriter<Event> excelEventSummaryWriter() {
        return new EventSummaryWriter();
    }

    @Bean
    Step eventSummaryUploadStep(ItemReader<EventSummaryDTO> excelEventSummaryReader,
                                 ItemProcessor<EventSummaryDTO, Event> excelEventSummaryProcessor,
                                 ItemWriter<Event> excelEventSummaryWriter,
                                 StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("eventSummaryUploadStep")
                .<EventSummaryDTO, Event>chunk(10)
                .reader(excelEventSummaryReader)
                .processor(excelEventSummaryProcessor)
                .writer(excelEventSummaryWriter)
                .build();
    }

    @Bean
    Job excelFileToDatabaseJob(JobBuilderFactory jobBuilderFactory,
                               @Qualifier("eventSummaryUploadStep") Step eventSummaryUploadStep) {
        return jobBuilderFactory.get("excelFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .flow(eventSummaryUploadStep)
                .end()
                .build();
    }
}
