package com.example.hongssang.subwaykorea;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Test extends AppCompatActivity {
    TextView txtView;
    private LinkedList<Integer>[] timeTable; //시간표 정보가 들어갈 연결구조
    private String data;
    private Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        txtView = (TextView)findViewById(R.id.textView);
        ImageView[] station = new ImageView[21];
        ImageView[] arrow = new ImageView[21];

        DayTime dt = new DayTime();
        DeajeonStation[] sangheng = new DeajeonStation[22];
        DeajeonStation[] haheng = new DeajeonStation[22];
        StationComparator comp = new StationComparator();
        String forDebug = "";

        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);


        int time = dt.getTime();
        int hol = dt.getHoilyDay();
        for(int i = 0; i < 22; i++){
            sangheng[i] = new DeajeonStation(101 + i, 0);
            haheng[i] = new DeajeonStation(101 + i, 1);
        }
        for(int i = 1; i < 22; i++){
            comp.setCurrentStation(haheng[i]);
            comp.setPreviousStation(haheng[i-1]);
            comp.setStationState();
        }
        for(int i = 20; i >= 0; i--){
            comp.setCurrentStation(sangheng[i]);
            comp.setPreviousStation(sangheng[i+1]);
            comp.setStationState();
        }
        for(int i = 0; i < 21; i++){
            Resources res = getResources();
            station[i] = (ImageView) findViewById( res.getIdentifier("station" + (i+1), "id", getPackageName()) );
            arrow[i] = (ImageView) findViewById( res.getIdentifier("imageView" + (i+1), "id", getPackageName()) );
            setImage(arrow[i], station[i], sangheng[21 - i].getStationState());
        }
        for(int i = 0; i < 22; i++){
            forDebug += sangheng[i].getID() + " : " + sangheng[i].getStationState() + "\n";
        }
        txtView.setText(forDebug);
    }

    private void setImage(ImageView arrow, ImageView station, String state){
        switch(state){
            case "arriving":
                arrow.setVisibility(View.VISIBLE);
                station.setVisibility(View.INVISIBLE);
                arrow.setAnimation(anim);
                break;
            case "atStation":
                arrow.setVisibility(View.INVISIBLE);
                station.setVisibility(View.VISIBLE);
                station.setAnimation(anim);
                break;
            case "nothing":
                arrow.setVisibility(View.INVISIBLE);
                station.setVisibility(View.INVISIBLE);
                break;
            case "error":
                arrow.setVisibility(View.INVISIBLE);
                station.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
