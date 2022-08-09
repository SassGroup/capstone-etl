package com.bluepi.loan.consumer;

import com.bluepi.loan.base.IntegratorConstants;
import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import javax.inject.Singleton;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Singleton
public class ETLConsumer implements Runnable{

	private KafkaConsumer<String, String> consumer;

	public static Map<Integer, PriorityBlockingQueue<MessageOffset>> partitionInProgressQ = new HashMap<>();
	public static Map<Integer, PriorityBlockingQueue<MessageOffset>> partitionCompletedQ = new HashMap<>();
	public static Hashtable<Integer, AtomicLong> partitionOffset = new Hashtable<>();
	@Value("${kafka.consumer.partitionCount}")
    int partitionCount;
	@Value("${kafka.consumer.poll.interval}")
    int pollInterval;
	@Value("${kafka.topic.t4}")
	String topicName;



//	public static AtomicLong offset = new AtomicLong(0);

	public ETLConsumer() {
		Properties props = new Properties();
		props.put(IntegratorConstants.YAML_CONSTANTS.KAFKA_SERVER_KEY, IntegratorConstants.YAML_CONSTANTS.KAFKA_SERVER_VALUE);
		props.put(IntegratorConstants.GROUP_ID, IntegratorConstants.KAFKA_GROUP_ID);
		props.put(IntegratorConstants.ENABLE_AUTO_COMMIT, IntegratorConstants.KAFKA_ENABLE_AUTO_COMMIT);
		props.put(IntegratorConstants.AUTO_OFFSET_RESET, IntegratorConstants.KAFKA_AUTO_OFFSET_RESET);
		props.put(IntegratorConstants.KAFKA_CONSUMER_MAX_POLL_INTERVAL_IN_MILLIS, IntegratorConstants.YAML_CONSTANTS.KAFKA_MAX_POLL_INTERVAL);
		props.put(IntegratorConstants.KAFKA_CONSUMER_MAX_POLL_RECORDS, IntegratorConstants.YAML_CONSTANTS.KAFKA_MAX_POLL_RECORDS);
		props.put(IntegratorConstants.KEY_DESERIALIZER, StringDeserializer.class.getName());
		props.put(IntegratorConstants.VALUE_DESERIALIZER, StringDeserializer.class.getName());
		//props.put(IntegratorConstants.MAX_POLL_RECORDS, IntegratorConstants.KAFKA_MAX_POLL_RECORDS);

		log.info("Kafka Properties : {}", props);
		this.consumer = new KafkaConsumer<>(props);
	}



//	@PostConstruct
//	public void startPolling() {
//		log.info("Starting consumer thread for ETL");
//		final ExecutorService executorService = Executors.newSingleThreadExecutor();
//		executorService.submit(this);
//	}
	@Override
	public void run() {
		log.info("Executing KafkaPoller.call()");
		final List<String> topics = new ArrayList<>();
		topics.add(topicName);
		consumer.subscribe(topics);

		try {
			initializeOffset(partitionCount);

			while (true) {
				long startTime = System.currentTimeMillis();
//				if (log.isTraceEnabled())
				log.debug("Polling for message from topic(s) {}", topics);

				final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(pollInterval));

				for (final TopicPartition partition : records.partitions()) {
					final List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
					for (final ConsumerRecord<String, String> consumerRecord : partitionRecords) {
					//	if (log.isDebugEnabled())
					//		log.debug("Message received: {}", consumerRecord);
						handleMessage(consumerRecord);
					}
				}

				// Commit message offset for all partitions
				for (int i = 0; i < partitionCount; i++) {
					commitCompletedMessages(i);
				}

				long endTime = System.currentTimeMillis();
				//log.info("ETL_Consumer Total Time in ms : {}", (endTime - startTime));
			}
		} catch (final Exception e) {
			log.error("ETL Poller stopped", e);
			throw e;
		}
	}

	private void commitCompletedMessages(int partitionId) {
		try {
			final AtomicLong offset = partitionOffset.get(partitionId);
			if (offset.get() != 0) {
				log.info("Committing to offset {} for topic {} partition {}", offset.get() + 1, topicName, partitionId);
				consumer.commitSync(Collections.singletonMap(new TopicPartition(topicName, partitionId),
						new OffsetAndMetadata(offset.getAndSet(0) + 1)));
						//new OffsetAndMetadata(offset.getAndSet(0));
			}
		} catch (Exception ex) {
			log.error("Error in setting offset ", ex);
			throw ex;
		}
	}

	/**
	 * Handle each record consumed from Kafka and process it using
	 * IOThreadDispatcher.
	 *
	 * @param consumerRecord
	 */
	public void handleMessage(ConsumerRecord<String, String> consumerRecord) {
		addToInprogressQueue(consumerRecord);
		IntegratorConstants.exec.execute(new ETLMessageHandler(consumerRecord, IntegratorConstants.iod_AerospikeRead));
	}


	public void initializeOffset(int partitionCount) {
		for (int partitionId = 0; partitionId < partitionCount; partitionId++) {
			partitionOffset.put(partitionId, new AtomicLong(0));
			partitionInProgressQ.put(partitionId, new PriorityBlockingQueue<MessageOffset>());
			partitionCompletedQ.put(partitionId, new PriorityBlockingQueue<MessageOffset>());
		}
	}


	public MessageOffset addToInprogressQueue(ConsumerRecord<String, String> consumerRecord) {

		final MessageOffset messageOffset = MessageOffset.builder().offset(consumerRecord.offset())
				.partition(consumerRecord.partition()).topicName(consumerRecord.topic()).build();
		log.info("Adding to kafka-etl-topic queue {}", messageOffset);
		partitionInProgressQ.get(consumerRecord.partition()).offer(messageOffset);
		return messageOffset;

	}
}
