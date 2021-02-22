package com.prinkal.pos.app.connections

import com.prinkal.pos.app.db.entity.Administrator
import com.prinkal.pos.app.model.ApiResponse
import com.prinkal.pos.app.model.OpenExchangeRate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {
    @GET("/latest.json")
    fun getRates(
            @Query("app_id") app_id: String?,
            @Query("base") base_currency: String?
    ): Call<OpenExchangeRate?>?

    @POST("user")
    suspend fun createUser(@Body user: Administrator?): ApiResponse?
}