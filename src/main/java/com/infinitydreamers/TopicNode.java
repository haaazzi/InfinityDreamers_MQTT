package com.infinitydreamers;


import java.util.Iterator;

import org.json.JSONObject;

public class TopicNode extends InputOutputNode{

    @Override
    void process() {
        if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {
            Message message = getInputWire(0).get();
            String commonTopic = "data";

            JSONObject data = message.getJson();
            JSONObject info = (JSONObject)data.get("deviceInfo");

            Iterator<String> i = info.keys();
            while (i.hasNext()) {
                String key = i.next();
                switch (key) {
                    case "site":
                        commonTopic += "/s/" + info.get(key);         
                        break;
                    case "name":
                        commonTopic += "/n/" + info.get(key);         
                        break;
                    case "branch":
                        commonTopic += "/b/" + info.get(key);         
                        break;
                    case "place":
                        commonTopic += "/p/" + info.get(key);         
                        break;
                    default:
                        break;
                }
            };
            
            message.setData(commonTopic);
            output(message);
        }
    }
}
