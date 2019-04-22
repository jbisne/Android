package com.study.android.project03;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class PharmParser
{

    public final static String PHARM_URL = "http://openapi.seoul.go.kr:8088/707a45486e6a6973373541554f4365/xml/GeoInfoShelterWGS/1/694";
    public final static String KEY = "707a45486e6a6973373541554f4365";

    public PharmParser()
    {
        try
        {
            apiParserSearch();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     *
     * @throws Exception
     */
    public ArrayList<PharmDTO> apiParserSearch() throws Exception {
        URL url = new URL(getURLParam(null));

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        xpp.setInput(bis, "utf-8");

        String tag = null;
        int event_type = xpp.getEventType();

        ArrayList<PharmDTO> list = new ArrayList<PharmDTO>();

        String lng = null, lat= null, name= null;
        while (event_type != XmlPullParser.END_DOCUMENT) {
            if (event_type == XmlPullParser.START_TAG) {
                tag = xpp.getName();
            } else if (event_type == XmlPullParser.TEXT) {
                /**
                 * 대피소의 주소만 가져와본다.
                 */
                if(tag.equals("LNG"))
                {
                    lng = xpp.getText();
                    System.out.println(lng);
                }
                else if(tag.equals("LAT"))
                {
                    lat = xpp.getText();
                }
                else if(tag.equals("SHUNT_NAM"))
                {
                    name = xpp.getText();
                }
            }
            else if (event_type == XmlPullParser.END_TAG)
            {
                tag = xpp.getName();
                if (tag.equals("row"))
                {
                    PharmDTO entity = new PharmDTO();
                    entity.setLNG(Double.valueOf(lng));
                    entity.setLAT(Double.valueOf(lat));
                    entity.setName(name);

                    list.add(entity);
                }
            }
            event_type = xpp.next();
        }
        System.out.println(list.size());

        return list;
    }




    private String getURLParam(String search){
        String url = PHARM_URL+"?ServiceKey="+KEY;
        if(search != null){
            url = url+"&SHUNT_NAM"+search;
        }
        return url;
    }

    public static void main(String[] args)
    {
        new PharmParser();
    }

}
