package bits.se.hackstreetboys.intelligentmedbox;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

class MyNotification {
    private String type;
    private String message;

    public MyNotification() {
    }

    public MyNotification(Map<String, Object> map) {
        this.type = (String) map.get("type");
        this.message = (String) map.get("msg");
    }

    public MyNotification(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return "Type: "+
                getType()+"\n"+getMessage()+"\n";
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        //map.put("uid", hashCode());
        map.put("type", getType());
        map.put("message", getMessage());
        return map;
    }
}
