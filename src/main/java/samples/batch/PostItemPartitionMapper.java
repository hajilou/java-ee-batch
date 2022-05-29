package samples.batch;

import org.apache.log4j.Logger;
import samples.dao.postitem.PostItemDao;

import javax.batch.api.partition.PartitionMapper;
import javax.batch.api.partition.PartitionPlan;
import javax.batch.api.partition.PartitionPlanImpl;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Dependent
@Named
public class PostItemPartitionMapper implements PartitionMapper {

    private final static Logger logger = Logger.getLogger(PostItemPartitionMapper.class);
    private List<Long> postItemIdList = new ArrayList<>();
    private int chunkSize = 20;
    private int threadCount = 3;
    private int totalRecords = 0;
    // partition parameter
    public final String POST_ITEM_ID_LIST = "idList";
    public final String START_PROP = "start";
    public final String END_PROP = "end";
    public final String PARTITION_NUMBER = "partitionNumber";

    @Override
    public PartitionPlan mapPartitions() throws Exception {
        logger.debug("Creating partitions...");
        logger.debug("Use " + threadCount + " number of thread for execute partitions");
        int numberOfPartitions = 0;
        int totalRecords = 0;

        // get required data
        init();

        totalRecords = postItemIdList.size();

        /*
         * Calculating number of partitions based on the chunk size and
         * total number of records
         */
        if (totalRecords <= chunkSize) {
            numberOfPartitions = 1;
        } else {
            float realNum = ((float) totalRecords / (float) chunkSize);
            numberOfPartitions = ((int) Math.ceil(realNum));
        }
        logger.debug("Create " + numberOfPartitions + " partition");
        logger.debug("Every partition can process " + chunkSize + " items");
        PartitionPlanImpl partitionPlan = new PartitionPlanImpl();
        partitionPlan.setPartitions(numberOfPartitions);
        partitionPlan.setThreads(threadCount);//core -1
        // Put list of batch records id(s) into properties of each partition
        setPartitionPlanProperties(numberOfPartitions, partitionPlan);
        return partitionPlan;
    }


    @Inject
    private PostItemDao postItemDao;

    public void init() throws Exception {
        postItemIdList = postItemDao.findAllIdNotDeliveredOrderBYReceiveDateDesc();
        totalRecords = postItemIdList.size();
        logger.debug("All partitions should be process " + totalRecords + " items.");
    }

    private void setPartitionPlanProperties(int numberOfPartitions, PartitionPlanImpl partitionPlan) {
        Properties[] partitionProperties = new Properties[numberOfPartitions];
        int first = 0;
        int last = totalRecords <= chunkSize ? totalRecords - 1 : chunkSize - 1;

        for (int i = 0; i < numberOfPartitions; i++) {
            //
            Properties properties = new Properties();
            String idList = postItemIdList.subList(first, last + 1).stream().map(x -> x + "").collect(Collectors.joining(","));
            properties.setProperty(POST_ITEM_ID_LIST, idList);
            properties.setProperty(PARTITION_NUMBER, i + "");
            properties.setProperty(START_PROP, first + "");
            properties.setProperty(END_PROP, last + "");
            partitionProperties[i] = properties;
            partitionPlan.setPartitionProperties(partitionProperties);

            logger.debug(String.format("Partition(%02d) process %s number of items, from %3s to %3s with ids(%s)", i, last - first + 1, first, last, idList));
            first += chunkSize;
            last += chunkSize;
            if (last >= totalRecords) {
                last = totalRecords - 1;
            }
        }
    }

}