package com.infinitydreamers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Injector extends InputOutputNode{
    int transactionId = 0;

    @Override
    void process() {
        try (Socket socket = new Socket("172.18.0.1",11502)) {
            BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());

            int unitId = 1;

            // for (int i = 0; i < 10; i++) {
                // byte[] request = SimpleMB.addMBAP(++transactionId, unitId,SimpleMB.makeWriteMultiRequest(0, new int[]{1,2,3,4}));
                // byte[] request = SimpleMB.addMBAP(++transactionId, unitId,SimpleMB.makeWriteSingleRequest(0, 10));
                byte[] request = ModbusResponse.addMBAP(++transactionId, unitId, ModbusResponse.makeReadInputRegistersRequest(0, 5));
                // byte[] request = SimpleMB.addMBAP(++transactionId, unitId, SimpleMB.makeReadHoldingRegistersRequest(0, 5));

                outputStream.write(request);
                outputStream.flush();
                
                byte[] response = new byte[512];
                int recieveLength = inputStream.read(response, 0, response.length);
    
                System.out.println(Arrays.toString(Arrays.copyOfRange(response, 0, recieveLength)));
                Thread.sleep(5000);
            // }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

    }
}
