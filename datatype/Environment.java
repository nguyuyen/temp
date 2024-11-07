package datatype;

import java.util.ArrayList;
import java.util.Date;

public interface Environment {
    public String toStr();
    public String getType();
    public Date getTime();
    public String getStation();
    public ArrayList<Float> getList();
}
