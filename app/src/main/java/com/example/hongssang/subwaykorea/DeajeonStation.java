package com.example.hongssang.subwaykorea;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by HongSSang on 2016-01-13.
 */
public class DeajeonStation extends Station implements Serializable {
    private int holiday, hour, minute, second, direction;
    //stationID(101(판암)상~122(반석)하), holiday(0:평,1:휴), direction(0:상,1:하)
    private final String key = "YdE7EgSS7%2Fa1ERMqXljn5AUN0Q%2Fb1Te6QBbKSOao4mjWOL8VFW71yQc9MN95QjB%2BmN1C0t2neA7BMNxkaPL%2FdQ%3D%3D";
    private LinkedList<TimetableSrc>[] timeTable; //시간표 정보가 들어갈 연결구조, 배열번호는 시각을 뜻한다.

    public DeajeonStation(int aStationID, int aDirection){
        //aTime(HHmmSS 의 int형 숫자)
        timeTable = new LinkedList[25];
        stationID = aStationID;
        direction = aDirection;
        stationState = 3;
        extraState = "";

        for(int i = 0; i < 25; i++){
            timeTable[i] = new LinkedList<TimetableSrc>();
        }

        MyThread tread = new MyThread();
        tread.start();  //네트워크에 접속하려면 tread를 통해서만 가능함
                        //네트워크 접속시간은 얼마나 될 지 모르기에, 비동기적인 동작이 더 좋다.
                        //물론 우리는 동기적으로 실행한다.
        try{
            tread.join();
        }catch(Exception e){
            Log.e("E:DeajeonStation,Tread", e.toString());
        }
    }

    public void setTime(int aHoliday, int aTime){
        holiday = aHoliday;
        hour = aTime / 10000;
        minute = (aTime / 100) % 100;
        second = aTime % 100;
        //현재시간 parsing
    }

    @Override
    public int getNextTime(){
        if(0 <= hour || hour <= 24) {
            Iterator<TimetableSrc> it = timeTable[hour].iterator();
            while(it.hasNext()){
                TimetableSrc tempSrc = it.next();
                if(minute < tempSrc.getMinute()){
                    extraState = tempSrc.getExtra();    //TimetableSrc 객체에 extra 정보가 들어있는경우 따로 저장
                    return (tempSrc.getMinute() - minute - 1) * 100 + (60 - second);
                }
            }
            return 9999;
        }
        else {
            return 9999;
        }
    }

    @Override
    public String getExtraState(){
        return extraState;
    }

    private void getXmlData(){
        String queryUrl = "http://www.djet.co.kr/OpenAPI/service/timeTableSVC/getTimeTable?stNum="+stationID+"&drctType="+direction+"&dayType="+holiday+"&ServiceKey="+key;

        try {
            URL url = new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기

            String tag;
            String tempTime = "";

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기

                        if (tag.equals("item")) ;//태그이름이 item일경우 스킵
                        else if (tag.equals("tmList")) {     //태그이름이 tmList일 경우 분 도착정보이다, 임시 저장해둔다
                            xpp.next();
                            tempTime = xpp.getText();
                        }
                        else if (tag.equals("tmZone")) {    //tmZone일 경우 모든 정보가 모였으므로 정보 가공을 시작
                            xpp.next();
                            int time = Integer.parseInt(xpp.getText()); //tmZone은 시 도착정보이다. timeTable 배열의 번호가 된다.
                            StringTokenizer tokenizer = new StringTokenizer(tempTime , " ");//임시로 저장해두었던 분 도착정보 토큰화
                            for(int j = 0; tokenizer.hasMoreTokens(); j++) {
                                String token = tokenizer.nextToken();
                                String tempExtra = "";
                                int tempMinute = Integer.parseInt(token.substring(0, 2));   //분 도착정보중 분 정보
                                if(token.length() > 2){
                                    tempExtra = token.substring(2, token.length());         //분 도착정보와 함께 종점 정보가 들어있는경우
                                }
                                timeTable[time].addLast(new TimetableSrc(tempMinute, tempExtra));
                                if(j == 0){
                                    timeTable[time -1].addLast(new TimetableSrc(tempMinute+60, tempExtra));
                                }
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("E:DeajeonStation,parse", e.toString());
        }
    }

    private class MyThread extends Thread{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            getXmlData();
         }
    }

    private class TimetableSrc implements Serializable{
        private int minute;
        private String extra;

        public TimetableSrc(int aMinute, String aExtra){
            minute = aMinute;
            extra = aExtra;
        }

        public int getMinute(){
            return minute;
        }

        public String getExtra(){
            return extra;
        }
    }
}
