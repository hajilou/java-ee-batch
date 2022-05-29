package samples.batch.chunk;

import org.apache.log4j.Logger;
import samples.entity.PostItem;
import samples.enums.Status;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named
public class PostItemChunkProcessor implements ItemProcessor {
    private final static Logger logger = Logger.getLogger(PostItemChunkProcessor.class);

    @Inject()
    @BatchProperty(name = "partitionNumber")
    private String partitionNumberProp;
    private int counter;

    public PostItem processItem(Object item) {

        logger.trace(String.format("Partition(%s) process item(%s): %s", partitionNumberProp, counter++, item));
        Thread currentThread = Thread.currentThread();
        logger.trace(String.format("Partition(%s) - item(%s) -> Thread Name(%s): - Thread Id(%s)", partitionNumberProp, counter, currentThread.getName(), currentThread.getId()));

        PostItem postItem = (PostItem) item;
        postItem.setStats(Status.EXPIRED);
        return postItem;
    }

}
