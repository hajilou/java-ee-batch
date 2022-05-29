package samples.batch.listener;

import org.apache.log4j.Logger;

import javax.batch.api.listener.AbstractJobListener;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

@Dependent
@Named
public class GeneralJobListener extends AbstractJobListener {

    private final static Logger logger = Logger.getLogger(GeneralJobListener.class);

    @Inject
    private JobContext jobContext;
    private long startTime;

    public void beforeJob() throws Exception {
        startTime = new Date().getTime();
        logger.debug(String.format("Before job: %s\tstatus: %s\tjobStartTime: %s\texecutionId: %s\tinstanceId: %s"
                , jobContext.getJobName(), jobContext.getBatchStatus(), startTime, jobContext.getExecutionId(), jobContext.getInstanceId()));
    }

    public void afterJob() throws Exception {
        long endTime = new Date().getTime();
        logger.debug(String.format("After job: %s\tstatus: %s\tjobEndTime: %s\texecutionId: %s\tinstanceId: %s\ttimeConsumed: %sms"
                , jobContext.getJobName(), jobContext.getBatchStatus(), endTime, jobContext.getExecutionId(), jobContext.getInstanceId()
                , endTime - startTime));
    }
}
