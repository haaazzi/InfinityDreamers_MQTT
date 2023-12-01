package com.infinitydreamers;

import java.util.Iterator;

import org.json.JSONObject;

public class TopicNode extends InputOutputNode {

    @Override
    void process() {
        if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {

            Message message = getInputWire(0).get();

            if (message.isFlag() && message.getJson().has("payload")) {
                StringBuilder commonTopic = null;

                JSONObject data = new JSONObject(message.getJson().get("payload").toString());
                JSONObject info = (JSONObject) data.get("deviceInfo");

                if (message.getJson().has("outputTopic")) {
                    commonTopic = new StringBuilder("data");
                    System.out.println(message.getJson().toString(4));
                    for (String key : message.getJson().getString("outputTopic").split(",")) {
                        commonTopic.append("/"+key.charAt(0)+"/"+info.get(key));
                    }

                } else {
                    commonTopic = new StringBuilder("data");
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
                }

                message.setFlag(true);
                message.put("outputTopic", commonTopic.toString());
                commonTopic.delete(0, commonTopic.length()-1);
                
                output(message);
            }
        }
    }
}
