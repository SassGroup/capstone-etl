package com.bluepi.loan.consumer;


import com.bluepi.loan.base.IntegratorConstants;
import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Singleton
public class QueueOffSetHandler implements Runnable{

    @Inject
    ETLConsumer etlConsumer;

    @Value("${kafka.offset.poll.interval}")
    public int pollInterval;
   // private int pollInterval = IntegratorConstants.KAFKA_CONSUMER_OFFSET_INTERVAL;

    @Value("${kafka.consumer.partitionCount}")
    public int partitionCount;

    @Override
    public void run() {
        log.info("ETL Offset handler poller started...");
        while (1 > 0) {
            try {
                Thread.sleep(pollInterval);
                if (log.isDebugEnabled())
                    log.debug("Polling for etl-kafka-topic Offset {}",pollInterval);
                for (int partitionId = 0; partitionId < partitionCount; partitionId++) {
                    PriorityBlockingQueue<MessageOffset> inProgressQ = etlConsumer.partitionInProgressQ.get(partitionId);
                    PriorityBlockingQueue<MessageOffset> completedQ = etlConsumer.partitionCompletedQ.get(partitionId);
                    if (log.isDebugEnabled()) {
                        log.debug("Partition {}, inprogress queue : {}", partitionId, inProgressQ);
                        log.debug("Partition {}, completed queue : {}", partitionId, completedQ);
                    }
                    MessageOffset messageOffset = null;
                    while (inProgressQ.peek() != null
                            && inProgressQ.peek().equals(completedQ.peek())) {
                        messageOffset = inProgressQ.poll();
                      //  log.info("Taken off from inprogress queue {}", messageOffset);
                        messageOffset = completedQ.poll();
                      //  log.info("Taken off from completed queue {}", messageOffset);
                    }
                    if (messageOffset != null) {
                        log.info("Setting offset for Partition {} with offset {}", partitionId, messageOffset.getOffset());
                        final AtomicLong offset = etlConsumer.partitionOffset.get(partitionId);
                        offset.set(messageOffset.getOffset());
                    }
                }
            } catch (final InterruptedException ex) {
                try {
                    throw ex;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.error("QueueOffSetHandler thread interrupted ", ex);
            }
        }
    }
}
