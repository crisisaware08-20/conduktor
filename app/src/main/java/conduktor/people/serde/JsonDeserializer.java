
package conduktor.people.serde;

import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDeserializer<T> implements Deserializer<T> {
	public static final String VALUE_TARGET_CONFIG = "value.deserializer.type";
    private final ObjectMapper objectMapper = new ObjectMapper();
	private Class<T> targetType;

	public JsonDeserializer() {
		super();
	}

	public JsonDeserializer(Class<T> targetType) {
		this.targetType = targetType;
	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		String className = (String) configs.get(VALUE_TARGET_CONFIG);
    if (className != null) {
        try {
            this.targetType = (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load target type class", e);
        }
    }
	}

	@Override
	public T deserialize(String topic, byte[] data) {
		try {
			return objectMapper.readValue(data, targetType);
		} catch (Exception e) {
			throw new RuntimeException("Error deserializing JSON", e);
		}
	}

	@Override
	public void close() {
	}
}
