package custom;

import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.kafka.common.Cluster;

import datatype.*;

import org.apache.kafka.clients.producer.Partitioner;

public class EnvPartitioner implements Partitioner {

    // @Override
    public void configure(Map<String, ?> configs) {
        // Do nothing, not necessary right now
    }

    // @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // TODO: implement the partitioner
        if (topic.equals("air")) {
            Air air = (Air) value;
            if (air.station.equals("SVDT1"))
                return 0;
            else if (air.station.equals("SVDT3"))
                return 1;
        } else if (topic.equals("earth")) {
            Earth earth = (Earth) value;
            if (earth.station.equals("SVDT3"))
                return 0;
        } else if (topic.equals("water")) {
            Water water = (Water) value;
            if (water.station.equals("SVDT2"))
                return 0;
        }
        return -1;
    }

    // @Override
    public void close() {
        // Do nothing, not necessary right now
    }
}
