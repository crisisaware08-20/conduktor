package conduktor.people.client.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import conduktor.people.model.Person;

@ExtendWith(MockitoExtension.class)
public class ConduktorKafkaReaderShould {

	ConduktorKafkaReader conductorKafkaReader;

	@Mock
	KafkaConsumer<String, Person> kafkaConsumer;

	@BeforeEach
	void setUp() {
		conductorKafkaReader = new ConduktorKafkaReader(kafkaConsumer);
	}

	@Test
	void should_trim_excess_records_when_poll_provides_more_than_required() {
		int offset = 5;
		int requestedNumber = 5;

		given_kafkaconsumer_returns_records_count(100);

		List<Person> reads = conductorKafkaReader.read("topic", offset, requestedNumber);

		Assertions.assertEquals(requestedNumber, reads.size());
	}

	@Test
	void should_return_already_retrieved_records_when_poll_does_not_fetch_required_number() {
		int offset = 5;
		int requestedNumber = 100;

		given_kafkaconsumer_returns_records_count(10);

		List<Person> reads = conductorKafkaReader.read("topic", offset, requestedNumber);

		Assertions.assertEquals(10, reads.size());
	}


	private void given_kafkaconsumer_returns_records_count(int number) {
		List<ConsumerRecord<String, Person>> records = new ArrayList<>();

		for (int i = 0; i < number; i++) {
			records.add(new ConsumerRecord<>("topic", 0, i, "key" + i, new Person()));
		}

		ConsumerRecords<String, Person> consumerRecords = new ConsumerRecords<>(
				Map.of(new TopicPartition("topic", 0), records));

		when(kafkaConsumer.poll(any(Duration.class)))
			.thenReturn(consumerRecords)
			.thenReturn(ConsumerRecords.empty());
	}

}
