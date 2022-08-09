mvn clean install 
java -Xms2G -Xmx2G -XX:+UseG1GC -XX:+UseStringDeduplication -Dmicronaut.environments=local -jar target/idfc-loan-etl-service-0.1.jar "-Dmicronaut.server.port=8095"



















