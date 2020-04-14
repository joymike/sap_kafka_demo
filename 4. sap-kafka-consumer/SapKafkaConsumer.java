import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
 
import java.util.Collections;
import java.util.Properties;


import java.util.concurrent.CountDownLatch;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

/**
* Kafka Consumer with Example Java Application
*/
public class SapKafkaConsumer extends ShutdownableThread {
    private final KafkaConsumer<Integer, String> consumer;
    private final String topic;
    
    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final String CLIENT_ID = "SampleConsumer";
 
 
    public static final String ABAP_AS = "ABAP_AS1";
 
 
 
    public SapKafkaConsumer(String topic) {
        super("KafkaConsumerExample", false);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CLIENT_ID);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
 
        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
    }
 
    @Override
    public void doWork() {
        consumer.subscribe(Collections.singletonList(this.topic));
        ConsumerRecords<Integer, String> records = consumer.poll(500);
		try{
		//JCoDestination is the logic address of an ABAP system and ...
        JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS);
        // ... it always has a reference to a metadata repository
        JCoFunction function = destination.getRepository().getFunction("STFC_CONNECTION");
        if(function == null)
            throw new RuntimeException("ZMP_STFC_CONNECTION not found in SAP.");

        for (ConsumerRecord<Integer, String> record : records) {


        //JCoFunction is container for function values. Each function contains separate
        //containers for import, export, changing and table parameters.
        //To set or get the parameters use the APIS setValue() and getXXX(). 
			function.getImportParameterList().setValue("REQUTEXT", record.value());
			
			try
			{
				//execute, i.e. send the function to the ABAP system addressed 
				//by the specified destination, which then returns the function result.
				//All necessary conversions between Java and ABAP data types
				//are done automatically.
				function.execute(destination);
			}
			catch(AbapException e)
			{
				System.out.println(e.toString());
				return;
			}
			
			System.out.println("STFC_CONNECTION finished:");
			System.out.println(" Echo: " + function.getExportParameterList().getString("ECHOTEXT"));
			System.out.println(" Response: " + function.getExportParameterList().getString("RESPTEXT"));
			System.out.println();			
			
            System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
        }
} catch (Exception ex){
System.out.println(ex.toString());
}
    }
 
    @Override
    public String name() {
        return null;
    }
 
    @Override
    public boolean isInterruptible() {
        return false;
    }

public void callSap(String message ) throws JCoException{
//return null;
}
}
