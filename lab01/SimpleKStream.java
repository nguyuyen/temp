import java.util.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;

import custom.*;

import java.time.*;

import datatype.*;

import org.apache.kafka.common.serialization.*;

public class SimpleKStream {

    public static void main(String args[]) {
        Properties conf = new Properties();
        conf.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream - app");
        conf.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.26.220:9092");
        conf.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
        conf.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        conf.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, EnvSerde.class.getName());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> airStream = builder.<String, Environment>stream("air")
                .selectKey((k, v) -> v.getTime().toString()).mapValues(v -> v.toStr());
        KStream<String, String> earthStream = builder.<String, Environment>stream("earth")
                .selectKey((k, v) -> v.getTime().toString()).mapValues(v -> v.toStr());
        KStream<String, String> waterStream = builder.<String, Environment>stream("water")
                .selectKey((k, v) -> v.getTime().toString()).mapValues(v -> v.toStr());

        KStream<String, String> join_stream = airStream.join(earthStream, (left, right) -> {
            return String.format("station: %s value=%s", left.s, left.value);
        }, JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMillis(1000)),
                StreamJoined.with(Serdes.String(), new EnvSerde(), Serdes.String()))
                .join(waterStream, (left, right) -> {
                    return String.format("station: %s value=%s", left.s, left.value);
                }, JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMillis(1000)),
                        StreamJoined.with(Serdes.String(), new EnvSerde(), Serdes.String()));

        join_stream.foreach((k, v) -> System.out.println(k + ": " + v));

        airStream.foreach((key, value) -> {
            System.out.println("Air Stream - Key: " + key + ", Value: " + value);
        });
        // earthStream.foreach((key, value) -> {
        // System.out.println("Earth Stream - Key: " + key + ", Value: " + value);
        // });
        // waterStream.foreach((key, value) -> {
        // System.out.println("Water Stream - Key: " + key + ", Value: " + value);
        // });

        KafkaStreams streams = new KafkaStreams(builder.build(), conf);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Shutting down ... ");
                streams.close();
            }
        });
        streams.start();
    }

    // public static int air_count = 0;
    // public static int earth_count = 0;
    // public static int water_count = 0;
    // public static ArrayList<Double> air_avg = new ArrayList<>();
    // public static ArrayList<Double> earth_avg = new ArrayList<>();
    // public static ArrayList<Double> water_avg = new ArrayList<>();
    // public static ArrayList<Double> air_std = new ArrayList<>();
    // public static ArrayList<Double> earth_std = new ArrayList<>();
    // public static ArrayList<Double> water_std = new ArrayList<>();

    // public double random_in_range(double std) {
    // return (Math.random() * 2.0 - 1.0) * std;
    // }

    // public ArrayList<String> process_data_air(ArrayList<String> list) {
    // if (air_count == 0) {
    // for (String string : list) {
    // if (string.equals("---")) {
    // air_avg.add(0.0);
    // string = "0";
    // } else
    // air_avg.add(Double.parseDouble(string));
    // air_std.add(0.0);
    // }
    // } else {
    // for (int i = 0; i < air_avg.size(); i++) {
    // double new_data;
    // if (list.get(i).equals("---")) {
    // new_data = air_avg.get(i) + random_in_range(air_std.get(i));
    // list.set(i, String.valueOf(new_data));
    // } else {
    // new_data = Double.parseDouble(list.get(i));
    // }
    // double air_avg_new = (air_count * air_avg.get(i) + new_data) / (air_count +
    // 1);
    // air_std.set(i, Math.sqrt((air_count * air_std.get(i) * air_std.get(i)
    // + (new_data - air_avg_new) * (new_data - air_avg.get(i))) / (air_count +
    // 1)));
    // air_avg.set(i, air_avg_new);
    // }
    // }
    // ++air_count;
    // return list;
    // }

    // public ArrayList<String> process_data_earth(ArrayList<String> list) {
    // if (earth_count == 0) {
    // for (String string : list) {
    // if (string.equals("---")) {
    // earth_avg.add(0.0);
    // string = "0";
    // } else
    // earth_avg.add(Double.parseDouble(string));
    // earth_std.add(0.0);
    // }
    // } else {
    // for (int i = 0; i < earth_avg.size(); i++) {
    // double new_data;
    // if (list.get(i).equals("---")) {
    // new_data = earth_avg.get(i) + random_in_range(earth_std.get(i));
    // list.set(i, String.valueOf(new_data));
    // } else {
    // new_data = Double.parseDouble(list.get(i));
    // }
    // double earth_avg_new = (earth_count * earth_avg.get(i) + new_data) /
    // (earth_count + 1);
    // earth_std.set(i, Math.sqrt((earth_count * earth_std.get(i) * earth_std.get(i)
    // + (new_data - earth_avg_new) * (new_data - earth_avg.get(i))) / (earth_count
    // + 1)));
    // earth_avg.set(i, earth_avg_new);
    // }
    // }
    // ++earth_count;
    // return list;
    // }

    // public ArrayList<String> process_data_water(ArrayList<String> list) {
    // if (water_count == 0) {
    // for (String string : list) {
    // if (string.equals("---")) {
    // water_avg.add(0.0);
    // string = "0";
    // } else
    // water_avg.add(Double.parseDouble(string));
    // water_std.add(0.0);
    // }
    // } else {
    // for (int i = 0; i < water_avg.size(); i++) {
    // double new_data;
    // if (list.get(i).equals("---")) {
    // new_data = water_avg.get(i) + random_in_range(water_std.get(i));
    // list.set(i, String.valueOf(new_data));
    // } else {
    // new_data = Double.parseDouble(list.get(i));
    // }
    // double water_avg_new = (water_count * water_avg.get(i) + new_data) /
    // (water_count + 1);
    // water_std.set(i, Math.sqrt((water_count * water_std.get(i) * water_std.get(i)
    // + (new_data - water_avg_new) * (new_data - water_avg.get(i))) / (water_count
    // + 1)));
    // water_avg.set(i, water_avg_new);
    // }
    // }
    // ++water_count;
    // return list;
    // }

    // public static void main(String args[]) {
    // Properties conf = new Properties();
    // conf.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream - app");
    // conf.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.26.220:9092");
    // conf.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
    // conf.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
    // Serdes.String().getClass().getName());
    // conf.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
    // EnvSerde.class.getName());

    // ///
    // StreamsBuilder builder = new StreamsBuilder();
    // KStream<String, Environment> stream_air = builder.<String,
    // Environment>stream("air")
    // .selectKey((k, v) -> v.time.toString())
    // .mapValues(v -> new Air(v.getTime, v.getStation,
    // process_data_air(v.getList())));
    // KStream<String, Environment> stream_earth = builder.<String,
    // Environment>stream("earth")
    // .selectKey((k, v) -> v.time.toString())
    // .mapValues(v -> new Earth(v.getTime, v.getStation,
    // process_data_earth(v.getList())));
    // KStream<String, Environment> stream_water = builder.<String,
    // Environment>stream("water")
    // .selectKey((k, v) -> v.time.toString())
    // .mapValues(v -> new Water(v.getTime, v.getStation,
    // process_data_water(v.getList())));
    // KStream<String, String> join_stream = stream_air.join(stream_earth, (left,
    // right) -> {
    // return String.format("key: %s, value=%s", left.toStr(), right.toStr());
    // }, JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMillis(1000)),
    // // Each record waits record from other stream at most 1s
    // StreamJoined.with(Serdes.String(), new EnvSerde(), Serdes.Integer()))
    // .join(stream_water, (left, right) -> {
    // return String.format("key: %s, value=%s", left.toStr(), right.toStr());
    // }, JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMillis(1000)),
    // // Each record waits record from other stream at most 1s
    // StreamJoined.with(Serdes.String(), new EnvSerde(), Serdes.Integer()));
    // // StreamJoined.with(<Key datatype>, <stream 1 Value datatype>, <stream 2
    // Value
    // // datatype>)
    // join_stream.foreach((k, v) -> System.out.println(k + ": " + v));
    // KafkaStreams streams = new KafkaStreams(builder.build(), conf);
    // Runtime.getRuntime().addShutdownHook(new Thread() {
    // public void run() {
    // System.out.println("Shutting down...");
    // streams.close();
    // }
    // });
    // streams.start();

    // StreamsBuilder builder = new StreamsBuilder();
    // KStream<String, Environment> env_stream = builder.<String,
    // Environment>stream("<topic name 1>")
    // .selectKey((k, v) -> v.time.toString())
    // .mapValues(v -> String.format(" Station : %s value =%s", v.station,
    // v.value()));
    // env_stream.to("<topic name 2 >", Produced.with(Serdes.String(), new
    // EnvSerde()));

    // KafkaStreams streams = new KafkaStreams(builder.build(), conf);
    // Runtime.getRuntime().addShutdownHook(new Thread() {
    // public void run() {
    // System.out.println(" Shutting down ... ");
    // stream.close();
    // }
    // });
    // streams.start();

    ///
    ///
    ///

    // // Config is the same
    // StreamsBuilder builder = new StreamsBuilder();
    // KStream<String, Environment> stream_1 = builder.<String,
    // Environment>stream("<topic name 1>")
    // .selectKey((k, v) -> v.time.toString());
    // KStream<String, Integer> stream_2 = builder.<String,
    // Environment>stream("<topic name 2>")
    // .selectKey((k, v) -> v.time.toString()).mapValues(v -> v.value);

    // KStream<String, String> join_stream = stream_1.join(stream_2, (left, right)
    // -> {
    // return String.format("station: %s value=%s", left.station, left.value +
    // right);
    // }, JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMillis(1000)),
    // // Each record waits record from other stream at most 1s
    // StreamJoined.with(Serdes.String(), new EnvSerde(), Serdes.Integer()));
    // // StreamJoined.with(<Key datatype>, <stream 1 Value datatype>, <stream 2
    // Value
    // // datatype>)
    // join_stream.foreach((k, v) -> System.out.println(k + ": " + v));
    // KafkaStreams streams = new KafkaStreams(builder.build(), conf);
    // Runtime.getRuntime().addShutdownHook(new Thread() {
    // public void run() {
    // System.out.println("Shutting down...");
    // stream.close();
    // }
    // });
    // streams.start();
    // }
}
