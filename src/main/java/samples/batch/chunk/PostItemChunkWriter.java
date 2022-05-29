package samples.batch.chunk;

import org.apache.log4j.Logger;
import samples.dao.postitem.PostItemDao;
import samples.entity.PostItem;
import samples.enums.Status;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Dependent
@Named
public class PostItemChunkWriter extends AbstractItemWriter {
    private final static Logger logger = Logger.getLogger(PostItemChunkWriter.class);

    @Inject
    private PostItemDao postItemDao;

    @Inject()
    @BatchProperty(name = "partitionNumber")
    private String partitionNumberProp;

    @Override
    public void writeItems(List<Object> items) throws Exception {
        logger.trace(String.format("Partition(%s) write %s count of items: %s", partitionNumberProp, items, items.size()));
        printCurrentThread();

        logger.trace(items.size() + " of PostItem are Expired!");
        items.stream().map(PostItem.class::cast).forEach(postItem -> {
            logger.trace("WRITER " + postItem.toString());
            postItem.setStats(Status.EXPIRED);
            postItemDao.update(postItem);
        });
    }

    private void printCurrentThread() {
        Thread currentThread = Thread.currentThread();
        logger.trace(String.format("Partition(%s) -> Thread Name(%s): - Thread Id(%s)", partitionNumberProp, currentThread.getName(), currentThread.getId()));
    }
}
