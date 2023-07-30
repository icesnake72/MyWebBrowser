package com.example.mywebbrowser;

public class GeoCode {
    private Local_names local_names;

    private String country;

    private String name;

    private String lon;

    private String lat;

    public Local_names getLocal_names ()
    {
        return local_names;
    }

    public void setLocal_names (Local_names local_names)
    {
        this.local_names = local_names;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [local_names = "+local_names+", country = "+country+", name = "+name+", lon = "+lon+", lat = "+lat+"]";
    }
}
