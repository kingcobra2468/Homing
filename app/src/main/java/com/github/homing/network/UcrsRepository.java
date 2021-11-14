package com.github.homing.network;

import android.util.Log;

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

/**
 * The ucrs repository for interacting with ucrs service.
 */
public class UcrsRepository {
    private static UcrsRepository instance;
    private final Map<String, String> headers = new HashMap<>();
    private UcrsService service;
    private String keyHeaderName = "api-key";
    private String address;
    private String port;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UcrsRepository getInstance() {
        if (instance == null) {
            instance = new UcrsRepository();
        }

        return instance;
    }

    /**
     * Sets gateway host.
     *
     * @param host the host
     * @return current instance
     */
    public UcrsRepository setGatewayHost(String host) {
        headers.put("Host", host);
        return this;
    }

    /**
     * Sets gateway key for key-based auth.
     *
     * @param key the key
     * @return current instance
     */
    public UcrsRepository setGatewayKey(String key) {
        headers.put(keyHeaderName, key);
        return this;
    }

    /**
     * Sets address of gateway or stand alone service.
     *
     * @param address the address
     * @return current instance
     */
    public UcrsRepository setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Sets port of gateway or stand alone service.
     *
     * @param port the port
     * @return current instance
     */
    public UcrsRepository setPort(String port) {
        this.port = port;
        return this;
    }

    /**
     * Sets key header name if api gateway is used.
     *
     * @param newKeyHeaderName the new key header name
     * @return current instance
     */
    public UcrsRepository setKeyHeaderName(String newKeyHeaderName) {
        String key = "";
        // remove old key header if it exists
        if (headers.containsKey(keyHeaderName)) {
            key = headers.remove(keyHeaderName);
        }
        keyHeaderName = newKeyHeaderName;
        setGatewayKey(key);

        return this;
    }

    /**
     * Reset headers if any have been set if api gateway is enabled.
     *
     * @return current instance
     */
    public UcrsRepository resetHeaders() {
        headers.clear();
        return this;
    }

    /**
     * Build retrofit enpoints for the ucrs service.
     *
     * @return current instance
     */
    public UcrsRepository build() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.format("%s:%s", address, port))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(UcrsService.class);

        return this;
    }

    /**
     * Push registration token to ucrs.
     *
     * @param token    the registration token
     * @param callback the ucrs callback
     */
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

    /**
     * Ping ucrs to check if service is alive and/or the settings are valid.
     *
     * @param callback the callback
     */
    public void ping(UcrsCallback callback) {
        Call<PingResponse> call = service.ping(headers);
        call.enqueue(new Callback<PingResponse>() {
            @Override
            public void onResponse(Call<PingResponse> call, Response<PingResponse> response) {
                if (!response.isSuccessful()) {
                    callback.onError(response.code(), "Unable to reach UCRS");
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

    /**
     * Heartbeat to let ucrs know that this registration token is part of an active device.
     *
     * @param token    the registration token
     * @param callback the callback
     */
    public void heartbeat(String token, UcrsCallback callback) {
        Call<HeartbeatResponse> call = service.tokenHeartbeat(headers, token);

        call.enqueue(new Callback<HeartbeatResponse>() {
            @Override
            public void onResponse(Call<HeartbeatResponse> call,
                                   Response<HeartbeatResponse> response) {
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
