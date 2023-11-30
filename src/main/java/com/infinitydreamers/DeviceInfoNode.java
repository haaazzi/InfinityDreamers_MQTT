package com.infinitydreamers;

import org.json.JSONObject;

public class DeviceInfoNode extends InputOutputNode {

    @Override
    void process() {
        String deviceInfoString = "deviceInfo";
        String objectString = "object";
        if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {
            Message message = getInputWire(0).get();

            if (message.isFlag()) {
                JSONObject json = message.getJson();
                if (json.has(deviceInfoString) && json.has(objectString)) {
                    JSONObject newJson = new JSONObject();
                    JSONObject deviceInfo = (JSONObject) ((JSONObject) json.get(deviceInfoString)).get("tags");

                    deviceInfo.put("deviceId", ((JSONObject) json.get(deviceInfoString)).get("devEui"));
                    newJson.put(deviceInfoString, deviceInfo);// tags
                    newJson.put(objectString, json.get(objectString));

                    message.setJson(newJson);
                    output(message);
                } else {
                    output(new Message());
                }
            }
        }
    }
}
