package com.infinitydreamers;

import java.util.UUID;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttOut extends InputOutputNode {

    @Override
    void process() {
        String publisherId = UUID.randomUUID().toString();
        if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {
            try (IMqttClient client = new MqttClient("tcp://localhost", publisherId)) {
                client.connect();
                Message message = getInputWire(0).get();
                if (message.getJson().has("topic")) {
                    String topic = message.getJson().get("topic").toString();
                    String data = message.getJson().get("payload").toString();
                    client.publish(topic, new MqttMessage(data.getBytes()));

                    client.disconnect();
                }
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}

// Message message = getInputWire(0).get();
// String topic = message.getData();
// String data = message.getJson().toString();
// // client.publish(topic, new MqttMessage(data.getBytes()));