package com.infinitydreamers;

public class Main {
    public static void main(String[] args) {

        MqttIn in = new MqttIn(args);
        JsonNode json = new JsonNode();
        DeviceInfoNode deviceInfo = new DeviceInfoNode();
        TopicNode topicNode = new TopicNode();
        SensorNode sensorNode = new SensorNode();
        MqttOut out = new MqttOut();
        DebugNode debugNode1 = new DebugNode("MqttIn");
        DebugNode debugNode2 = new DebugNode("JsonNode");
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

        in.connectOutputWire(wire1);
        in.connectOutputWire(debugWire1);
        debugNode1.connectInputWire(debugWire1);

        json.connectInputWire(wire1);
        json.connectOutputWire(wire2);
        json.connectOutputWire(debugWire2);
        debugNode2.connectInputWire(debugWire2);

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
        json.start();
        deviceInfo.start();
        topicNode.start();
        sensorNode.start();
        out.start();
        debugNode1.start();
        debugNode2.start();
        debugNode3.start();
        debugNode4.start();
        debugNode5.start();
        debugNode6.start();
    }
}
