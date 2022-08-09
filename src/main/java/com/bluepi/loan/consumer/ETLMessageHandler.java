package com.bluepi.loan.consumer;

import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.model.MessagePayload;
import com.bluepi.loan.processor.MongoWriteLoanProcessor;
//import com.bluepi.loan.processor.RDBMSLoanProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class ETLMessageHandler implements Runnable {

	ConsumerRecord record;
	ThreadPoolExecutor iodAerospikeRead;

	public ETLMessageHandler(ConsumerRecord record,ThreadPoolExecutor iodAerospikeRead) {
		this.record = record;
		this.iodAerospikeRead = iodAerospikeRead;
	}


	@Override
	public void run() {
		long start = System.currentTimeMillis();
		MessageOffset messageOffset = null;
		try {
			log.info("Processing message {},  on thread {}", record, Thread.currentThread().getName());
			messageOffset = MessageOffset.builder().offset(record.offset()).partition(record.partition())
					.topicName(record.topic()).build();
			String payload = (String) record.value();
			MessagePayload mp = new ObjectMapper().readValue(payload, MessagePayload.class);
			MDC.put(IntegratorConstants.UNIQUE_ID, mp.getUuid());
			MDC.put(IntegratorConstants.APPLICATION_ID, mp.getApplicationId());
			//MDC.put(IntegratorConstants.RETRYCOUNTER,mp.getRetryCounter());
			log.info("Received payload in ETL........." + mp.getPayload());
			executeCacheReadRequest(mp.getApplicationId(), mp, mp.getUuid(), messageOffset);
			processRDBMSRequest(mp.getApplicationId(),mp,mp.getUuid(),messageOffset);

			log.info("data insertion done in aerospike in ");
			///todo confirm with joseph
			//QueueOffSetHandler.completedQ.offer(messageOffset);
			addToCompletedQueue(messageOffset);

		} catch (Exception ex) {
			log.error("unable to push data into Aerospike read and Mysql schema due to exception", ex);
			addToCompletedQueue(messageOffset);
		}
		long end = System.currentTimeMillis();
		log.info("Total time {} ms",(end - start));
		MDC.clear();
		return;
	}

	private void processRDBMSRequest(String applicationId, MessagePayload messagePayload,String UUID, MessageOffset messageOffset) {

		///todo set the constants for IOThreadDispatcher using application.yml
		//IOThreadDispatcher<Map<String, Object>> iodMysql = new IOThreadDispatcher<Map<String, Object>>(1, 1, 1, "mysql-push-service", 1);
		MDC.put(IntegratorConstants.UNIQUE_ID,UUID);
		MDC.put(IntegratorConstants.APPLICATION_ID,applicationId);
		log.info("IO Created for oracle....");


//		RDBMSLoanProcessor rdbmsLoanProcessor = new RDBMSLoanProcessor();
//		Map<String, Object> rdbmsProcessorContextMap = new HashMap<>();
//		rdbmsProcessorContextMap.put(IntegratorConstants.SERVICE_EXECUTOR, rdbmsLoanProcessor);
//		rdbmsProcessorContextMap.put(IntegratorConstants.DATA, messagePayload);
//		rdbmsProcessorContextMap.put(IntegratorConstants.APPLICATION_ID, applicationId);
//		rdbmsProcessorContextMap.put(IntegratorConstants.UNIQUE_ID,UUID);
//		rdbmsProcessorContextMap.put(IntegratorConstants.MESSAGE_OFFSET,messageOffset);
//
//		rdbmsLoanProcessor.setContextMap(rdbmsProcessorContextMap);
//
//		iodAerospikeRead.execute(rdbmsLoanProcessor);

		log.info("Request thread Released for oracle.......");

	}

	private void executeCacheReadRequest(String applicationId, MessagePayload msgPayload,String UUID, MessageOffset messageOffset){
		///todo set the constants for IOThreadDispatcher using application.yml
		MDC.put(IntegratorConstants.UNIQUE_ID,UUID);
		MDC.put(IntegratorConstants.APPLICATION_ID,applicationId);

		log.info("IO Created for Aerospike read....");

		MongoWriteLoanProcessor aerospikeReadLoanProcessor = new MongoWriteLoanProcessor();
		Map<String, Object> aerospikeReadProcessorContextMap = new HashMap<>();
		aerospikeReadProcessorContextMap.put(IntegratorConstants.SERVICE_EXECUTOR, aerospikeReadLoanProcessor);
		aerospikeReadProcessorContextMap.put(IntegratorConstants.DATA, msgPayload);
		aerospikeReadProcessorContextMap.put(IntegratorConstants.APPLICATION_ID, applicationId);

		aerospikeReadProcessorContextMap.put(IntegratorConstants.UNIQUE_ID,UUID);
		aerospikeReadProcessorContextMap.put(IntegratorConstants.MESSAGE_OFFSET,messageOffset);


		aerospikeReadLoanProcessor.setContextMap(aerospikeReadProcessorContextMap);

		iodAerospikeRead.execute(aerospikeReadLoanProcessor);
		log.info("Request thread Released for aerospike read........");

	}

	private void addToCompletedQueue(MessageOffset messageOffset) {
		log.info("Message offset {}", messageOffset);

		PriorityBlockingQueue<MessageOffset> completedQ = ETLConsumer.partitionCompletedQ.get(messageOffset.getPartition());
		completedQ.offer(messageOffset);

	}


}
