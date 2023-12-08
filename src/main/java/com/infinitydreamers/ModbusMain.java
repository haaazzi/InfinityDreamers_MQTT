package com.infinitydreamers;

public class ModbusMain {
    public static void main(String[] args) {
        Injector injector = new Injector("Injector");
        ModbusServer modbusServer = new ModbusServer("ModbusServer");
        ModbusClient modbusClient = new ModbusClient();

        injector.start();
        modbusServer.start();
        modbusClient.start();
    }
}
