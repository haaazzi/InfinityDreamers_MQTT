package com.infinitydreamers;

import org.json.JSONObject;

public class JsonNode extends InputOutputNode {

    @Override
    void process() {
        if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {
            Message message = getInputWire(0).get();
            String payload = message.getPayload();
            JSONObject newJson = new JSONObject(payload);

            message.setJson(newJson);
            output(message);
        }
    }
}
