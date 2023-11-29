package com.infinitydreamers;

import org.json.JSONObject;

public class Message {
    JSONObject json;
    String data;
    String sensor;
    boolean flag = false;

    Message() {
        this("");
    }

    Message(JSONObject json) {
        this.json = json;
    }

    Message(String data) {
        this.data = data;
    }

    public JSONObject getJson() {
        return json;
    }

    public String getData() {
        return data;
    }

    public String getSensor() {
        return sensor;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
