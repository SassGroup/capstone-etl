package com.bluepi.loan.base;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.aerospike.client.Host;
import io.micronaut.context.ApplicationContext;

public class IntegratorConstants {

	public static final ApplicationContext APPLICATION_CONTEXT_INSTANCE = ApplicationContext.run();
	public static final ETLConstantsYml YAML_CONSTANTS = APPLICATION_CONTEXT_INSTANCE.getBean(ETLConstantsYml.class);

	Host[] AEROSPIKE_HOST_LIST = IntegratorConstants.YAML_CONSTANTS.aerospikeHostList();

//	public static final String AEROSPIKE_LOCAL_HOST= "localhost";
//	public static final int AEROSPIKE_PORT= 3000;

	public static ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);

	public static final String SERVICE_EXECUTOR = "service-executor";

	public static final String DATA = "data";

	public static final int AEROSPIKE_READ_CORE_POOL_SIZE = 20;

	public static final int AEROSPIKE_READ_MAX_POOL_SIZE = 50;

	public static final int AEROSPIKE_READ_KEEP_ALIVE_TIME = 1;

	public static final String AEROSPIKE_READ_POOL_NAME = "aerospike-read-push-service";

	public static final int AEROSPIKE_READ_MAX_WAIT_INPUT_QUEUE= 1;

	public static final String KAFKA_TOPIC = "etl-kafka-topic";

//	public static final int KAFKA_CONSUMER_PARTITION = 1;
//	public static final int KAFKA_CONSUMER_POLL_INTERVAL = 500;
//	public static final int KAFKA_CONSUMER_OFFSET_INTERVAL = 30000;
//	public static final int KAFKA_CONSUMER_MAX_POLL_INTERVAL_IN_MILLIS_VALUE = 60000;
//	public static final int KAFKA_CONSUMER_MAX_POLL_RECORDS_VALUE = 200;

	//public static final int KAFKA_CONSUMER_OFFSET_INTERVAL = 1000;

//	public static final int KAFKA_CONSUMER_THREAD_COUNT = 2;

	public static final String COMPLETABLE_FUTURE = "COMPLETABLE_FUTURE";

	public static final String KAFKA_INTERNAL_UPDATE_TOPIC = "internal-update-kafka-topic";
//	public static final String RETRYCOUNTER = "";

	public static final String APPLICATION_ID ="applicationId" ;
	public static final String MESSAGE_OFFSET = "messageOffset";

	public static final String BOOTSTRAP_SERVERS ="bootstrap.servers" ;

	public static final String GROUP_ID ="group.id" ;

	public static final String ENABLE_AUTO_COMMIT ="enable.auto.commit" ;

	public static final String AUTO_OFFSET_RESET ="auto.offset.reset" ;

	public static final String KEY_DESERIALIZER ="key.deserializer" ;

	public static final String VALUE_DESERIALIZER ="value.deserializer" ;

	public static final String RETRIES = "max.in.flight.requests.per.connection";

	public static final String EVENT_CREATE ="create" ;

	public static final String EVENT_UPDATE ="update" ;

	public static final String EVENT_NAME ="event_name" ;

	public static final String DATA_CHANGED="data_changed" ;

	public static final String EVENT_TIMESTAMP="event_Timestamp" ;

	public static final String UNIQUE_ID ="UUID";
	public static final String KAFKA_BOOTSTRAP_SERVERS = "localhost:9092";

	//public static final String AEROSPIKE_READ_SCHEMA = ETLConstantsYml.AEROSPIKE_COMMON_NAMESPACE;
	public static final String AEROSPIKE_READ_PRODUCT_MASTER = "product_metadata";
	public static final String AEROSPIKE_PROD_MASTER_PROD_CON_KEY = "prod_cfg_key";

	public static final String AEROSPIKE_RW_SCHEMA_LOAN_APPLICATION_TABLE_R = "Loan_application_data_r";

	public static final String REQUEST_APPLICANT 		= "applicantInfo";
	public static final String REQUEST_COAPPLICANT 		= "coApplicantInfo";
	public static final String REQUEST_PROD_CONF_KEY 	= "productConfigKey";
	public static final String REQUEST_STAGE 			= "stageInfo";
	public static final String REQUEST_TIMESTAMP 		= "requestTimestamp";

	public static final String AEROSPIKE_LAD_BIN_APPLICANT 		= "applicant";
	public static final String AEROSPIKE_LAD_BIN_COAPPLICANT 	= "coApplicant";
	public static final String AEROSPIKE_LAD_BIN_UPDATE_TIME 	= "updateTime";
	public static final String AEROSPIKE_LAD_BIN_CREATED_TIME 	= "createdTime";
	public static final String AEROSPIKE_LAD_BIN_STAGE 			= "stage";
	public static final String AEROSPIKE_LAD_BIN_PROD_CONF_KEY 	= "prodConKey";
	public static final String AEROSPIKE_LAD_BIN_LOAN_APP_DATA 	= "loanAppData";
	public static final String AEROSPIKE_LAD_BIN_LOAN_APP_ID 	= "loanAppID";
	public static final String AEROSPIKE_LAD_BIN_LOAN_APP_SUMMARY	= "loanApplicationInfo";



	//public static final String KAFKA_BOOTSTRAP_SERVERS = "localhost:9092";

	public static final String KAFKA_CONSUMER_MAX_POLL_INTERVAL_IN_MILLIS = "max.poll.interval.ms";
	public static final String KAFKA_CONSUMER_MAX_POLL_RECORDS = "max.poll.records";
	public static final String KAFKA_RETRY_TOPIC = "";


	public static final String KAFKA_GROUP_ID ="etlConsumer";

	public static final String KAFKA_ENABLE_AUTO_COMMIT ="true";

	public static final String KAFKA_AUTO_OFFSET_RESET ="latest";

	public static final String REQUEST_APPLICANTID = "applicantId";

	public static int ORACLE_CONNECTION_TIMEOUT = 20000;
	public static boolean ORACLE_AUTO_COMMIT = false;

	public static String CACHE_PREPARED_STATEMENT = "cachePrepStmts";
	public static boolean CACHE_PREPARED_STATEMENT_VALUE = true;

	public static String PREPARED_STATEMENT_CACHE_SIZE = "prepStmtCacheSize";
	public static String PREPARED_STATEMENT_CACHE_SIZE_VALUE = "250";

	public static String PREPARED_STATEMENT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
	public static String PREPARED_STATEMENT_CACHE_SQL_LIMIT_VALUE = "2048";

	public static final String ORACLE_SAVE_TYPE = "UPDATE";
	public static final String ORACLE_BIN_LAE_ENTITY_ID = "entity_id";
	public static final String ORACLE_BIN_LAE_STAGE = "stage";
	public static final String ORACLE_BIN_LAE_SUBSTAGE= "substage";
	public static final String ORACLE_BIN_LAE_PRODUCT_KEY = "product_key";
	public static final String ORACLE_BIN_LAE_LOAN_APPLICATION_DATA = "loanApplicationData";

	public static final String ORACLE_BIN_AE_LOAN_APPLICATION_ID =  "loanApplicationId";
	public static final String ORACLE_BIN_AE_ENTITY_ID = "entity_id";
	public static final String ORACLE_BIN_AE_MOBILE_NUMBER =  "mobileNumber";
	public static final String ORACLE_BIN_AE_PAN_NUMBER =  "panNumber";
	public static final String ORACLE_BIN_AE_AADHAR_NUMBER =  "aadharNumber";
	public static final String ORACLE_BIN_AE_ELIGIBILITY_STATUS =  "eligiblity_status";
	public static final String ORACLE_BIN_AE_APPLICANT_DATA =   "applicant_data";

	public static final String ORACLE_BIN_CAE_ENTITY_ID = "entity_id";
	public static final String ORACLE_BIN_CAE_COAPPLICANT_DATA=  "coApplicant_data";
	public static final String ORACLE_BIN_CAE_APPLICANTID=  "applicant_id";
	public static final String ORACLE_BIN_CAE_MOBILE_NUMBER =  "mobileNumber";
	public static final String ORACLE_BIN_CAE_PAN_NUMBER =  "panNumber";
	public static final String ORACLE_BIN_CAE_AADHAR_NUMBER =  "aadharNumber";
	public static final String ORACLE_BIN_CAE_ELIGIBILITY_STATUS =  "eligiblity_status";


	public static final String ORACLE_TABLE_LOAN_APPLICATION_ENTITY= "Loan_Application_Entity";
	public static final String ORACLE_TABLE_APPLICANT_ENTITY= "Applicant_Entity";
	public static final String ORACLE_TABLE_COAPPLICANT_ENTITY= "CoApplicant_Entity";
	public static final String ORACLE_TABLE_LOAN_APPLICATION_AUDIT= "Loan_Application_Audit";
	public static final String ORACLE_TABLE_LOAN_APPLICATION_INCREMENTAL= "Loan_Application_Increment";

	public static final String REQUEST_COAPPLICANTID=  "applicantId";

	public static final String ORACLE_BIN_CREATED_TIME =  "createdTime";
	public static final String ORACLE_BIN_UPDATED_TIME =  "updatedTime";

	public static final String ORACLE_BIN_LAA_LOAN_APPLICATION_ID = "loanApplicationId" ;
	public static final String ORACLE_BIN_LAA_LOAN_EVENTID = "eventId" ;
	public static final String ORACLE_BIN_LAA_LOAN_EVENTOBJECT= "eventObject" ;
	public static final String ORACLE_BIN_LAA_CREATED_TIME= "createdTime";
	public static final String ORACLE_BIN_LAE_LOAN_APPLICATION_INCREMENTAL="incremental";
	public static final String ORACLE_BIN_LAA_LAST_UPDATED_TIME= "lastUpdatedTime";

	public static final String KAFKA_SERVER_KEY = "bootstrap.servers";
	public static final String KAFKA_SERVER_VALUE = "localhost:9092";
	public static final String KAFKA_KEY_SERIALIZER_KEY = "key.serializer";
	public static final String KAFKA_KEY_SERIALIZER_VALUE = "org.apache.kafka.common.serialization.StringSerializer";
	public static final String KAFKA_VALUE_SERIALIZER_KEY = "value.serializer";
	public static final String KAFKA_VALUE_SERIALIZER_VALUE = "org.apache.kafka.common.serialization.StringSerializer";
	public static ThreadPoolExecutor iod_AerospikeRead = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);


	//mongodb
    public static String mongodb_url = "mongodb+srv://system:admin123@cluster1.nsjzv.mongodb.net/test";

    public static String mongodb_application_data_database = "application_data";
    public static String mongodb_applications_collection = "applications";
    public static String mongodb_applications_application_id = "application_id";
    public static String mongodb_applications_prod_config_key = "prod_config_key";
    public static String mongodb_applications_data = "data";
    public static String mongodb_applications_applicant_id = "applicant_id";
}
