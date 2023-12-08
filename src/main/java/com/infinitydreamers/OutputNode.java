package com.infinitydreamers;

import java.util.ArrayList;
import java.util.List;

public abstract class OutputNode extends ActiveNode {
    List<Wire> outputWires;

    OutputNode(String name) {
        super(name);

        outputWires = new ArrayList<>();
    }

    public void connectOutputWire(Wire wire) {
        outputWires.add(wire);
    }

    public int getOutputWireCount() {
        return outputWires.size();
    }

    public Wire getOutputWire(int index) {
        return outputWires.get(index);
    }

    public void output(Message message) {
        for (Wire wire : outputWires) {
            wire.put(message);
        }
    }
}
