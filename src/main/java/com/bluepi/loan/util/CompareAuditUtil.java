package com.bluepi.loan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Singleton
public class CompareAuditUtil {


    private static volatile CompareAuditUtil instance;

    private static final Logger log = LoggerFactory.getLogger(CompareAuditUtil.class);

    /**
     * private constructor
     */
    public CompareAuditUtil() {

        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }



    /**
     * @return singleton object of this class
     */
    public synchronized static CompareAuditUtil getInstance() {

        if (instance == null) {
            synchronized (CompareAuditUtil.class) {
                if (instance == null)
                    instance = new CompareAuditUtil();
            }
        }
        return instance;
    }

    /**
     * @return getInstance and  Make singleton from serialize and deserialize operation.
     */
    protected CompareAuditUtil readResolve() {
        return getInstance();
    }


    public Map<String,Object> mapData(Object DestKetSet, Object DestObject, Object SourceObject){
        Map<String,Object> data =new HashMap<>();
        data.put("Field_name",DestKetSet);
        data.put("oldValue",DestObject);
        data.put("newValue", SourceObject);
        return data;
    }

    public void CompareMapForAuditLog(Map<String, Object> SourceMap, Map<String, Object> DestinationMap, List<Map<String, Object>> data){
        for(var DestKetSet : DestinationMap.keySet()) {
            if(SourceMap.containsKey(DestKetSet))
            {
                if(DestinationMap.get(DestKetSet) instanceof Map<?, ?>){
                    CompareMapForAuditLog((Map)SourceMap.get(DestKetSet),(Map)DestinationMap.get(DestKetSet),data);
                }
                else if (DestinationMap.get(DestKetSet) instanceof List<?>){

                    for(var i=0;i<((List<?>) DestinationMap.get(DestKetSet)).size();i++)
                    {
                        CompareMapForAuditLog((Map)((List<?>) SourceMap.get(DestKetSet)).get(i),(Map)((List<?>) DestinationMap.get(DestKetSet)).get(i),data);
                    }
                }
                else{
                    if(SourceMap.get(DestKetSet)!=null && SourceMap.get(DestKetSet).equals(DestinationMap.get(DestKetSet))) {

                    }
                    else{
                        data.add(mapData(DestKetSet,SourceMap.get(DestKetSet),DestinationMap.get(DestKetSet)));
                    }
                }
            }
            else
            {
                data.add(mapData(DestKetSet,SourceMap.get(DestKetSet),DestinationMap.get(DestKetSet)));
            }
        }
        log.info("list is "+ data);
    }


}
