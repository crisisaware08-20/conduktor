package conduktor.people.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


public class JsonPeopleReaderShould {

	JsonPeopleReader jsonPeopleReader = new JsonPeopleReader();

	@Test
	void load_data_in_memory_from_default_resource_file__500_people() {
		Assertions.assertEquals(500, jsonPeopleReader.read().size());
	}

	@Test
	@Disabled
	void load_data_in_memory_from_custom_resource_file__500_people() {
		Assertions.assertEquals(500, jsonPeopleReader.read("/Users/xxxxxxxxxx/Downloads/random-people-data.json").size());
	}

	@Test
	void throw_exception_when_file_does_not_exist() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			jsonPeopleReader.read("/Users/xxxxxxxxxx/Downloads/random-people-data-DOES-NOT-EXIST.json");
		});
	}

}
