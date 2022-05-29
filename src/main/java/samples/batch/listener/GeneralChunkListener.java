package samples.batch.listener;

import org.apache.log4j.Logger;

import javax.batch.api.chunk.listener.AbstractChunkListener;
import javax.batch.api.chunk.listener.ChunkListener;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

@Dependent
@Named
public class GeneralChunkListener extends AbstractChunkListener implements ChunkListener {

    private final static Logger logger = Logger.getLogger(GeneralChunkListener.class);

    @Inject
    private JobContext jobContext;
    @Inject
    private StepContext stepContext;
    private int chunkNumber;
    private long startTime;

    public void beforeChunk() throws Exception {
        startTime = new Date().getTime();
        logger.debug(String.format("Before chunk: %s\tpartition: %s\tjob: %s\tjobStartTime: %s\texecutionId: %s\tinstanceId: %s\""
                , chunkNumber, stepContext.getProperties().getProperty("partitionNumber"), jobContext.getJobName(), startTime,
                jobContext.getExecutionId(), jobContext.getInstanceId()));
    }

    public void afterChunk() throws Exception {
        long endTime = new Date().getTime();
        logger.debug(String.format("After chunk: %s\tpartition: %s\tjob: %s\tjobEndTime: %s\texecutionId: %s\tinstanceId: %s\ttimeConsumed: %sms",
                chunkNumber++, stepContext.getProperties().getProperty("partitionNumber"), jobContext.getJobName(),
                endTime, jobContext.getExecutionId(), jobContext.getInstanceId(), endTime - startTime));
        if (stepContext.getException() != null) {
            logger.warn(String.format("An error occurred in chunk: %s\tpartition: %s\tjob: %s\ttexecutionId: %s\tinstanceId: %s",
                    chunkNumber++, stepContext.getProperties().getProperty("partitionNumber"), jobContext.getJobName(),
                    jobContext.getExecutionId(), jobContext.getInstanceId()));
            logger.warn("An error occurred in ");
            logger.warn("Error is: " + stepContext.getException().getMessage());
        }
    }


    public void onError(Exception ex) throws Exception {
        logger.error(String.format("An error acquired on  chunk: %s\tpartition: %s\tjob: %s\texecutionId: %s\tinstanceId: %s",
                chunkNumber, stepContext.getProperties().getProperty("partitionNumber"), jobContext.getJobName(),
                jobContext.getExecutionId(), jobContext.getInstanceId()));
    }

}
