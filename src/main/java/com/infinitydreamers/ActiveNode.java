package com.infinitydreamers;

import java.util.UUID;

public abstract class ActiveNode extends Node implements Runnable {
    Thread thread;

    ActiveNode() {
        super();
    }

    ActiveNode(String name) {
        super(name);
    }

    ActiveNode(String name, UUID id) {
        super(name, id);
    }

    public void start() {
        thread = new Thread(this, getName());
        thread.start();
    }

    void preprocess() {

    }

    void process() {

    }

    @Override
    public void run() {
        preprocess();
        while (thread.isAlive()) {
            process();
        }
    }

}
