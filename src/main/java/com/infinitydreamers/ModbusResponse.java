package com.infinitydreamers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ModbusResponse {

    ModbusResponse() {

    }

    // Request
    public static byte[] makeReadHoldingRegistersRequest(int address, int quantity) {
        byte[] frame = new byte[5];

        // int -> byte
        ByteBuffer b = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
        b.putInt(address);

        // PDU's read function code
        frame[0] = 0x03;

        // PDU's data
        frame[1] = b.get(2);
        frame[2] = b.get(3);

        b.clear();
        b.putInt(quantity);

        frame[3] = b.get(2);
        frame[4] = b.get(3);

        return frame;
    }

    public static byte[] makeReadInputRegistersRequest(int address, int quantity) {
        byte[] frame = new byte[5];

        // int -> byte
        ByteBuffer b = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
        b.putInt(address);

        // PDU's read function code
        frame[0] = 0x04;

        // PDU's data
        frame[1] = b.get(2);
        frame[2] = b.get(3);

        b.clear();
        b.putInt(quantity);

        frame[3] = b.get(2);
        frame[4] = b.get(3);

        return frame;
    }

    // Request - Write
    public static byte[] makeWriteSingleRequest(int address, int quantity) {
        byte[] frame = new byte[5];

        ByteBuffer b = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
        b.putInt(address);

        frame[0] = 0x06;

        frame[1] = b.get(2);
        frame[2] = b.get(3);

        b.clear();
        b.putInt(quantity);

        frame[3] = b.get(2);
        frame[4] = b.get(3);

        return frame;
    }

    // Request - Write Multi
    public static byte[] makeWriteMultiRequest(int address, int[] registerValues) {
        // 13 minimum
        int quantity = registerValues.length;
        byte[] frame = new byte[6 + 2 * quantity];

        ByteBuffer b = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
        b.putInt(address);

        // Function code - 16
        frame[0] = 0x10;

        // Address
        frame[1] = b.get(2);
        frame[2] = b.get(3);

        // Quantity of Registers
        frame[3] = 0;
        frame[4] = (byte) quantity;

        // Byte Count
        frame[5] = (byte) (2 * quantity);

        b.clear();
        // Values
        for (int i = 0; i < quantity; i++) {
            int value = registerValues[i];
            b.putInt(value);
            frame[6 + 2 * i] = b.get(2);
            frame[7 + 2 * i] = b.get(3);
            b.clear();
        }

        return frame;
    }

    // Response - Read
    public static byte[] makeReadHoldingRegistersResponse(int[] registers) {
        byte[] frame = new byte[1 + 1 + registers.length * 2];

        frame[0] = 0x03;

        frame[1] = (byte) (registers.length * 2);

        for (int i = 0; i < registers.length; i++) {
            frame[2 + i * 2] = (byte) ((registers[i] >> 8) & 0xFF);
            frame[3 + i * 2] = (byte) (registers[i] & 0xFF);
        }

        return frame;
    }

    public static byte[] makeReadInputRegistersResponse(int[] registers) {
        byte[] frame = new byte[2 + 2 * registers.length];

        ByteBuffer b = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);

        frame[0] = 0x04;

        frame[1] = (byte) (2 * registers.length);

        for (int i = 0; i < registers.length; i++) {
            b.putInt(registers[i]);
            frame[2 + 2 * i] = b.get(2);
            frame[3 + 2 * i] = b.get(3);
            b.clear();
        }

        return frame;
    }

    // Response - Write
    public static byte[] makeWriteSingleResponse(int[] registers) {
        byte[] frame = new byte[2 + 2 * registers.length];

        ByteBuffer b = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);

        frame[0] = 0x06;

        frame[1] = (byte) (2 * registers.length);

        for (int i = 0; i < registers.length; i++) {
            b.putInt(registers[i]);
            frame[2 + 2 * i] = b.get(2);
            frame[3 + 2 * i] = b.get(3);
            b.clear();
        }

        return frame;
    }

    public static byte[] getResponse(byte[] buffer) {

        int[] holdingRegisters = new int[100];
        int transactionId = ((buffer[0] << 8) | Byte.toUnsignedInt(buffer[1]));
        int functionCode = buffer[7];
        int unitId = buffer[6];
        int address = ((buffer[8] << 8) | Byte.toUnsignedInt(buffer[9]));
        int quantity = ((buffer[10] << 8) | Byte.toUnsignedInt(buffer[11]));

        for (int i = 0; i < holdingRegisters.length; i++) {
            holdingRegisters[i] = i;
        }

        byte[] result = new byte[quantity]; 

        switch (functionCode) {
            
            case 3:
                result = makeReadHoldingRegistersResponse(Arrays.copyOfRange(holdingRegisters, address, address+quantity));
                break;
            
            case 4:
                result = makeReadInputRegistersResponse(Arrays.copyOfRange(holdingRegisters, address, address+quantity));
                break;
            
            case 6:
                result = makeWriteSingleResponse(Arrays.copyOfRange(holdingRegisters, address, address+quantity));
                break;
            
            case 16:
                
                break;

            default:
                System.err.println("Function code is not 3, 4, 6 or 16!");
                break;
        }

        return addMBAP(transactionId, unitId, result);
    }

    // Header Wrapper - Read + Write
    public static byte[] addMBAP(int transactionId, int unitId, byte[] pdu) {
        byte[] adu = new byte[7 + pdu.length];
        // System.out.println((byte) transactionId);
        ByteBuffer b = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);

        b.putInt(transactionId);
        // System.out.println(b.get(0) + "," + b.get(1) + "," + b.get(2) + "," +
        // b.get(3));

        adu[0] = b.get(2);
        adu[1] = b.get(3);
        adu[2] = 0;
        adu[3] = 0;
        adu[4] = 0;
        adu[5] = (byte) (pdu.length + 1);
        adu[6] = (byte) unitId;
        System.arraycopy(pdu, 0, adu, 7, pdu.length);

        return adu;
    }

}
