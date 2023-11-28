package com.infinitydreamers;

public class Test {
    public static void main(String[] args) {
        MqttIn in = new MqttIn(args);
        JsonNode json = new JsonNode();
        DeviceInfoNode deviceInfo = new DeviceInfoNode();

        Wire wire1 = new Wire();
        Wire wire2 = new Wire();

        in.connectOutputWire(wire1);
        json.connectInputWire(wire1);
        json.connectOutputWire(wire2);
        deviceInfo.connectInputWire(wire2);

        in.start();
        json.start();
        deviceInfo.start();
    }
}
