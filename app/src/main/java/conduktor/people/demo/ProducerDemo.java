package conduktor.people.demo;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemo {
	@Override
	public String toString() {
		return "ProducerDemo []";
	}

	private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class);

	public static void main(String[] args) {
		log.info("I am a Kafka Producer");

		String bootstrapServers = "127.0.0.1:9092";

		// create Producer properties
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// create the producer
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

		// create a producer record
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>("first_topic", "hello world");

		// send data - asynchronous
		Future<RecordMetadata> sendFuture = producer.send(producerRecord);

		try {
			sendFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// flush data - synchronous
		producer.flush();

		// flush and close producer
		producer.close();

	}
}
