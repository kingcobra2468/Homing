package com.github.homing.network;

import com.github.homing.models.HeartbeatResponse;
import com.github.homing.models.PingResponse;
import com.github.homing.models.TokenRegistrationRequest;
import com.github.homing.models.TokenRegistrationResponse;
import com.github.homing.models.UpdateTokenRequest;
import com.github.homing.models.UpdateTokenResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UcrsService {
    @POST("token/register")
    Call<TokenRegistrationResponse> registerToken(@HeaderMap Map<String, String> headers,
                                                  @Body TokenRegistrationRequest tokenRegistrationRequest);

    @PUT("token/{rt}/heartbeat")
    Call<HeartbeatResponse> tokenHeartbeat(@HeaderMap Map<String, String> headers,
                                           @Path("rt") String registrationToken);

    @PUT("token/{rt}/update-rt")
    Call<UpdateTokenResponse> updateToken(@HeaderMap Map<String, String> headers,
                                          @Path("rt") String registrationToken,
                                          @Body UpdateTokenRequest updateTokenRequest);

    @GET("ping")
    Call<PingResponse> ping(@HeaderMap Map<String, String> headers);
}
