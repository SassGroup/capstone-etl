package com.bluepi.loan.config;

import java.io.IOException;

import com.bluepi.loan.base.IntegratorConstants;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.Getter;

public class MongoDB {

    private static volatile MongoDB instance;


	public static MongoDB getInstance() throws IOException {

		if (instance == null) {
			synchronized (MongoDB.class) {
				if (instance == null) {
					instance = new MongoDB();
				}
			}
		}
		return instance;
	}

    @Getter private MongoClient client; 
    
    private MongoDB(){
        client = MongoClients.create(IntegratorConstants.mongodb_url);
    }

}
