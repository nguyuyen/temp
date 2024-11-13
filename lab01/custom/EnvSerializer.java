package custom;

import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import datatype.Environment;

public class EnvSerializer implements Serializer<Environment> {
    final String ENCODING = "UTF8";
    final SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    // @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Do nothing, not necessary right now
    }

    // @Override
    public byte[] serialize(String topic, Environment data) {
        // TODO: implement the serializer
        try {
            if (data == null) {
                return null;
            }
            // Serialize string data to byte
            // byte[] time = DF.format(data.time).getBytes("UTF8");
            // byte[] station = data.station.getBytes("UTF8");
            // int value = data.value;
            // Create byte buffer
            // int len = Integer.BYTES + station.length + Integer.BYTES + time.length +
            // Integer.BYTES;
            // ByteBuffer buf = ByteBuffer.allocate(len);
            // buf.putInt(station.length);
            // buf.put(station);
            // buf.putInt(time.length);
            // buf.put(time);
            // buf.putInt(value);
            // return buf.array();
            byte[] type = data.getType().getBytes("UTF8");
            byte[] time = DF.format(data.getTime()).getBytes("UTF8");
            byte[] station = data.getStation().getBytes("UTF8");
            ArrayList<String> list = data.getList();
            int len = Integer.BYTES + type.length + Integer.BYTES + time.length + Integer.BYTES + station.length
                    + Integer.BYTES;
            ArrayList<byte[]> byte_list = new ArrayList<>();
            for (String mem : list) {
                byte[] d = mem.getBytes("UTF8");
                len += Integer.BYTES + d.length;
                byte_list.add(d);
            }
            ByteBuffer buf = ByteBuffer.allocate(len);
            buf.putInt(type.length);
            buf.put(type);
            buf.putInt(time.length);
            buf.put(time);
            buf.putInt(station.length);
            buf.put(station);
            buf.putInt(list.size());
            for (byte[] bs : byte_list) {
                buf.putInt(bs.length);
                buf.put(bs);
            }
            return buf.array();
        } catch (Exception e) {
            throw new SerializationException("Error when serializing environment data.");
        }
    }

    // @Override
    public void close() {
        // Do nothing, not necessary right now
    }
}
