package com.example.mobdev.Classes;
//Name - Richard Sharpe Marshall
//Matric Number - S1829121
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class set_items {


    private String title;
    private String Description;
    private String Link;
    private Double Lat;
    private Double Lon;
    private Double Lat1;
    private Double Lon1;
    private String PDate;
    private Date sDate;
    private Date eDate;
    private String fDate;
    private ArrayList<set_items> i_list;
    private LatLng ltlng;

    public set_items() {
        this.Lat = 0.0;
        this.Lon = 0.0;
        this.Lat1 = 0.0;
        this.Lon1 = 0.0;
        this.title = "";
        this.Description = "";
        this.Link = "";
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        this.PDate = "";
        this.sDate = today.getTime();
        this.eDate = today.getTime();
        this.i_list = new ArrayList<>();
        this.fDate = "";
        this.ltlng = new LatLng(0,0);
    }
/*
    public set_items(String title, String description, String link, String pDate, String fDate, Double lat, Double lon, Date sDate, Date eDate,  ArrayList<set_items> FeedItems) {

        this.Lat = lat;
        this.Lon = lon;
        this.title = title;
        this.Description = description;
        this.Link = link;
        this.PDate = pDate;
        this.sDate = sDate;
        this.eDate = eDate;
        this.i_list = FeedItems;
        this.fDate = fDate;

    }
*/
    public String getTitle(){return title;}
    public String getDescription(){return Description;}
    public String getLink(){return Link;}
    public Double getLat(){return Lat;}
    public String getPublishDate(){return PDate;}
    public Date getStartDate(){return sDate;}
    public Date getEndDate(){return eDate;}
    public ArrayList getItemList(){return i_list;}
    public double getLon(){return Lon;}
    public String getfDate(){return fDate;}

    public double getLon1(){return Lon1;}
    public double getLat1(){return Lat1;}
    //Setters
    public void setTitle(String Title){title = Title;}
    public void setDescription(String desc){Description = desc;}
    public void setLat(Double lat){Lat=lat;}
    public void setLon(Double lon){Lon=lon;}
    public void setPublishDate(String dat){PDate = dat;}
    public void setLink(String link){Link = link;}
    public void setfDate(String dat){fDate = dat;}

    public void setLat1(Double lat){Lat1 = lat;}
    public void setLon1(Double lon){Lon1 = lon;}

    public LatLng getCoords(){
        return new LatLng(Lat,Lon);
    }

    public void setLtLng(LatLng latlong){
        ltlng = latlong;

    }

    public LatLng getCoordinates(){
        return new LatLng(Lat, Lon);
    }

}