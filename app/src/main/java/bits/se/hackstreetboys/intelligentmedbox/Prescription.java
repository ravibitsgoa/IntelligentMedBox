package bits.se.hackstreetboys.intelligentmedbox;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Prescription {
    private Map<String, ArrayList<Long>> meds;
    private String expiryDate;
    private String id;

    public Prescription(){
        meds = new HashMap<>();
        expiryDate = "";
    }

    public Prescription(Map<String, Object> map) {
        //System.out.println(map);
        meds = new HashMap<>();
        for(Map.Entry<String, Object> entry: map.entrySet()) {
            String key = entry.getKey();
            switch (key) {
                case "expiry_date":
                    expiryDate = (String) entry.getValue();
                    break;
                case "id":
                    id = (String) entry.getValue();
                    break;
                case "medicines":
                    meds = (Map<String, ArrayList<Long>>) entry.getValue();
                    break;
                default:
                    //System.out.println(key+" "+ map.get(key));
                    //HashMap<String, Object> temp = (HashMap<String, Object>) entry.getValue();
                    //meds.put(key, (ArrayList<Long>) temp.get("Dosage"));
                    meds.put(key, (ArrayList<Long>) entry.getValue());
                    break;
            }
        }
    }

    public Map<String, ArrayList<Long>> getMeds(){
        return meds;
    }

    public String getExpiryDate(){
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate){
        this.expiryDate = expiryDate;
    }

    public void setMeds(Map<String, ArrayList<Long> > meds){
        this.meds = meds;
    }

    private String prettyPrintDosage(ArrayList<Long> dosage) {
        StringBuilder stringBuilder = new StringBuilder();
        final String[] times = {"breakfast", "lunch", "snacks", "dinner"};
        for(int i=0; i<8; i++) {
            int x = dosage.get(i).intValue();
            if(x == 0)
                continue;
            if(i%2==0) {
                stringBuilder.append("\nBefore ");
                stringBuilder.append(times[i/2]);
                stringBuilder.append(" ");
                stringBuilder.append(dosage.get(i));
            } else {
                stringBuilder.append("\nAfter ");
                stringBuilder.append(times[i/2]);
                stringBuilder.append(" ");
                stringBuilder.append(dosage.get(i));
            }
        }
        return stringBuilder.toString();
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<String, ArrayList<Long>> entry: meds.entrySet() ) {
            stringBuilder.append(entry.getKey()).
                    append(prettyPrintDosage(entry.getValue())).append("\n\n");
        }
        stringBuilder.append("Expiry Date: ").append(expiryDate);
        return stringBuilder.toString();
    }

}
