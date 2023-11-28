package com.infinitydreamers;

import org.json.JSONObject;

public class Message {
    JSONObject json;
    String payload;
    String sensor;

    Message() {
        this("");
    }

    Message(JSONObject json) {
        this.json = json;
    }

    Message(String payload) {
        this.payload = payload;
    }

    public JSONObject getJson() {
        return json;
    }

    public String getPayload() {
        return payload;
    }

    public String getSensor() {
        return sensor;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

}
