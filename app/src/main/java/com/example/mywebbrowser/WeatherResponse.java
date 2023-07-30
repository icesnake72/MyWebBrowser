package com.example.mywebbrowser;

import java.util.List;

public class WeatherResponse {

    private Coordinate coord;

    private List<WeatherDesc> weather;

    private Weather main;
//    private WeatherDesc weather;

    private String name;

    public Coordinate getCoord()
    {
        return coord;
    }

    public Weather getMain() {
        return main;
    }

    public String getName()
    {
        return name;
    }

    public List<WeatherDesc> getWeather()
    {
        return weather;
    }
}
