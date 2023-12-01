package com.infinitydreamers;

import org.json.JSONObject;

public class Message {
    JSONObject json;

    Message() {
        this(new JSONObject());
    }

    Message(JSONObject json) {
        this.json = json;
    }

    public void put(String key, String value) {
        json.put(key, value);
    }

    public void setSensor(String sensor) {
        put("sensor", sensor);
    }

    public void setFlag(boolean flag) {
        put("flag", String.valueOf(flag));
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public boolean isFlag() {
        return json.has("flag");
    }

    public boolean hasJson() {
        return json != null;
    }
}
