package com.example.mywebbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {    // implements OnMapReadyCallback

//    protected SupportMapFragment mapFragment;

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String GEO_CODE_URL = "http://api.openweathermap.org/geo/1.0/";
    private static final String API_KEY = "555e77f38f5b28cf6481409e56f93ad4";
    private static final String METRIC = "metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();
        String lat = intent.getStringExtra("lat");
        String lon = intent.getStringExtra("lon");
        String city = intent.getStringExtra("city");

        TextView textView = this.findViewById(R.id.text_view_city2);
        textView.setText( city );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);

        Call<WeatherResponse> call = weatherApi.getWeatherData(lat, lon, API_KEY, METRIC);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        Weather weather = weatherResponse.getMain();

                        TextView tempView = findViewById(R.id.text_view_temp);
                        tempView.setText( String.format("섭씨 : %4.1f 도", weather.temp) );

                        TextView weatherView = findViewById(R.id.text_view_weather);
                        weatherView.setText(weatherResponse.getWeather().get(0).main);
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

    private void initGeoCode(String cityName, String country)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GEO_CODE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeoCodeApi geoApi = retrofit.create(GeoCodeApi.class);

        String cityCode = String.format("%s,%s", cityName, country.toUpperCase());
        Call<List<GeoCode>> call = geoApi.getGeoCodeData(cityCode, API_KEY);
        call.enqueue(new Callback<List<GeoCode>>() {
            @Override
            public void onResponse(Call<List<GeoCode>> call, Response<List<GeoCode>> response) {
                if (response.isSuccessful()) {
                    List<GeoCode> liGeoCodes = response.body();
                    if (liGeoCodes != null) {
                        liGeoCodes.forEach(System.out::println);
                    }
                }
                else
                {
                    Log.e("API Error", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<GeoCode>> call, Throwable t) {
                Log.e("Weather API", "Weather API 호출 실패 : " + t.getMessage());
            }
        });

    }


//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));
//    }
}