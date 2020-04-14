/**
*  Kafka Consumer with Example Java Application
*/
public class SapKafkaConsumerDemo {
    public static void main(String[] args) {
        SapKafkaConsumer consumerThread = new SapKafkaConsumer("my-kafka-topic");
        consumerThread.start();
    }
}
