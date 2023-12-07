package com.infinitydreamers;

public class ModbusMain {
    public static void main(String[] args) {
        ModbusServer modbusServer = new ModbusServer("ModbusServer");
        Injector injector = new Injector();
        
        injector.start();
        modbusServer.start();
    }
}
