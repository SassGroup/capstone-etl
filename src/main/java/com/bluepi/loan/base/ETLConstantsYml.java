package com.bluepi.loan.base;

import javax.inject.Singleton;

import com.aerospike.client.Host;

import io.micronaut.context.annotation.Value;


@Singleton
public class ETLConstantsYml {


    //Kafka Constants

    @Value("${kafka.server.key}")
    public String KAFKA_SERVER_KEY;

    @Value("${kafka.server.value}")
    public String KAFKA_SERVER_VALUE;

    @Value("${kafka.max.poll.records}")
    public int KAFKA_MAX_POLL_RECORDS;

    @Value("${kafka.consumer.poll.interval}")
    public int KAFKA_CONS_POLL_INT;

    @Value("${kafka.consumer.thread.count}")
    public int KAFKA_CONS_THR_CONT;

    @Value("${kafka.max.poll.interval.ms}")
    public int KAFKA_MAX_POLL_INTERVAL;

//    @Value("${kafka.offset.poll.interval}")
//    public int KAFKA_OFFSET_POLL_INTERVAL;

    @Value("${kafka.consumer.partitionCount}")
    public int KAFKA_CONS_PARTITION_COUNT;




    //Aerospike Constants

    @Value("${aerospike.namespace}")
    public String AEROSPIKE_DB_NAMESPACE;

    @Value("${aerospike.host}")
    public String AEROSPIKE_CLIENT_HOST;

    @Value("${aerospike.port}")
    public String AEROSPIKE_CLIENT_PORT;

    @Value("${aerospike.user.authentication.enabled}")
    public int FLAG_FOR_DEV;

    @Value("${aerospike.username}")
    public String AEROSPIKE_DEV_USERNAME;

    @Value("${aerospike.password}")
    public String AEROSPIKE_DEV_PASSWORD;

    @Value("${aerospike.rwPolicy.readPolicyDefault.totalTimeout}")
    public int AEROSPIKE_RW_READ_TIME_OUT;

    @Value("${aerospike.rwPolicy.writePolicyDefault.totalTimeout}")
    public int AEROSPIKE_RW_WRITE_TIME_OUT;

    @Value("${aerospike.rwPolicy.minConnsPerNode}")
    public int AEROSPIKE_RW_MIN_CON_PER_NODE;

    @Value("${aerospike.rwPolicy.maxSocketIdle}")
    public int AEROSPIKE_RW_MAX_SOC_IDLE;

    @Value("${aerospike.expiration.ttl}")
    public int AEROSPIKE_TTL;

    @Value("${io.core.pool.size}")
    public int ioCorePoolSize;

    @Value("${io.max.pool.size}")
    public int ioMaxPoolSize;

    @Value("${io.keep.alive.time}")
    public int ioKeepAliveTime;

    public Host[] aerospikeHostList(){
        String[] ipAddress = AEROSPIKE_CLIENT_HOST.split("[,]");
        String[] port = AEROSPIKE_CLIENT_PORT.split("[,]");
        if(ipAddress.length != port.length){
            throw new ExceptionInInitializerError("Number of Aerospike hosts doesn't match with number of ports");
            // throw new Exception("Number of host doen't match with number of ports");
        }
        Host[] host = new Host[port.length];
        for(int i=0;i<port.length;i++){
            host[i] = new Host(String.valueOf(ipAddress[i]), Integer.parseInt(port[i]));
        }
        return host;
    }
}
