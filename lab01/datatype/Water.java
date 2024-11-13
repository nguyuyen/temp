package datatype;

import java.util.ArrayList;
import java.util.Date;

public class Water implements Environment {
    // TODO: declare all attributes
    public Date time;
    public String station;
    public String ph;
    public String d_o;
    public String temperature;
    public String salinity;

    public Water() {
    }

    public Water(Date time_, String station_, ArrayList<String> list_) {
        time = time_;
        station = station_;
        ph = list_.get(0);
        d_o = list_.get(1);
        temperature = list_.get(2);
        salinity = list_.get(3);
    }

    public String toStr() {
        // TODO: Convert to string
        return "Water:<" + time + "," + station + "," + ph + "," + d_o + "," + temperature + "," + salinity + ">";
    }

    public String getType() {
        return "water";
    }

    public Date getTime() {
        return time;
    }

    public String getStation() {
        return station;
    }

    public ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(ph);
        list.add(d_o);
        list.add(temperature);
        list.add(salinity);
        return list;
    }
}
