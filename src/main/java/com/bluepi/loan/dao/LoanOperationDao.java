package com.bluepi.loan.dao;


import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.RecordSet;
import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.config.MongoDB;
import com.bluepi.loan.model.MessagePayload;
import com.bluepi.loan.service.InsertOnFly;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import lombok.extern.slf4j.Slf4j;

import org.bson.Document;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Write Only
 * --Loan Application Data
 * loanApplicationID  applicant(json) coapplicant(json) createdTime  updatedTime  stage(json)
 *
 * @author dell
 */

@Slf4j
public class LoanOperationDao {

    private static volatile LoanOperationDao instance;

    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * private constructor
     */
    private LoanOperationDao() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    /**
     * @return singleton object of this class
     */
    public static LoanOperationDao getInstance() {
        if (instance == null) {
            synchronized (LoanOperationDao.class) {
                if (instance == null)
                    instance = new LoanOperationDao();
            }
        }
        return instance;
    }

    /**
     * @return getInstance and  Make singleton from serialize and deserialize operation.
     */
    protected LoanOperationDao readResolve() {
        return getInstance();
    }


    /**
     * @param applicationId
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    public void pushToReadDB(String applicationId, MessagePayload msgPayload) throws IOException {
        MongoCollection<Document> applcations_collection = MongoDB.getInstance().getClient().getDatabase(IntegratorConstants.mongodb_application_data_database).getCollection(IntegratorConstants.mongodb_applications_collection);
        applcations_collection.insertOne(new Document(IntegratorConstants.mongodb_applications_application_id, msgPayload.getApplicationId())
            .append(IntegratorConstants.mongodb_applications_data, msgPayload.getPayload())
            .append(IntegratorConstants.mongodb_applications_applicant_id, (String)((Map)msgPayload.getPayload().get(IntegratorConstants.REQUEST_APPLICANT)).get(IntegratorConstants.REQUEST_APPLICANTID))
            .append(IntegratorConstants.mongodb_applications_prod_config_key, (String)((Map)msgPayload.getPayload().get(IntegratorConstants.AEROSPIKE_LAD_BIN_LOAN_APP_SUMMARY)).get(IntegratorConstants.REQUEST_PROD_CONF_KEY)));
    }
}






