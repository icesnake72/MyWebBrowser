package com.example.mywebbrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {    // implements OnMapReadyCallback

//    protected SupportMapFragment mapFragment;

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "555e77f38f5b28cf6481409e56f93ad4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

//        mapFragment = null;
//        mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.mapView);
//
////        if ( mapFragment!=null )
//        mapFragment.getMapAsync(this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);

        Call<WeatherResponse> call = weatherApi.getWeatherData("seoul", API_KEY, "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        Coordinate coord = weatherResponse.getCoord();
                        Weather weather = weatherResponse.getMain();
                        String cityName = weatherResponse.getName();
                        weatherResponse.getWeather().forEach( weatherDesc -> Log.i(weatherDesc.main, weatherDesc.description) );
//                        WeatherDesc weatherDesc = weatherResponse.getWeather();

                        // 여기서 Weather 객체의 데이터를 사용하면 됩니다.
//                        Log.d("Weather Data", "City: " + weather.getCityName());
//                        Log.d("Weather Data", "Temperature: " + weather.getTemperature());
//                        Log.d("Weather Data", "Humidity: " + weather.getHumidity());
                    }
                } else {
                    Log.e("API Error", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("Weather API", "Weather API 호출 실패 : " + t.getMessage());
            }
        });
    }

    public void onClickButtonBrowser(View view)
    {
        Intent browserIntent = new Intent(WeatherActivity.this, MainActivity.class);
        startActivity(browserIntent);
        //finish();
    }


//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));
//    }
}