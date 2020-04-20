package com.example.mobdev.Classes;
//Name - Richard Sharpe Marshall
//Matric Number - S1829121
import java.util.ArrayList;
import java.util.Date;

public class setItemChannel extends ArrayList<setItemChannel> {

    private String Title;
    private String Description;
    private String Link;
    private String pubDate;
    private int Ttl;
    private ArrayList<set_items> ChannelItems;


    // Inital Constructor
    public setItemChannel() {
        this.Title = "";
        this.Description = "";
        this.Link = "";
        this.Ttl = 0;
        this.pubDate = "";
        this.ChannelItems = new ArrayList<set_items>();
    }

    //Setters
    public void setTitle(String title) {this.Title = title;}
    public void setDescription(String description) {this.Description = description;}
    public void setPubDate(String pubDate){this.pubDate = pubDate; }
    public void setLink(String link) {this.Link = link;}
    public void setChannelItems(ArrayList<set_items> channelItems) {this.ChannelItems = channelItems;}
    public void setTtl(int ttl) {Ttl = ttl;}

    //Getters
    public String getTitle() {return Title;}
    public String getPubDate(){return this.pubDate;}
    public String getDescription() {return Description;}
    public String getLink() {return Link;}
    public int getTtl() {return Ttl;}


    //public void addItemList(Item item){ChannelItems.add(item);}
    public void addItemList(set_items item){
        this.ChannelItems.add(item);
    }

    public ArrayList<set_items> getChannelItems(){
        return ChannelItems;
    }


}
