# sap_kafka_demo
SAP to KAFKA Demo

This code demonstrates how to:
- SAP call RFC
- RFC calls JCo Server
- JCo Server publishes message on KAFKA


I've modified the sample code from SAP, and combined it with an online KAFKA producer sample I took from:
https://www.tutorialspoint.com/apache_kafka/apache_kafka_simple_producer_example.htm


```script
javac -cp ~/sapjco30/sapjco3.jar:/opt/kafka/libs/* StepByStepServer.java
java -cp ~/sapjco30/sapjco3.jar:/opt/kafka/libs/*:. StepByStepServer
```
