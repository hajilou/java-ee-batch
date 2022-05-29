package samples.controllers;


import org.apache.log4j.Logger;
import samples.dao.postitem.PostItemDao;
import samples.entity.PostItem;
import samples.enums.PostItemType;
import samples.enums.Status;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@RequestScoped
@Path("/post-item")
@Produces("application/json")
@Consumes("application/json")
public class PostItemController {

    private final static Logger logger = Logger.getLogger(PostItemController.class);

    @Inject
    private PostItemDao postItemDao;

    @POST
    @Path("/send")
    public Response send(final PostItem postItem) {
        logger.info("User register one PostItem");
        postItem.setReceiveDate(new Date());
        postItemDao.insert(postItem);
        return Response.noContent().build();
    }


    @GET
    @Path("/fake-data")
    public Response generateFakeData() {
        logger.info("Generating fake data...");
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            PostItem postItem = new PostItem();
            String phone = String.format("%04d", random.nextInt(1000));
            postItem.setSenderPhone(phone);
            postItem.setReceiverPhone(phone);
            postItem.setPostItemType(PostItemType.LETTER);
            postItem.setLabel("Test label number: " + i);
            postItem.setReceiveDate(new Date());
            if (random.nextBoolean()) {
                postItem.setStats(Status.NOT_DELIVERED);
            } else {
                postItem.setStats(Status.DELIVERED);
            }
            try {
                postItemDao.insert(postItem);
            } catch (Exception ignore) {
            }
        }
        logger.info("Fake data generated successfully.");
        return Response.ok("Fake data generated successfully").build();
    }

    @GET
    @Path("/run-job")
    public Response runJob() {
        logger.info("Received request by rest for run job.");
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("postItemChunk", new Properties());
        return Response.ok("postItemChunk Started with executionId: " + executionId).build();
    }
}
