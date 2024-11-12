package datatype;

import java.util.ArrayList;
import java.util.Date;

public class Earth implements Environment {
    // TODO: declare all attributes
    public String type = "Earth";
    public Date time;
    public String station;
    public float moisture;
    public float temperature;
    public float salinity;
    public float ph;
    public float water_root;
    public float water_leaf;
    public float water_level;
    public float voltage;

    public Earth() {
    }

    public Earth(Date time_, String station_, ArrayList<Float> list_) {
        time = time_;
        station = station_;
        moisture = list_.get(0);
        temperature = list_.get(1);
        salinity = list_.get(2);
        ph = list_.get(3);
        water_root = list_.get(4);
        water_leaf = list_.get(5);
        water_level = list_.get(6);
        voltage = list_.get(7);
    }

    public String toStr() {
        // TODO: Convert to string
        return type + ":<" + time + "," + station + "," + moisture + "," + temperature + "," + salinity + "," + ph + ","
                + water_root + "," + water_leaf + "," + water_level + "," + voltage + ">";
    }

    public String getType() {
        return type;
    }

    public Date getTime() {
        return time;
    }

    public String getStation() {
        return station;
    }

    public ArrayList<Float> getList() {
        ArrayList<Float> list = new ArrayList<Float>();
        list.add(moisture);
        list.add(temperature);
        list.add(salinity);
        list.add(ph);
        list.add(water_root);
        list.add(water_leaf);
        list.add(water_level);
        list.add(voltage);
        return list;
    }
}
