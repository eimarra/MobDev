package com.example.mobdev.Classes;
//Name - Richard Sharpe Marshall
//Matric Number - S1829121
import android.util.Log;

import com.example.mobdev.MainActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ParseFeed extends MainActivity {

    public enum ParserScope{
        Channel,
        Item,
        Coordinates
    }

    private set_items TraffItem = new set_items();
    private setItemChannel ItemChannel = new setItemChannel();
    //private String url = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String url = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private ParserScope scope = ParserScope.Channel;

    public ArrayList<set_items> fetchXml() throws XmlPullParserException {

        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                try{
                    URL url1 = new URL(url);
                    HttpURLConnection connect = (HttpURLConnection)url1.openConnection();
                    connect.setReadTimeout(20000);
                    connect.setConnectTimeout(25000);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);
                    connect.connect();

                    InputStream stream = connect.getInputStream();

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    xpp.setInput(stream,null);

                    try{
                        int event = xpp.getEventType();
                        while (event != XmlPullParser.END_DOCUMENT){
                            switch (event) {
                                case XmlPullParser.START_TAG:
                                    switch (xpp.getName().toLowerCase()){
                                        case "georss:point":
                                            String latString = xpp.nextText();

                                            if(latString.isEmpty()){
                                                Log.i("Geo", "empty");

                                            }else{
                                                String[] Coords = latString.split(" ");

                                                try{
                                                    Double lat = Double.parseDouble(Coords[0]);
                                                    Double lon = Double.parseDouble(Coords[1]);

                                                    //TraffItem.setPublishDate(latString);

                                                    TraffItem.setLat1(lat);
                                                    TraffItem.setLon1(lon);
                                                }catch (Exception e){

                                                }
                                            }
                                            break;
                                        case "channel":
                                            // Do nothing
                                            scope = ParserScope.Channel;
                                            break;
                                        case "item":
                                            // create new instance of an item class
                                            scope = ParserScope.Item;
                                            // (make an item class)
                                            break;
                                        case "title":
                                            String title = xpp.nextText();
                                            if(scope.equals(ParserScope.Channel)) {
                                                ItemChannel.setTitle(title);
                                            }
                                            else{
                                            TraffItem.setTitle(title);

                                            }
                                            break;
                                        case "description":
                                            String desc = xpp.nextText();

                                            if(scope.equals(ParserScope.Channel)) {
                                                ItemChannel.setDescription(desc);
                                            }
                                            else{
                                                TraffItem.setDescription(desc);
                                            }
                                            TraffItem.setDescription(desc);
                                            break;
                                        case "pubdate":
                                            String DatePublished = xpp.nextText();
                                            //TraffItem.setPublishDate(DatePublished);
                                            //try{
                                                //Date pdate = new SimpleDateFormat("E, dd MM YYY HH:mm:ss z").parse(DatePublished);

                                                //String fDate = pdate.toString();
                                                //String fDate = xpp.nextText();
                                                String subDate = DatePublished.substring(0,11);
                                                TraffItem.setPublishDate(subDate);
                                                TraffItem.setfDate(subDate);
                                           // } catch (ParseException e){

                                            //}
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case XmlPullParser.END_TAG:

                                    if(xpp.getName().toLowerCase().equalsIgnoreCase("item")){
                                        TraffItem.setDescription(TraffItem.getDescription().replaceAll("<br />", "\\\n"));
                                        ItemChannel.addItemList(TraffItem);

                                        TraffItem = new set_items();


                                    }

                                    break;
                                default:
                                    break;


                            }
                            event=xpp.next();
                        }
                    }
                    catch(Exception e){

                    }
                }
                catch(Exception e) {

                }
            }
        });
        thread.start();
        return ItemChannel.getChannelItems();
    }
}
