package conduktor.people.utility;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import conduktor.people.model.Person;
import conduktor.people.model.Root;

public class JsonPeopleReader {

	Logger log = LoggerFactory.getLogger(JsonPeopleReader.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	public List<Person> read(String... randomPeopleFileLocation) {

		if (randomPeopleFileLocation.length == 0) {
			try {
				URI uriResource = JsonPeopleReader.class.getClassLoader().getResource("random-people-data.json").toURI();
				return loadPeople(uriResource);
			} catch (URISyntaxException e) {
				throw new RuntimeException("Error loading resource", e);
			}
		}

		return loadPeople(new File(randomPeopleFileLocation[0]).toURI());
	}

	private List<Person> loadPeople(URI resource) {
		try {
			Root root = objectMapper.readValue(resource.toURL(), Root.class);
			return root.persons();
		} catch (IOException e) {
			throw new RuntimeException("Error parsing file: " + resource.getPath(), e);
		}
	}

}
