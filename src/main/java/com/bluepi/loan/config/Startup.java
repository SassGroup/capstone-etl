package com.bluepi.loan.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.bluepi.loan.consumer.ETLConsumer;
import com.bluepi.loan.consumer.QueueOffSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
@Singleton
public class Startup {
    @Inject
    ETLConsumer idfcConsumer;
    @Inject
    QueueOffSetHandler queueOffSetHandler;
    private static final Logger logger = LoggerFactory.getLogger(Startup.class);
    @PostConstruct
    public void showMsg() {
        logger.info("Inside KafkaDynamicDocumentConfig:showMsg()");
    }
    @EventListener
    void onStartup(ServerStartupEvent event) {
        // 3 Threads initiation of kafka consumer, QueueOffset handler and Caching Process Thread
        logger.info("Starting the IDFC Consumer and QueueOffsetHandler Threads");
        ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        exec.submit(idfcConsumer);
        exec.submit(queueOffSetHandler);
    }
}
