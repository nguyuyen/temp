import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.Properties;
import java.io.File;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import custom.EnvPartitioner;
import custom.EnvSerializer;
import datatype.Air;
import datatype.Environment;
import datatype.Earth;
import datatype.Water;

public class Producer {
    final static String URL = "192.168.26.220:9092"; // TODO: replace by your Kafka address
    final static SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) throws Exception {
        final var conf = new Properties();
        conf.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "java-producer");
        conf.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, URL);
        conf.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        conf.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EnvSerializer.class.getName());
        conf.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, EnvPartitioner.class.getName());

        KafkaProducer<String, Environment> producer = new KafkaProducer<>(conf);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Shutting down producer...");
                producer.flush();
                producer.close();
            }
        });

        System.out.println("Begin producing...");
        // TODO: read all files from dataset send them CONCURRENTLY
        //
        // While air, earth or water is not empty {
        //
        // Air air_data = null; // init an air record
        // ProducerRecord<String, Environment> air_record = new
        // ProducerRecord<>("air-topic", air_data);
        // producer.send(air_record);
        //
        // Earth earth_data = null; // init an earth record
        // ProducerRecord<String, Environment> earth_record = new
        // ProducerRecord<>("earth-topic", earth_data);
        // producer.send(earth_record);
        //
        // Water water_data = null; // init an water record
        // ProducerRecord<String, Environment> water_record = new
        // ProducerRecord<>("water-topic", water_data);
        // producer.send(water_record);
        //
        // producer.flush(); // force producer to send immediately
        // }
        Scanner sc_air = new Scanner(new File("dataset/AIR2308.csv"));
        Scanner sc_earth = new Scanner(new File("dataset/EARTH2308.csv"));
        Scanner sc_water = new Scanner(new File("dataset/WATER2308.csv"));
        boolean air_next_line = sc_air.hasNextLine();
        boolean earth_next_line = sc_earth.hasNextLine();
        boolean water_next_line = sc_water.hasNextLine();
        while (air_next_line || earth_next_line || water_next_line) {
            if (air_next_line) {
                String air_line = sc_air.nextLine();
                air_next_line = sc_air.hasNextLine();
                String[] air_values = air_line.split(",");
                ArrayList<Float> list = new ArrayList<Float>();
                for (int i = 2; i < air_values.length; i++) {
                    list.add(Float.parseFloat(air_values[i]));
                }
                Air air = new Air(DF.parse(air_values[0]), air_values[1], list);
                ProducerRecord<String, Environment> air_record = new ProducerRecord<>("air", air);
                producer.send(air_record);
            }
            if (earth_next_line) {
                String earth_line = sc_earth.nextLine();
                earth_next_line = sc_earth.hasNextLine();
                String[] earth_values = earth_line.split(",");
                ArrayList<Float> list = new ArrayList<Float>();
                for (int i = 2; i < earth_values.length; i++) {
                    list.add(Float.parseFloat(earth_values[i]));
                }
                Earth earth = new Earth(DF.parse(earth_values[0]), earth_values[1], list);
                ProducerRecord<String, Environment> earth_record = new ProducerRecord<>("earth", earth);
                producer.send(earth_record);
            }
            if (water_next_line) {
                String water_line = sc_water.nextLine();
                water_next_line = sc_water.hasNextLine();
                String[] water_values = water_line.split(",");
                ArrayList<Float> list = new ArrayList<Float>();
                for (int i = 2; i < water_values.length; i++) {
                    list.add(Float.parseFloat(water_values[i]));
                }
                Water water = new Water(DF.parse(water_values[0]), water_values[1], list);
                ProducerRecord<String, Environment> water_record = new ProducerRecord<>("water", water);
                producer.send(water_record);
            }
            producer.flush();
        }
    }
}
