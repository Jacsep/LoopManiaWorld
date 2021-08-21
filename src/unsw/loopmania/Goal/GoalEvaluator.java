package unsw.loopmania.Goal;

import java.util.Iterator;

import org.json.JSONObject;

public class GoalEvaluator {
    public boolean evaluate(JSONObject obj) {

        Iterator<String> keys = obj.keys();
        
        while(keys.hasNext()) {
            String key = keys.next();
            System.out.println(key);
            if (key.equals("goal")) {
                String body = obj.getString(key);
            } else {
                int body = obj.getInt(key);
            }
            
        }
        return true;
    }
}
