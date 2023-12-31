package com.infinitydreamers;

import java.util.UUID;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttIn extends InputOutputNode {
    Message message;
    static final String DEFAULT_TOPIC = "application/#";
    static final String DEFAULT_URI = "tcp://ems.nhnacademy.com:1883";
    IMqttClient client;

    public MqttIn(Message message) {
        this.message = message;
    }

    @Override
    void preprocess() {
        String publisherId = UUID.randomUUID().toString();
        String uri = message.getJson().has("uri") ? message.getJson().getString("uri") : DEFAULT_URI;

        try {
            client = new MqttClient(uri, publisherId);
            client.connect();
            String topicFilter = message.getJson().has("inputTopic") ? message.getJson().getString("inputTopic") : DEFAULT_TOPIC;
            String sensor = message.getJson().has("sensor") ? message.getJson().getString("sensor") : null;
            
            client.subscribe(topicFilter, (topic, msg) -> {
                if (topic.contains("device")) {
                    // message = new Message();
                    message.setFlag(true);
                    message.setSensor(sensor);
                    message.put("payload", msg.toString());
                    output(message);
                } else {
                    Message fail = new Message();
                    fail.put("fail", "Topic does not contain device");
                    fail.setFlag(false);
                    output(fail);
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    void postprocess() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
