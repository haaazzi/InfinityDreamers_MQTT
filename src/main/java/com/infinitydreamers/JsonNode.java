// package com.infinitydreamers;

// import org.json.JSONObject;

// public class JsonNode extends InputOutputNode {

// @Override
// void process() {
// if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {
// Message message = getInputWire(0).get();
// if (message.isFlag()) {

// String payload = message.getData();
// JSONObject newJson = new JSONObject(payload);

// message.setFlag(true);
// message.setJson(newJson);
// output(message);
// }
// }
// }
// }
