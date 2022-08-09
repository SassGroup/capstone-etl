package com.bluepi.loan.controller;
import com.bluepi.loan.dao.LoanOperationDao;
import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.dao.LoanOperationDao;
import com.bluepi.loan.processor.MongoWriteLoanProcessor;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;
import org.slf4j.MDC;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
@Controller("/Etlloan")
public class EtlController {
    private Logger log = LoggerFactory.getLogger(EtlController.class);
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Put("/applicationupdate/{id}")
    public String updateLoan(@Body @NotNull Map<String, Object> payload, @PathVariable @NotNull String id) throws Exception {
//        LoanOperationDao.getInstance().pushToOracle(id,payload);
//        Map<String,Object> map= LoanOperationDao.getInstance().readDataFromMysql(id);
//        System.out.println(map);
//        LoanOperationDao.getInstance().auditLog(id,payload);
//        LoanOperationDao.getInstance().pushToOracle(id,payload);
//        LoanOperationDao.getInstance().pushToReadDB(id,payload);
//        LoanOperationDao.getInstance().updateToDuplicateCheck(id,payload);
//        LoanOperationDao.getInstance().pushToOracle(id,payload);
//        Map<String,Object> map= LoanOperationDao.getInstance().readDataFromMysql(id);
//        System.out.println(map);
//        LoanOperationDao.getInstance().auditLog(id,payload);
//        LoanOperationDao.getInstance().pushToOracle(id,payload);
//        LoanOperationDao.getInstance().pushToReadDB(id,payload);
//        LoanOperationDao.getInstance().updateToDuplicateCheck(id,payload);
        return ("Insertion done");
    }
    /**
     * @return uuid as string
     */
    private String generateUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}