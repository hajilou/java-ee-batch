package samples.batch.chunk;

import org.apache.log4j.Logger;
import samples.dao.postitem.PostItemDao;
import samples.entity.PostItem;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Dependent
@Named
public class PostItemChunkReader extends AbstractItemReader {
    private final static Logger logger = Logger.getLogger(PostItemChunkReader.class);

    private List<PostItem> postItemList;
    private int counter;
    private int maxItem;
    @Inject()
    @BatchProperty(name = "start")
    private String startProp;
    @Inject()
    @BatchProperty(name = "end")
    private String endProp;
    @Inject()
    @BatchProperty(name = "idList")
    private String idListProp;
    @Inject()
    @BatchProperty(name = "partitionNumber")
    private String partitionNumberProp;
    @Inject
    private JobContext jobContext;
    @Inject
    private StepContext stepContext;
    @Inject
    private PostItemDao postItemDao;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        logger.debug("Initializing partition number " + partitionNumberProp + "");
        logger.debug(String.format("Partition(%s) process items from %s to %s with ids(%s)", partitionNumberProp, startProp, endProp, idListProp));
        List<Long> longList = Arrays.stream(idListProp.split(",")).map(Long::parseLong).collect(Collectors.toList());
        postItemList = postItemDao.findByIdList(longList);
        counter = 0;
        maxItem = Integer.parseInt(endProp) - Integer.parseInt(startProp);
        Thread currentThread = Thread.currentThread();
        logger.debug(String.format("All items in partition(%s) run with thread name: %s - thread id: %s - executionId:%s - instanceId: %s"
                , partitionNumberProp, currentThread.getName(), currentThread.getId(), jobContext.getExecutionId(), jobContext.getInstanceId()));
    }

    @Override
    public void close() throws Exception {
        Thread currentThread = Thread.currentThread();
        logger.trace("step context is: "+stepContext.toString());
        logger.debug(String.format("Partition(%s) has been closed. thread name: %s - thread id: %s - executionId:%s - instanceId: %s",
                partitionNumberProp, currentThread.getName(), currentThread.getId(), jobContext.getExecutionId(), jobContext.getInstanceId()));
    }

    @Override
    public PostItem readItem() throws Exception {

        if (counter < maxItem) {
            // throws an error manually
//            if (counter == 15) {
//                throw new RuntimeException("Test exception");
//            }
            PostItem postItem = postItemList.get(counter);
            logger.trace(String.format("Partition(%s) read item %s is: %s", partitionNumberProp, counter, postItem));
            counter++;
            return postItem;
        }
        return null;
    }
}