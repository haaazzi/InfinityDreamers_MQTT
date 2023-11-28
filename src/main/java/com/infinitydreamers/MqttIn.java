package com.infinitydreamers;

import java.util.UUID;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttIn extends InputOutputNode {
    String[] args;

    public MqttIn(String[] args) {
        this.args = args;
    }

    @Override
    void process() {
        String publisherId = UUID.randomUUID().toString();

        Options options = new Options();
        options.addOption("an", "application", true, "application name");
        options.addOption("s", "sensor", true, "sensor");

        try (IMqttClient client = new MqttClient("tcp://ems.nhnacademy.com:1883", publisherId)) {

            client.connect();

            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine;
            commandLine = parser.parse(options, args);

            String topicFilter = "";
            String sensor = "";
            Message message = new Message();

            if (commandLine.hasOption("an")) {
                topicFilter = commandLine.getOptionValue("an");
            } else if (commandLine.hasOption("s")) {
                sensor = commandLine.getOptionValue("s");
                message.setSensor(sensor);
            }

            if (topicFilter.equals("")) {
                topicFilter = "application/#";
            }

            client.subscribe(topicFilter, (topic, msg) -> {
                if (!topic.contains("will")) {
                    message.setPayload(msg.toString());
                    output(message);
                }
            });

            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(100);
            }

            client.disconnect();

        } catch (MqttException | ParseException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
