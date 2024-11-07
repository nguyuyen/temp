package datatype;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Air implements Environment {
    // TODO: declare all attributes
    public String type = "Air";
    public Date time;
    public String station;
    public float temperature;
    public float moisture;
    public float light;
    public float wind;
    public float direction;
    public float pm2_5;
    public float pm10;
    public float co;
    public float nox;
    public float so2;

    public Air() {
    }

    public Air(Date time_, String station_, ArrayList<Float> list_) {
        time = time_;
        station = station_;
        temperature = list_.get(0);
        moisture = list_.get(1);
        light = list_.get(2);
        wind = list_.get(3);
        direction = list_.get(4);
        pm2_5 = list_.get(5);
        pm10 = list_.get(6);
        co = list_.get(7);
        nox = list_.get(8);
        so2 = list_.get(9);
    }

    public String toStr() {
        // TODO: Convert to string
        return type + ":<" + time + "," + station + "," + temperature + "," + moisture + "," + light + "," + wind + ","
                + direction + "," + pm2_5 + "," + pm10 + "," + co + "," + nox + "," + so2 + ">";
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
        list.add(temperature);
        list.add(moisture);
        list.add(light);
        list.add(wind);
        list.add(direction);
        list.add(pm2_5);
        list.add(pm10);
        list.add(co);
        list.add(nox);
        list.add(so2);
        return list;
    }
}
