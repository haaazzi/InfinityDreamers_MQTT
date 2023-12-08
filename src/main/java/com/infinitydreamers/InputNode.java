package com.infinitydreamers;

import java.util.ArrayList;
import java.util.List;

public abstract class InputNode extends ActiveNode {
    List<Wire> inputWires;

    InputNode(String name) {
        super(name);

        inputWires = new ArrayList<>();
    }

    public void connectInputWire(Wire wire) {
        inputWires.add(wire);
    }

    public int getInputWireCount() {
        return inputWires.size();
    }

    public Wire getInputWire(int index) {
        return inputWires.get(index);
    }
}
