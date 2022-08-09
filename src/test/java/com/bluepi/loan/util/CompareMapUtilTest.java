package com.bluepi.loan.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;


public class CompareMapUtilTest {

//     @Inject
//     CompareMapUtil compareMapUtil;

//     ObjectMapper mapper =new ObjectMapper();
//     String json = "{\"name\":\"akashdeep\", \"age\":\"37\"}";
//     String json2 = "{\"name\":\"akash\",\"info\": {\"coapplicantname\":\"akash\",\"age\": \"19\"}}";
//     String resultjson = "{\"name\":\"akashdeep\", \"age\":\"37\",\"info\": {\"coapplicantname\":\"akash\",\"age\": \"19\"}}";

//     @Test
//     public void CompareMapUtilTest() throws JsonProcessingException {
//         Map<String, Object> oldPayload = mapper.readValue(json, Map.class);
//         Map<String, Object> newPayload = mapper.readValue(json2, Map.class);
//         Map<String, Object> Result = mapper.readValue(resultjson, Map.class);
//         Map<String, Object> mergedMap = CompareMapUtil.getInstance().compareMaps(oldPayload, newPayload);
//         Assertions.assertEquals(Result,mergedMap);
//     }

//     String json3 = "{\"name\":\"akashdeep\", \"age\":\"37\"}";
//     String json4 = "{\"name\":\"akash\",\"info\": [{\"coapplicantname1\":\"akash\",\"age\": \"19\"},{\"coapplicantname2\":\"abhishek\",\"age\": \"16\"}]}";
//     String result = "{\"name\":\"akashdeep\", \"age\":\"37\",\"info\": [{\"coapplicantname1\":\"akash\",\"age\": \"19\"},{\"coapplicantname2\":\"abhishek\",\"age\": \"16\"}]}";

//     @Test
//     public void CompareMapUtilTest1() throws JsonProcessingException {
//         Map<String, Object> oldPayload = mapper.readValue(json3, Map.class);
//         Map<String, Object> newPayload = mapper.readValue(json4, Map.class);
//         Map<String, Object> Result = mapper.readValue(result, Map.class);
//         Map<String, Object> mergedMap = CompareMapUtil.getInstance().compareMaps(oldPayload, newPayload);
//         Assertions.assertEquals(Result,mergedMap);
//     }

//     String json5 = "{\"name\":\"akashdeep\", \"age\":\"37\"}";
//     String json6 = "{\"name\":\"akash\",\"gender\":\"male\"}";
//     String result1 = "{\"name\":\"akash\", \"age\":\"37\",\"gender\":\"male\"}";

//    @Test
//    public void CompareMapUtilTest2() throws JsonProcessingException {
//        Map<String, Object> oldPayload = mapper.readValue(json6, Map.class);
//        Map<String, Object> newPayload = mapper.readValue(json5, Map.class);
//        Map<String, Object> Result = mapper.readValue(result1, Map.class);
//        Map<String, Object> mergedMap = CompareMapUtil.getInstance().compareMaps(oldPayload, newPayload);
//        System.out.println(mergedMap);
//        Assertions.assertEquals(Result,mergedMap);
//    }


}
