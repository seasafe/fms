package com.fms.event.watch;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fms.event.batch.EventSummaryBatchLauncher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileAdapter implements FileListener {

	

	@Override
	public void onCreated(FileEvent event,EventSummaryBatchLauncher eventSummaryBatchLauncher) {
		log.info("file created event triggered -> {}", event.getFile().getName());
		try {
			eventSummaryBatchLauncher.launchEventSummaryJob(event.getFile().getAbsolutePath());
		} catch (JobParametersInvalidException | JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onModified(FileEvent event,EventSummaryBatchLauncher eventSummaryBatchLauncher) {
		log.info("file Modified event triggered -> {}", event.getFile().getName());
		try {
			eventSummaryBatchLauncher.launchEventSummaryJob(event.getFile().getAbsolutePath());
		} catch (JobParametersInvalidException | JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDeleted(FileEvent event,EventSummaryBatchLauncher eventSummaryBatchLauncher) {
		log.info("file Delete event triggered -> {}", event.getFile().getName());
	}
}