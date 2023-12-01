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
                JSONObject json = new JSONObject(message.getJson().get("payload").toString());

                if (json.has(deviceInfoString) && json.has(objectString) && json.has(deviceInfoString)) {
                    JSONObject newJson = new JSONObject();
                    JSONObject deviceInfo = (JSONObject) ((JSONObject) json.get(deviceInfoString)).get("tags");

                    deviceInfo.put("deviceId", ((JSONObject) json.get(deviceInfoString)).get("devEui"));
                    newJson.put(deviceInfoString, deviceInfo);
                    newJson.put(objectString, json.get(objectString));

                    message.put("payload", newJson.toString());
                    message.setFlag(true);
                    output(message);
                } else {
                    output(new Message());
                }
            }
        }
    }
}
