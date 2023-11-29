package com.infinitydreamers;

import org.json.JSONObject;

public class DeviceInfoNode extends InputOutputNode {

    @Override
    void process() {
        if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {
            Message message = getInputWire(0).get();

            JSONObject json = message.getJson();
            if (json.has("deviceInfo") && json.has("object")) {
                JSONObject newJson = new JSONObject();
                newJson.put("deviceInfo", ((JSONObject) json.get("deviceInfo")).get("tags")); // tags
                newJson.put("object", json.get("object"));
                message.setJson(newJson);
                output(message);
            }

        }
    }
}
