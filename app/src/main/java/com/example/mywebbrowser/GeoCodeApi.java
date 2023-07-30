package com.example.mywebbrowser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoCodeApi {

    @GET("direct")
    Call<List<GeoCode>> getGeoCodeData(
            @Query("q") String cityName,
            @Query("appid") String apiKey);
}
