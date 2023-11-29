package com.infinitydreamers;

import org.json.JSONObject;

public class SensorNode extends InputOutputNode {

    @Override
    void process() {
        if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {
            Message message = getInputWire(0).get();

            JSONObject data = message.getJson();
            JSONObject object = (JSONObject) data.get("object");
            String commonTopic = message.getData();
            String sensor = message.getSensor();
            JSONObject newJson = new JSONObject();
            JSONObject payload = new JSONObject();

            if (sensor != null) {
                for (String key : sensor.split(",")) {
                    newJson = new JSONObject();
                    payload = new JSONObject();

                    newJson.put("topic", commonTopic + "/e/" + key);
                    payload.put("time", System.currentTimeMillis());
                    payload.put("value", object.get(key));
                    newJson.put("payload", payload);
                }
            } else {
                for (String key : object.keySet()) {
                    newJson = new JSONObject();
                    payload = new JSONObject();

                    newJson.put("topic", commonTopic + "/e/" + key);
                    payload.put("time", System.currentTimeMillis());
                    payload.put("value", object.get(key));
                    newJson.put("payload", payload);
                }
            }

            message.setData(commonTopic);
            output(message);
        }
    }
}
