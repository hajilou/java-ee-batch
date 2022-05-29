package samples.batch.batchlet;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchStatus;
import javax.inject.Named;

@Named
public class SimpleBatchLet extends AbstractBatchlet {
 
    @Override
    public String process() throws Exception {

        return BatchStatus.COMPLETED.toString();
    }
}