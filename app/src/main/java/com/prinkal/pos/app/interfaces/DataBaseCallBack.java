package com.prinkal.pos.app.interfaces;

public interface DataBaseCallBack {
    void onSuccess(Object responseData, String successMsg);

    void onFailure(int errorCode, String errorMsg);
}
