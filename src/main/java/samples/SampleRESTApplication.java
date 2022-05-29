package samples;

import org.apache.log4j.Logger;
import samples.controllers.PostItemController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/api")
public class SampleRESTApplication extends Application {
    private final static Logger logger = Logger.getLogger(Application.class);
    public SampleRESTApplication() {

//        try {
//            JobDetail job = JobBuilder.newJob(HBDBatchRunnerJob.class)
//                    .withIdentity("myJobName", "group1")
//                    .build();
//
//            Trigger trigger = TriggerBuilder
//                    .newTrigger()
//                    .withIdentity("mrTriggerName", "myGroupName")
//                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ?"))
//                    .build();
//
//            JobDetail postItemJob = JobBuilder.newJob(PostItemJob.class)
//                    .withIdentity("postItemJonName", "group1")
//                    .build();
//
//            //schedule it
//            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//            scheduler.start();
//            scheduler.scheduleJob(postItemJob, trigger);
//
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);

        // Add Jackson feature.
        resources.add(org.glassfish.jersey.jackson.JacksonFeature.class);

        return resources;
    }


    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(PostItemController.class);
    }

}
