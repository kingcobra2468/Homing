package com.github.homing.network;

import com.github.homing.models.HeartbeatResponse;
import com.github.homing.models.PingResponse;
import com.github.homing.models.TokenRegistrationRequest;
import com.github.homing.models.TokenRegistrationResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * The interface ucrs service for mapping ucrs endpoints.
 */
public interface UcrsService {
    /**
     * Push new registration token.
     *
     * @param headers                  headers for api gateway
     * @param tokenRegistrationRequest the token registration request
     * @return the call
     */
    @POST("token/register")
    Call<TokenRegistrationResponse> registerToken(@HeaderMap Map<String, String> headers,
                                                  @Body TokenRegistrationRequest tokenRegistrationRequest);

    /**
     * Token heartbeat to let ucrs know that this device is alive and to reset TLL for he given
     * token.
     *
     * @param headers           headers for api gateway
     * @param registrationToken the registration token
     * @return the call
     */
    @PUT("token/{rt}/heartbeat")
    Call<HeartbeatResponse> tokenHeartbeat(@HeaderMap Map<String, String> headers,
                                           @Path("rt") String registrationToken);

    /**
     * Ping ucrs to check if the service is reachable.
     *
     * @param headers headers for api gateway
     * @return the call
     */
    @GET("ping")
    Call<PingResponse> ping(@HeaderMap Map<String, String> headers);
}
