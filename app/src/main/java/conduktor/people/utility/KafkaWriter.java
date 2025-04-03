package conduktor.people.utility;

import java.util.List;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import conduktor.people.model.Person;

public class KafkaWriter {

	private KafkaProducer<String, Person> kafkaProducer;

	public KafkaWriter(KafkaProducer<String, Person> kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}

	public void loadPeople(List<Person> persons, String topic) {
		persons.forEach(person -> {
			ProducerRecord<String, Person> record = new ProducerRecord<>(topic, person._id, person);
			kafkaProducer.send(record);
		});
		kafkaProducer.flush();
		kafkaProducer.close();
		System.out.println("Loaded all persons to topic: " + topic);
	}
}
