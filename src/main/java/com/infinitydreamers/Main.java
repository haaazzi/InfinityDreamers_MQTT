package com.infinitydreamers;

import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    public static void main(String[] args) {
        try {
            Options options = new Options();
            options.addOption("an", "application", true, "application name");
            options.addOption("s", "sensor", true, "sensor");
            options.addOption("c", "configure", true, "configure");
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine;
            commandLine = parser.parse(options, args);

            String topicFilter = "";
            String sensor = "";
            String filePath = "";
            Message message = new Message();

            if (commandLine.hasOption("an")) {
                topicFilter = commandLine.getOptionValue("an");
            } else if (commandLine.hasOption("s")) {
                sensor = commandLine.getOptionValue("s");
                message.setSensor(sensor);
            } else if (commandLine.hasOption("c")) {
                filePath = commandLine.getOptionValue("c");
                JSONParser jsonParser = new JSONParser();
                Object obj = jsonParser.parse(new FileReader("./" + filePath));
                JSONObject jsonObject = (JSONObject) obj;

                topicFilter = (String) jsonObject.get("name");
                sensor = (String) jsonObject.get("sensor");

                message.put("topic", topicFilter);
                message.setSensor(sensor);
            }

            MqttIn in = new MqttIn(message);
            // JsonNode json = new JsonNode();
            DeviceInfoNode deviceInfo = new DeviceInfoNode();
            TopicNode topicNode = new TopicNode();
            SensorNode sensorNode = new SensorNode();
            MqttOut out = new MqttOut();
            DebugNode debugNode1 = new DebugNode("MqttIn");
            // DebugNode debugNode2 = new DebugNode("JsonNode");
            DebugNode debugNode3 = new DebugNode("DeviceNode");
            DebugNode debugNode4 = new DebugNode("TopicNode");
            DebugNode debugNode5 = new DebugNode("SensorNode");
            DebugNode debugNode6 = new DebugNode("MqttOut");

            Wire wire1 = new Wire();
            Wire wire2 = new Wire();
            Wire wire3 = new Wire();
            Wire wire4 = new Wire();
            Wire wire5 = new Wire();

            Wire debugWire1 = new Wire();
            Wire debugWire2 = new Wire();
            Wire debugWire3 = new Wire();
            Wire debugWire4 = new Wire();
            Wire debugWire5 = new Wire();
            Wire debugWire6 = new Wire();

            in.connectOutputWire(wire2);
            in.connectOutputWire(debugWire2);
            debugNode1.connectInputWire(debugWire2);

            // json.connectInputWire(wire1);
            // json.connectOutputWire(wire2);
            // json.connectOutputWire(debugWire2);
            // debugNode2.connectInputWire(debugWire2);

            deviceInfo.connectInputWire(wire2);
            deviceInfo.connectOutputWire(wire3);
            deviceInfo.connectOutputWire(debugWire3);
            debugNode3.connectInputWire(debugWire3);

            topicNode.connectInputWire(wire3);
            topicNode.connectOutputWire(wire4);
            topicNode.connectOutputWire(debugWire4);
            debugNode4.connectInputWire(debugWire4);

            sensorNode.connectInputWire(wire4);
            sensorNode.connectOutputWire(wire5);
            sensorNode.connectOutputWire(debugWire5);
            debugNode5.connectInputWire(debugWire5);

            out.connectInputWire(wire5);
            out.connectOutputWire(debugWire6);
            debugNode6.connectInputWire(debugWire6);

            in.start();
            // json.start();
            deviceInfo.start();
            topicNode.start();
            sensorNode.start();
            out.start();
            debugNode1.start();
            // debugNode2.start();
            debugNode3.start();
            debugNode4.start();
            debugNode5.start();
            debugNode6.start();
        } catch (ParseException | IOException | org.json.simple.parser.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
