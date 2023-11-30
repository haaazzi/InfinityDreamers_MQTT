package com.infinitydreamers;

import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

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
        options.addOption("c", "configure", true, "configure");

        try (IMqttClient client = new MqttClient("tcp://ems.nhnacademy.com:1883", publisherId)) {

            client.connect();

            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine;
            commandLine = parser.parse(options, args);

            String topicFilter = "";
            String sensor = "";
            String path = "";
            Message message = new Message();

            if (commandLine.hasOption("an")) {
                topicFilter = commandLine.getOptionValue("an");
            } else if (commandLine.hasOption("s")) {
                sensor = commandLine.getOptionValue("s");
                message.setSensor(sensor);
            } else if (commandLine.hasOption("c")) {
                path = commandLine.getOptionValue("c");
                JSONParser jsonParser = new JSONParser();
                Object obj = jsonParser.parse(new FileReader("./" + path));
                JSONObject jsonObject = (JSONObject) obj;

                topicFilter = (String) jsonObject.get("name");
                message.setSensor((String) jsonObject.get("sensor"));

            }

            if (topicFilter.equals("")) {
                topicFilter = "application/#";
            }

            client.subscribe(topicFilter, (topic, msg) -> {
                if (!topic.contains("will")) {
                    message.setFlag(true);
                    message.setData(msg.toString());
                    output(message);
                } else {
                    message.setFlag(false);
                }
            });

            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(100);
            }

            client.disconnect();

        } catch (MqttException | ParseException | InterruptedException | IOException
                | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}
