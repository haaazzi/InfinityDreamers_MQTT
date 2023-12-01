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

    void postprocess() {

    }

    @Override
    public void run() {
        preprocess();
        while (thread.isAlive()) {
            process();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        postprocess();

    }

}
