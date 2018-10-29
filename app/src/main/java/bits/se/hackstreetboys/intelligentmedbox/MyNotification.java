package bits.se.hackstreetboys.intelligentmedbox;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

class MyNotification {
    private String pills;
    private String time;
    private String medicine;

    public MyNotification() {
    }

    public MyNotification(Map<String, Object> map) {
        this.pills = (String) map.get("Pills");
        this.medicine = (String) map.get("Medicine");
        this.time = (String) map.get("Time");
    }

    public MyNotification(String pills, String time, String medicine) {
        this.pills = pills;
        this.medicine = medicine;
        this.time = time;
    }

    public void setPills(String pills) {
        this.pills = pills;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getPills() {
        return pills;
    }

    public String getTime() {
        return time;
    }

    public String getMedicine() {
        return medicine;
    }

    @NonNull
    @Override
    public String toString() {
        return getMedicine()+" Pills "+
                getPills()+"\nTime "+getTime();
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", hashCode());
        map.put("Pills", pills);
        map.put("Medicine", medicine);
        map.put("Time", time);
        return map;
    }
}
