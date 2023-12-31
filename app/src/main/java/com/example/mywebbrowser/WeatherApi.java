package com.example.mywebbrowser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherApi {

    @GET("weather")
    Call<WeatherResponse> getWeatherData(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
