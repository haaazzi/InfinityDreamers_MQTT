package com.infinitydreamers;

import java.util.ArrayList;
import java.util.List;

public class InputOutputNode extends ActiveNode {
    List<Wire> inputWires;
    List<Wire> outputWires;
    int successCount = 0;
    int failCount = 0;

    public InputOutputNode(String name) {
        super(name);

        inputWires = new ArrayList<>();
        outputWires = new ArrayList<>();
    }

    public InputOutputNode() {
        super();

        inputWires = new ArrayList<>();
        outputWires = new ArrayList<>();
    }

    public void connectInputWire(Wire wire) {
        inputWires.add(wire);
    }

    public void connectOutputWire(Wire wire) {
        outputWires.add(wire);
    }

    public void output(Message message) {
        for (Wire wire : outputWires) {
            wire.put(message);
        }
    }

    public int getInputWireCount() {
        return inputWires.size();
    }

    public int getOutputWireCount() {
        return outputWires.size();
    }

    public Wire getInputWire(int index) {
        return inputWires.get(index);
    }

    public Wire getOutputWire(int index) {
        return outputWires.get(index);
    }
}
