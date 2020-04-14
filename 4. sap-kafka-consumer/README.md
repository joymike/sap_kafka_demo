# sap_kafka_demo
SAP to KAFKA Demo


This code is used for consuming messages from KAFKA and call SAP.



Flow:
- Message produced on KAFKA Topic
- Java Client consumes message
- Java Client calls SAP RFC
- SAP RFC replies with the same text as echo


I've combined the code from SAP example and KAFKA consumer example found online.
https://www.tutorialkart.com/apache-kafka/kafka-consumer-with-example-java-application/  
The code is old with depricated methods, but it works.


```script
javac -cp ~/sapjco30/sapjco3.jar:/opt/kafka/libs/* -Xlint:deprecation *.java
java -cp ~/sapjco30/sapjco3.jar:/opt/kafka/libs/*:. SapKafkaConsumeDemo
```
