package datatype;

import java.util.ArrayList;
import java.util.Date;

public class Air implements Environment {
    // TODO: declare all attributes
    public Date time;
    public String station;
    public String temperature;
    public String moisture;
    public String light;
    public String wind_direction;
    public String pm2_5;
    public String pm10;
    public String co;
    public String nox;
    public String so2;

    public Air() {
    }

    public Air(Date time_, String station_, ArrayList<String> list_) {
        time = time_;
        station = station_;
        temperature = list_.get(0);
        moisture = list_.get(1);
        light = list_.get(2);
        wind_direction = list_.get(3);
        pm2_5 = list_.get(4);
        pm10 = list_.get(5);
        co = list_.get(6);
        nox = list_.get(7);
        so2 = list_.get(8);
    }

    public String toStr() {
        // TODO: Convert to string
        return "Air :<" + time + "," + station + "," + temperature + "," + moisture + "," + light + ","
                + wind_direction + "," + pm2_5 + "," + pm10 + "," + co + "," + nox + "," + so2 + ">";
    }

    public String getType() {
        return "air";
    }

    public Date getTime() {
        return time;
    }

    public String getStation() {
        return station;
    }

    public ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(temperature);
        list.add(moisture);
        list.add(light);
        list.add(wind_direction);
        list.add(pm2_5);
        list.add(pm10);
        list.add(co);
        list.add(nox);
        list.add(so2);
        return list;
    }
}
