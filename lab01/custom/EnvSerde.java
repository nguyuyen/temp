import java.util.*;
import org.apache.kafka.common.serialization.*;
import custom.*;

public class EnvSerde implements Serde<Environment> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Do nothing , not necessary right now
    }

    @Override
    public Serializer<Environment> serializer() {
        return new EnvSerializer();
    }

    @Override
    public Deserializer<Environment> deserializer() {
        return new EnvDeserialzer();
    }

    @Override
    public void close() {
        // Do nothing , not necessary right now
    }
}