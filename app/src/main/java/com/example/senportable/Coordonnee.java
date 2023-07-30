package com.example.senportable;

public class Coordonnee
{
    private Double latitude;
    private Double longitude;
    private String pays;
    private String ville;
    private String address;
    private String date;

    public Coordonnee(Double latitude, Double longitude, String pays, String ville, String address, String date)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.pays = pays;
        this.ville = ville;
        this.address = address;
        this.date = date;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public String getPays()
    {
        return pays;
    }

    public void setPays(String pays)
    {
        this.pays = pays;
    }

    public String getVille()
    {
        return ville;
    }

    public void setVille(String ville)
    {
        this.ville = ville;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "Coordonnee{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", pays='" + pays + '\'' +
                ", ville='" + ville + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
