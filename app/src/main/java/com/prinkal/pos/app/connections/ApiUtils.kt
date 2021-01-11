package com.prinkal.pos.app.connections

import com.prinkal.pos.app.constants.OpenExchangeRatesConstants

object ApiUtils {
    @JvmStatic
    val exchangeAPIService: APIService
        get() = RetrofitClient.getExchangeClient(OpenExchangeRatesConstants.BASE_EXCHANGE_RATE_URL)
                .create(APIService::class.java)
    val aPIService: APIService
        get() = RetrofitClient.getClient(OpenExchangeRatesConstants.BASE_API_URL).create(APIService::class.java)
}