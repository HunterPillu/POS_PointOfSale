package com.prinkal.pos.app.model;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

public class ApiResponse {
    private String error;
    private int status;
    private JsonObject data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    @NotNull
    @Override
    public String toString() {
        return "ApiResponse{" +
                "error='" + error + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
