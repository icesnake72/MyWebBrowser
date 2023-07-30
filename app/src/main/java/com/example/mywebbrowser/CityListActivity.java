package com.example.mywebbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView cityListView;

    private static final String GEO_CODE_URL = "http://api.openweathermap.org/geo/1.0/";
    private static final String API_KEY = "555e77f38f5b28cf6481409e56f93ad4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        cityListView = findViewById(R.id.cityListView);

        List<String> cities = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        cityListView.setAdapter(adapter);
        cityListView.setOnItemClickListener(this);

        cities.add("서울");
        cities.add("안양");
        cities.add("성남");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String cityName = (String)adapterView.getItemAtPosition(i);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GEO_CODE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeoCodeApi geoApi = retrofit.create(GeoCodeApi.class);

        String cityCode = String.format("%s,KR", cityName);
        Call<List<GeoCode>> call = geoApi.getGeoCodeData(cityCode, API_KEY);

        call.enqueue(new Callback<List<GeoCode>>() {
            @Override
            public void onResponse(Call<List<GeoCode>> call, Response<List<GeoCode>> response) {
                if (response.isSuccessful()) {
                    List<GeoCode> liGeoCodes = response.body();
                    if (liGeoCodes != null) {

                        String lat = liGeoCodes.get(0).getLat();
                        String lon = liGeoCodes.get(0).getLon();

                        Intent intent = new Intent(CityListActivity.this, WeatherActivity.class);
                        intent.putExtra("lat", lat);
                        intent.putExtra("lon", lon);
                        intent.putExtra("city", liGeoCodes.get(0).getLocal_names().getKo());
                        startActivity(intent);
                    }
                }
                else
                {
                    Log.e("API Error", "Response not successful");
                    throw new RuntimeException("GeoCode API 호출 실패!!!");
                }
            }

            @Override
            public void onFailure(Call<List<GeoCode>> call, Throwable t) {
                Log.e("GeoCode API", "GeoCode API 호출 실패 : " + t.getMessage());
            }
        });
    }
}