package com.infinitydreamers;

public class Test {
    public static void main(String[] args) {
        MqttIn in = new MqttIn(args);
        JsonNode json = new JsonNode();
        DeviceInfoNode deviceInfo = new DeviceInfoNode();
        TopicNode topicNode = new TopicNode();
        SensorNode sensorNode = new SensorNode();
        MqttOut out = new MqttOut();

        Wire wire1 = new Wire();
        Wire wire2 = new Wire();
        Wire wire3 = new Wire();
        Wire wire4 = new Wire();
        Wire wire5 = new Wire();

        in.connectOutputWire(wire1);
        json.connectInputWire(wire1);
        json.connectOutputWire(wire2);
        deviceInfo.connectInputWire(wire2);
        deviceInfo.connectOutputWire(wire3);
        topicNode.connectInputWire(wire3);
        topicNode.connectOutputWire(wire4);
        sensorNode.connectInputWire(wire4);
        sensorNode.connectOutputWire(wire5);
        out.connectInputWire(wire5);

        in.start();
        json.start();
        deviceInfo.start();
        topicNode.start();
        sensorNode.start();
        out.start();
    }
}
