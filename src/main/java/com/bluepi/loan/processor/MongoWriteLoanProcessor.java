package com.bluepi.loan.processor;

import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.model.MessagePayload;
import com.bluepi.loan.consumer.MessageOffset;
import com.bluepi.loan.dao.LoanOperationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Map;

public class MongoWriteLoanProcessor implements Runnable {

    private Logger log = LoggerFactory.getLogger(MongoWriteLoanProcessor.class);

    private Map<String, Object> contextMap;
    /**
     * @return
     */
    @Override
    public void run() {

        log.info("Processing request inside call for Aerospike read----------");
         MessagePayload msgPayload = (MessagePayload) contextMap.get(IntegratorConstants.DATA);
         String applicationId= (String) contextMap.get(IntegratorConstants.APPLICATION_ID);

         String UUID = (String) contextMap.get(IntegratorConstants.UNIQUE_ID);
         MessageOffset messageOffset = (MessageOffset) contextMap.get(IntegratorConstants.MESSAGE_OFFSET);
         MDC.put(IntegratorConstants.UNIQUE_ID,UUID);
         MDC.put(IntegratorConstants.APPLICATION_ID,applicationId);

        log.info("Processed request inside call Aerospike read----------");
        try {
            LoanOperationDao.getInstance().pushToReadDB(applicationId,msgPayload);
        } catch (IOException e) {
            log.error("MongoWriteLoanProcessor run() error : {}",e);
            e.printStackTrace();
        }
        log.info("Process Completed");
        //TODO Added as per discussion with Prashant must be committed --->
        //QueueOffSetHandler.completedQ.offer(messageOffset);
        MDC.clear();
        return;
    }

    public String getName() {
        return "ETL Aerospike read IO Thread Dispatcher";
    }

    public void setContextMap(Map<String, Object> contextMap) {
        this.contextMap = contextMap;
    }

}
