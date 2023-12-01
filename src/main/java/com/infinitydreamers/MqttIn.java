package com.infinitydreamers;

import java.util.UUID;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttIn extends InputOutputNode {
    Message message;
    final String DEFAULT_TOPIC = "application/#";
    final String DEFAULT_URI = "tcp://ems.nhnacademy.com:1883";

    public MqttIn(Message message) {
        this.message = message;
    }

    @Override
    void process() {
        String publisherId = UUID.randomUUID().toString();
        String uri = message.getJson().has("uri") ? message.getJson().getString("uri") : DEFAULT_URI;

        try (IMqttClient client = new MqttClient(uri, publisherId)) {

            client.connect();
            String topicFilter = message.getJson().getString("topic");
            String sensor = message.getJson().has("sensor") ? message.getJson().getString("sensor") : null;

            if (topicFilter.equals("")) {
                topicFilter = DEFAULT_TOPIC;
            }

            client.subscribe(topicFilter, (topic, msg) -> {
                if (!topic.contains("will")) {
                    message.setFlag(true);
                    message.setSensor(sensor);
                    message.put("payload", msg.toString());
                    output(message);
                } else {
                    message.setFlag(false);
                }
            });

            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(100);
            }

            client.disconnect();

        } catch (MqttException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
