package com.prinkal.pos.app.connections;

import com.prinkal.pos.app.model.OpenExchangeRate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("/latest.json")
    Call<OpenExchangeRate> getRates(@Query("app_id") String app_id,
                                    @Query("base") String base_currency);
}