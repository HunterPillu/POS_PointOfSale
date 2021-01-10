package com.prinkal.pos.app.connections;

import com.prinkal.pos.app.db.entity.Administrator;
import com.prinkal.pos.app.model.ApiResponse;
import com.prinkal.pos.app.model.OpenExchangeRate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @GET("/latest.json")
    Call<OpenExchangeRate> getRates(@Query("app_id") String app_id,
                                    @Query("base") String base_currency);

    @POST("user")
    Call<ApiResponse> createUser(@Body Administrator user);
}