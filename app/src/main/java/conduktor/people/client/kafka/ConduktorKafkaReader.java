package conduktor.people.client.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import conduktor.people.model.Person;

public class ConduktorKafkaReader {

	private static Logger LOGGER = LoggerFactory.getLogger(ConduktorKafkaReader.class);
	private static int MAX_EMPTY_POLLS = 2;

	private KafkaConsumer<String, Person> kafkaConsumer;

	public ConduktorKafkaReader(KafkaConsumer<String, Person> kafkaConsumer) {
		this.kafkaConsumer = kafkaConsumer;
	}

	public List<Person> read(String topic, int offset, int number) {

		kafkaConsumer
				.assign(List.of(new TopicPartition(topic, 0), new TopicPartition(topic, 1), new TopicPartition(topic, 2)));
		kafkaConsumer.seek(new TopicPartition(topic, 0), offset);
		kafkaConsumer.seek(new TopicPartition(topic, 1), offset);
		kafkaConsumer.seek(new TopicPartition(topic, 2), offset);

		List<Person> messages = new ArrayList<>();
		int emptyPolls = 0;

		while (messages.size() < number) {
			ConsumerRecords<String, Person> records = kafkaConsumer.poll(Duration.ofMillis(50));

			if (records.isEmpty()) {
				emptyPolls++;
				if (emptyPolls >= MAX_EMPTY_POLLS) {
					LOGGER.info("MAX_EMPTY_POLLS has been exceeded {}", MAX_EMPTY_POLLS);
					break;
				}
			} else {
				// reset empty polls
				emptyPolls = 0;
			}

			for (ConsumerRecord<String, Person> record : records) {
				messages.add(record.value());
				if (messages.size() >= number) {
					break;
				}
			}

		}


		kafkaConsumer.close();

		return messages;
	}

}
