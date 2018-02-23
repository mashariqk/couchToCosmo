package com.footlocker.azure;

public class Row {
    String id;
    String key;
    Value value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Row{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
