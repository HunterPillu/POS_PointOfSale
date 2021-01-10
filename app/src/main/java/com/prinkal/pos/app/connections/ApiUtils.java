package com.prinkal.pos.app.connections;

import static com.prinkal.pos.app.constants.OpenExchangeRatesConstants.BASE_API_URL;
import static com.prinkal.pos.app.constants.OpenExchangeRatesConstants.BASE_EXCHANGE_RATE_URL;

public class ApiUtils {

    private ApiUtils() {
    }

    public static APIService getExchangeAPIService() {
        return RetrofitClient.getExchangeClient(BASE_EXCHANGE_RATE_URL)
                .create(APIService.class);
    }

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_API_URL)
                .create(APIService.class);
    }
}