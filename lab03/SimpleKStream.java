import java.util.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.common.serialization.*;

public class SimpleKStream {
    public static void main(String args[]) {
        Properties conf = new Properties();
        conf.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream - app");
        conf.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "<machine 1/2/3 IP >:9092 ");
        conf.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
        conf.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        conf.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, EnvSerde.class.getName());
        
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Environment> env_stream = builder.<String, Environment>stream("<topic name 1>")
                .selectKey((k, v) -> v.time.toString())
                .mapValues(v -> String.format(" Station : %s value =%s", v.station, v.value()));
        env_stream.to("<topic name 2 >", Produced.with(Serdes.String(), new EnvSerde()));

        KafkaStreams streams = new KafkaStreams(builder.build(), conf);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println(" Shutting down ... ");
                stream.close();
            }
        });
        streams.start();
    }
}