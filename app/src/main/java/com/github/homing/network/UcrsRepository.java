package com.github.homing.network;

import android.util.Log;

import com.github.homing.models.HeartbeatRequest;
import com.github.homing.models.HeartbeatResponse;
import com.github.homing.models.PingResponse;
import com.github.homing.models.TokenRegistrationRequest;
import com.github.homing.models.TokenRegistrationResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UcrsRepository {
    private static UcrsRepository instance;
    private final Map<String, String> headers = new HashMap<>();
    private UcrsService service;
    private String address;
    private String port;

    public static UcrsRepository getInstance() {
        if (instance == null) {
            instance = new UcrsRepository();
        }

        return instance;
    }

    public UcrsRepository setGatewayHost(String host) {
        headers.put("Host", host);
        return this;
    }

    public UcrsRepository setGatewayKey(String key) {
        headers.put("apiKey", key);
        return this;
    }

    public UcrsRepository setAddress(String address) {
        this.address = address;
        return this;
    }

    public UcrsRepository setPort(String port) {
        this.port = port;
        return this;
    }

    public UcrsRepository build() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.format("%s:%s", address, port))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(UcrsService.class);

        return this;
    }

    public void registerToken(String token, UcrsCallback callback) {
        Call<TokenRegistrationResponse> call = service.registerToken(headers,
                new TokenRegistrationRequest(token));

        call.enqueue(new Callback<TokenRegistrationResponse>() {
            @Override
            public void onResponse(Call<TokenRegistrationResponse> call,
                                   Response<TokenRegistrationResponse> response) {
                if (!response.isSuccessful()) {
                    callback.onError(response.code(), response.errorBody().toString());
                    return;
                }
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<TokenRegistrationResponse> call, Throwable t) {
                callback.onError(0, "Unable to perform request");
            }
        });
    }

    public void ping(UcrsCallback callback) {
        Call<PingResponse> call = service.ping(headers);

        call.enqueue(new Callback<PingResponse>() {
            @Override
            public void onResponse(Call<PingResponse> call, Response<PingResponse> response) {
                if (!response.isSuccessful()) {
                    callback.onError(response.code(), response.errorBody().toString());
                    return;
                }
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<PingResponse> call, Throwable t) {
                Log.e("UCRS", t.toString());
                callback.onError(0, "Unable to perform request");
            }
        });
    }

    public void heartbeat(String token, UcrsCallback callback) {
        Call<HeartbeatResponse> call = service.tokenHeartbeat(headers, token);

        call.enqueue(new Callback<HeartbeatResponse>() {
            @Override
            public void onResponse(Call<HeartbeatResponse> call, Response<HeartbeatResponse> response) {
                if (!response.isSuccessful()) {
                    callback.onError(response.code(), response.errorBody().toString());
                    return;
                }
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<HeartbeatResponse> call, Throwable t) {
                Log.e("UCRS", t.toString());
                callback.onError(0, "Unable to perform request");
            }
        });
    }
}
