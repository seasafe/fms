/**
 * 
 */
package com.fms.event.batch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PushbackInputStream;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.Sheet;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fms.event.batch.dto.EventSummaryDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kesavalu
 *
 */
public class EventSummaryReader<T> extends PoiItemReader<T> {

	@Override
	protected Sheet getSheet(int sheet) {
		// TODO Auto-generated method stub
		return super.getSheet(0);
	}
	
	

	@BeforeStep
	public void beforeStep(final StepExecution stepExecution) throws FileNotFoundException {
		JobParameters jobParameters = stepExecution.getJobParameters();
		this.setResource(new InputStreamResource(
				new PushbackInputStream(new FileInputStream(jobParameters.getString("inputFilePath")))));
		
	}

}
