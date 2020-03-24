/**
 * 
 */
package com.fms.event.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kesavalu
 *
 */
@Component
@Slf4j
public class EventSummaryBatchLauncher {

	
	private final Job job;

    private final JobLauncher jobLauncher;

    @Autowired
    EventSummaryBatchLauncher(@Qualifier("excelFileToDatabaseJob") Job job, JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

   public void launchEventSummaryJob(String filePath) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.info("Starting event summary batch job");

        jobLauncher.run(job, newExecution(filePath));

        log.info("Stopping event summary batch job");
    }

    private JobParameters newExecution(String filePath) {
        Map<String, JobParameter> parameters = new HashMap<>();

        JobParameter dateParameter = new JobParameter(new Date());
        JobParameter filePathParameter = new JobParameter(filePath);
        parameters.put("currentTime", dateParameter);
        parameters.put("inputFilePath", filePathParameter);
        return new JobParameters(parameters);
    }
}
