package conduktor.people.config;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import conduktor.people.model.Person;
import conduktor.people.serde.JsonDeserializer;
import conduktor.people.serde.JsonSerializer;

public class KafkaConfig {

	public final static String DEFAUTL_BOOTSTRAP_SERVERS;
	public final static String TOPIC;

	private final static Properties consumerProperties = new Properties();
	private final static Properties producerProperties = new Properties();

	static {
		DEFAUTL_BOOTSTRAP_SERVERS = System.getenv("KAFKA_BOOTSTRAP_SERVERS") != null
				? System.getenv("KAFKA_BOOTSTRAP_SERVERS")
				: "127.0.0.1:9092";

		TOPIC = System.getenv("TOPIC") != null ? System.getenv("TOPIC") : "default-topic";
	}

	public static Properties consumerProperties() {

		consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAUTL_BOOTSTRAP_SERVERS);
		consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
		// In case the provided offset is out of range for any of the partition
		consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		// As per requirement
		consumerProperties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		consumerProperties.setProperty(JsonDeserializer.VALUE_TARGET_CONFIG, Person.class.getName());
		return consumerProperties;
	}

	public static Properties producerProperties() {
		producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAUTL_BOOTSTRAP_SERVERS);
		producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		return producerProperties;
	}

}
