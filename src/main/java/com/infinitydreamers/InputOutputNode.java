package com.infinitydreamers;

import java.util.ArrayList;
import java.util.List;

public class InputOutputNode extends ActiveNode {
    List<Wire> inputWires;
    List<Wire> outputWires;

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

    public List<Wire> getInputWire() {
        return inputWires;
    }

    public List<Wire> getOutputWire() {
        return outputWires;
    }
}
