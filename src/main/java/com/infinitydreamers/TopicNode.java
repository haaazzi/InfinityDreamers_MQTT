package com.infinitydreamers;

import java.util.Iterator;

import org.json.JSONObject;

public class TopicNode extends InputOutputNode {

    @Override
    void process() {
        if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {

            Message message = getInputWire(0).get();

            if (message.isFlag()) {
                StringBuilder commonTopic = new StringBuilder("data");

                JSONObject data = message.getJson();
                JSONObject info = (JSONObject) data.get("deviceInfo");

                Iterator<String> i = info.keys();

                while (i.hasNext()) {
                    String key = i.next();
                    switch (key) {
                        case "site":
                            commonTopic.append("/s/" + info.get(key));
                            break;
                        case "deviceId":
                            commonTopic.append("/d/" + info.get(key));
                            break;
                        case "branch":
                            commonTopic.append("/b/" + info.get(key));
                            break;
                        case "place":
                            commonTopic.append("/p/" + info.get(key));
                            break;
                        default:
                            break;
                    }
                }

                message.setFlag(true);
                message.setData(commonTopic.toString());

                output(message);
            }
        }
    }
}
