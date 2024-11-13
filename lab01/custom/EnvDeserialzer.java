package custom;

import java.util.ArrayList;
import java.util.Map;
import java.nio.ByteBuffer;
import java.security.spec.EncodedKeySpec;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import datatype.*;

public class EnvDeserialzer implements Deserializer<Environment> {
    final String ENCODING = "UTF8";
    final SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    // @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Do nothing, not necessary right now
    }

    // @Override
    public Environment deserialize(String topic, byte[] data) {
        // TODO: implement the deserialize
        try {
            if (data == null) {
                return null;
            }
            // ByteBuffer buf = ByteBuffer.wrap(data);
            // byte[] station = new byte[buf.getInt()];
            // buf.get(station);
            // byte[] time = new byte[buf.getInt()];
            // buf.get(time);
            // int value = buf.getInt();
            // return new Environment(
            // DF.parse(new String(time, ENCODING)), new String(station, ENCODING), value);
            ByteBuffer buf = ByteBuffer.wrap(data);
            byte[] type = new byte[buf.getInt()];
            buf.get(type);
            byte[] time = new byte[buf.getInt()];
            buf.get(time);
            byte[] station = new byte[buf.getInt()];
            buf.get(station);
            int size = buf.getInt();
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                byte[] mem = new byte[buf.getInt()];
                buf.get(mem);
                list.add(new String(mem, ENCODING));
            }
            if (topic.equals("air"))
                return new Air(DF.parse(new String(time, ENCODING)), new String(station, ENCODING), list);
            else if (topic.equals("earth"))
                return new Earth(DF.parse(new String(time, ENCODING)), new String(station, ENCODING), list);
            else if (topic.equals("water"))
                return new Water(DF.parse(new String(time, ENCODING)), new String(station, ENCODING), list);
            else throw new Exception("Error data.type.");
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing environment data.");
        }
    }

    // @Override
    public void close() {
        // Do nothing, not necessary right now
    }
}
