package com.infinitydreamers;

public class Test {
    public static void main(String[] args) {
        MqttIn in = new MqttIn(args);
        JsonNode json = new JsonNode();
        Wire wire1 = new Wire();

        in.connectOutputWire(wire1);
        json.connectInputWire(wire1);

        in.start();
        json.start();
    }
}
