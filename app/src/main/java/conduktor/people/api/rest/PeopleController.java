package conduktor.people.api.rest;

import java.util.List;
import java.util.Optional;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import conduktor.people.client.kafka.ConduktorKafkaReader;
import conduktor.people.config.KafkaConfig;
import conduktor.people.model.Person;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;

public class PeopleController {
	private static final int DEFAULT_COUNT = 10;
	private static final int DEFAULT_OFFSET = 0;

	public static void init() {
		var app = Javalin.create()
				.get("/topic/{topic_name}/{offset}", ctx -> {
					String topicName = ctx.pathParam("topic_name");
					try {
						int offset = validateOffset(ctx.pathParam("offset"));
						int count = validateCount(ctx.queryParam("count"));

						// Create a new Kafka consumer for each request - otherwise big problems
						KafkaConsumer<String, Person> kafkaConsumer = new KafkaConsumer<String, Person>(
								KafkaConfig.consumerProperties());
						ConduktorKafkaReader conduktorKafkaReader = new ConduktorKafkaReader(kafkaConsumer);
						List<Person> people = conduktorKafkaReader.read(topicName, offset, count);

						ctx.json(people);
					} catch (BadRequestResponse e) {
						ctx.status(400).json(e.getMessage());
					}
				})
				.get("/topic/{topic_name}/", ctx -> {

					try {
						String topicName = ctx.pathParam("topic_name");
						int offset = DEFAULT_OFFSET;
						int count = validateCount(ctx.queryParam("count"));

						// Create a new Kafka consumer for each request - otherwise big problems
						KafkaConsumer<String, Person> kafkaConsumer = new KafkaConsumer<String, Person>(
								KafkaConfig.consumerProperties());
						ConduktorKafkaReader conduktorKafkaReader = new ConduktorKafkaReader(kafkaConsumer);
						List<Person> people = conduktorKafkaReader.read(topicName, offset, count);
						ctx.json(people);
					} catch (BadRequestResponse e) {
						ctx.status(400).json(e.getMessage());
					}
				})

				.start(8080);
	}

	private static int validateOffset(String offsetStr) {
		try {
			int offset = Integer.parseInt(offsetStr);
			if (offset < 0)
				throw new BadRequestResponse("Offset must be a non-negative integer.");
			return offset;
		} catch (NumberFormatException e) {
			throw new BadRequestResponse("Invalid offset format. It must be an integer.");
		}
	}

	private static int validateCount(String countStr) {
		try {
			int count = Optional.ofNullable(countStr)
					.map(Integer::parseInt)
					.orElse(DEFAULT_COUNT); // Default count if missing
			if (count <= 0)
				throw new BadRequestResponse("Count must be a positive integer.");
			return count;
		} catch (NumberFormatException e) {
			throw new BadRequestResponse("Invalid count format. It must be an integer.");
		}
	}

}
