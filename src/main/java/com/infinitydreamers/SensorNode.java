package com.infinitydreamers;

import org.json.JSONObject;

public class SensorNode extends InputOutputNode {

    @Override
    void process() {
        if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {

            Message message = getInputWire(0).get();

            if (message.isFlag()) {
                JSONObject data = message.getJson();

                if (data.has("object")) {
                    JSONObject object = (JSONObject) data.get("object");
                    String commonTopic = message.getData();
                    String sensor = message.getSensor();
                    JSONObject newJson = new JSONObject();
                    JSONObject payload = new JSONObject();
                    Message newMessage = new Message();

                    if (sensor != null) {
                        for (String key : sensor.split(",")) {
                            newMessage = new Message();
                            newJson = new JSONObject();
                            payload = new JSONObject();
                            newJson.put("topic", commonTopic + "/e/" + key);
                            payload.put("time", System.currentTimeMillis());
                            payload.put("value", object.get(key));
                            newJson.put("payload", payload);

                            newMessage.setFlag(true);
                            newMessage.setJson(newJson);
                            newMessage.setData(commonTopic);

                            output(newMessage);
                        }
                    } else {
                        for (String key : object.keySet()) {
                            newMessage = new Message();
                            newJson = new JSONObject();
                            payload = new JSONObject();

                            newJson.put("topic", commonTopic + "/e/" + key);
                            payload.put("time", System.currentTimeMillis());
                            payload.put("value", object.get(key));
                            newJson.put("payload", payload);

                            newMessage.setFlag(true);
                            newMessage.setJson(newJson);
                            newMessage.setData(commonTopic);

                            output(newMessage);
                        }
                    }
                } else {
                    output(new Message());
                }
            }
        }
    }
}
