package samples.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import java.util.Properties;

public class PostItemJob implements Job {
    private final static Logger logger = Logger.getLogger(PostItemJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("PostItem job has been starting...");
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("postItemChunk", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        logger.info("PostItem job executed with id: " + jobExecution.getExecutionId() + " and name: " + jobExecution.getJobName());
    }
}
