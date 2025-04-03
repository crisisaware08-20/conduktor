package conduktor;

import java.util.List;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import conduktor.people.api.rest.PeopleController;
import conduktor.people.config.KafkaConfig;
import conduktor.people.model.Person;
import conduktor.people.utility.JsonPeopleReader;
import conduktor.people.utility.KafkaWriter;

public class App {

	private static Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		log.info("Loading people from file");
		List<Person> people = new JsonPeopleReader().read();

		KafkaProducer<String, Person> kafkaProducer = new KafkaProducer<>(KafkaConfig.producerProperties());
		log.info("Load " + people.size() + " people to Kafka topic: {}", KafkaConfig.TOPIC);
		new KafkaWriter(kafkaProducer).loadPeople(people, KafkaConfig.TOPIC);

		PeopleController.init();
	}
}
