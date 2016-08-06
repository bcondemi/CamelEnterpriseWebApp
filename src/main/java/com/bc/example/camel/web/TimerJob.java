package com.bc.example.camel.web;
/**
 *
 * @author bruno.condemi
 */
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple class to show how to use Apache Camel into a JEE Web Application 
 * mixing Camel API end standard JEE API. 
 * It's a Singletone EJB that in the post-constructor phase start a Camel Route
 * that print a simple string in the output log
 * @author Bruno Condemi
 */
@Singleton
@Startup
public class TimerJob {

    //Camel Context Injection
    @Inject
    SampleContext context;
    
    //EJB Injection
    @Inject
    HelloSessionBean helloEJB;

    Logger logger = LoggerFactory.getLogger(TimerJob.class);
 
    /**
     *Create the Camel Route and add the route to the injected Camel Context 
     */
    @PostConstruct
    public void init() {
        logger.info("Create CamelContext and register Camel Route.");
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("timer://timer1?period=1000")
                            .setBody()
                            .simple("Camel body to display")
                            .log(">> Response : ${body}"+helloEJB.helloMethod("Eric"));
                }
            });
        } catch (Exception ex) {
            logger.error("Error", ex);
        }

        try {
            // Start Camel Context
            context.start();
            logger.info("CamelContext created and camel route started.");
        } catch (Exception ex) {
           logger.error("Error",ex);
        }

        
    }

    @PreDestroy
    public void shutdown() {
        try {
            //Graceful Shutdown Camel Context
            context.stop();
             logger.info("CamelContext stopped");
        } catch (Exception ex) {
            logger.error("Error",ex);
        }
       
    }

}

