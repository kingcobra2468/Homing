package com.github.homing.network;

public interface UcrsCallback {
    void onSuccess();

    void onError(int code, String message);
}
