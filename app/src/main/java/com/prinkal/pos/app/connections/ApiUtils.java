package com.prinkal.pos.app.connections;

import static com.prinkal.pos.app.constants.OpenExchangeRatesConstants.BASE_EXCHANGE_RATE_URL;

public class ApiUtils {

    private ApiUtils() {
    }

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_EXCHANGE_RATE_URL)
                .create(APIService.class);
    }
}