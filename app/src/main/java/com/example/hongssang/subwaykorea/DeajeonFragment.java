package com.example.hongssang.subwaykorea;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DeajeonFragment extends Fragment implements OnClickListener {
    //Fragment 생명주기 : http://wawoops67.blogspot.kr/2013/02/android-fragment-lifecycle.html
    Intent intent;
    private ArrayList<DeajeonStation> sangheng;
    private ArrayList<DeajeonStation> haheng;
    private Animation anim;
    private ImageView[] sanghengStation = new ImageView[21];
    private ImageView[] sanghengArrow = new ImageView[21];
    DayTime dt;
    PopUp noteBalloon;
    boolean balloonEmitted;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onCreate()");
        super.onCreate(savedInstanceState);

        intent = getActivity().getIntent();
        sangheng = (ArrayList<DeajeonStation>) intent.getSerializableExtra("deajeonSangheng");
        haheng = (ArrayList<DeajeonStation>) intent.getSerializableExtra("deajeonHaheng");
        //intent로부터 DeajeonStation 객체들을 받아온다.

        dt = new DayTime();
        int time = dt.getTime();
        int hol = dt.getHoilyDay();
        //현재 시각 설정

        for (int i = 0; i < 22; i++) {
            sangheng.get(i).setTime(hol, time);
            haheng.get(i).setTime(hol, time);
        }//각 역에 현재 시각 설정해줌

        StationComparator comp = new StationComparator();
        //역 비교자 생성
        for (int i = 1; i < 22; i++) {
            comp.setCurrentStation(haheng.get(i));
            comp.setPreviousStation(haheng.get(i - 1));
            comp.setStationState();
        }//하행 역 비교, 각 역에 현재 상태 입력
        for (int i = 20; i >= 0; i--) {
            comp.setCurrentStation(sangheng.get(i));
            comp.setPreviousStation(sangheng.get(i + 1));
            comp.setStationState();
        }//상행 역 비교, 각 역에 현재 상태 입력

        //팝업창 설정을 위한 임시 코드
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        noteBalloon = (PopUp) layoutInflater.inflate(R.layout.popup_subwayinfo, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(500, 300);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        noteBalloon.setLayoutParams(layoutParams);
        balloonEmitted = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deajeon, container, false);

        for (int i = 0; i < 21; i++) {
            Resources res = getResources();
            sanghengStation[i] = (ImageView) view.findViewById(res.getIdentifier("sanghengStation" + (i + 1), "id", getActivity().getPackageName()));
            sanghengArrow[i] = (ImageView) view.findViewById(res.getIdentifier("sanghengArrow" + (i + 1), "id", getActivity().getPackageName()));
        }//각 변수에 알맞는 imageView 설정

        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        //상태를 나타내는 아이콘의 애니메이션 세팅(깜박임)

        for (int i = 0; i < 21; i++) {
            setImage(sanghengArrow[i], sanghengStation[i], sangheng.get(21 - i).getStationState());
        }

        return view;
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

    @Override
    public void onClick(View v) {
        Log.v("Tag", "clicked");
        if(!balloonEmitted) {
            switch (v.getId()) {
                case R.id.sanghengStation1:
                    showExtraState(sanghengStation[1], sangheng.get(1));
                    break;
                case R.id.sanghengStation2:
                    showExtraState(sanghengStation[2], sangheng.get(2));
                    break;
                case R.id.sanghengStation3:
                    showExtraState(sanghengStation[3], sangheng.get(3));
                    break;
                case R.id.sanghengStation4:
                    showExtraState(sanghengStation[4], sangheng.get(4));
                    break;
                case R.id.sanghengStation5:
                    showExtraState(sanghengStation[5], sangheng.get(5));
                    break;
                case R.id.sanghengStation6:
                    showExtraState(sanghengStation[6], sangheng.get(6));
                    break;
                case R.id.sanghengStation7:
                    showExtraState(sanghengStation[7], sangheng.get(7));
                    break;
                case R.id.sanghengStation8:
                    showExtraState(sanghengStation[8], sangheng.get(8));
                    break;
                case R.id.sanghengStation9:
                    showExtraState(sanghengStation[9], sangheng.get(9));
                    break;
                case R.id.sanghengStation10:
                    showExtraState(sanghengStation[10], sangheng.get(10));
                    break;
                case R.id.sanghengStation11:
                    showExtraState(sanghengStation[11], sangheng.get(11));
                    break;
                case R.id.sanghengStation12:
                    showExtraState(sanghengStation[12], sangheng.get(12));
                    break;
                case R.id.sanghengStation13:
                    showExtraState(sanghengStation[13], sangheng.get(13));
                    break;
                case R.id.sanghengStation14:
                    showExtraState(sanghengStation[14], sangheng.get(14));
                    break;
                case R.id.sanghengStation15:
                    showExtraState(sanghengStation[15], sangheng.get(15));
                    break;
                case R.id.sanghengStation16:
                    showExtraState(sanghengStation[16], sangheng.get(16));
                    break;
                case R.id.sanghengStation17:
                    showExtraState(sanghengStation[17], sangheng.get(17));
                    break;
                case R.id.sanghengStation18:
                    showExtraState(sanghengStation[18], sangheng.get(18));
                    break;
                case R.id.sanghengStation19:
                    showExtraState(sanghengStation[19], sangheng.get(19));
                    break;
                case R.id.sanghengStation20:
                    showExtraState(sanghengStation[20], sangheng.get(20));
                    break;
                case R.id.sanghengStation21:
                    showExtraState(sanghengStation[21], sangheng.get(21));
                    break;
                case R.id.sanghengArrow1:
                    showExtraState(sanghengArrow[1], sangheng.get(1));
                    break;
                case R.id.sanghengArrow2:
                    showExtraState(sanghengArrow[2], sangheng.get(2));
                    break;
                case R.id.sanghengArrow3:
                    showExtraState(sanghengArrow[3], sangheng.get(3));
                    break;
                case R.id.sanghengArrow4:
                    showExtraState(sanghengArrow[4], sangheng.get(4));
                    break;
                case R.id.sanghengArrow5:
                    showExtraState(sanghengArrow[5], sangheng.get(5));
                    break;
                case R.id.sanghengArrow6:
                    showExtraState(sanghengArrow[6], sangheng.get(6));
                    break;
                case R.id.sanghengArrow7:
                    showExtraState(sanghengArrow[7], sangheng.get(7));
                    break;
                case R.id.sanghengArrow8:
                    showExtraState(sanghengArrow[8], sangheng.get(8));
                    break;
                case R.id.sanghengArrow9:
                    showExtraState(sanghengArrow[9], sangheng.get(9));
                    break;
                case R.id.sanghengArrow10:
                    showExtraState(sanghengArrow[10], sangheng.get(10));
                    break;
                case R.id.sanghengArrow11:
                    showExtraState(sanghengArrow[11], sangheng.get(11));
                    break;
                case R.id.sanghengArrow12:
                    showExtraState(sanghengArrow[12], sangheng.get(12));
                    break;
                case R.id.sanghengArrow13:
                    showExtraState(sanghengArrow[13], sangheng.get(13));
                    break;
                case R.id.sanghengArrow14:
                    showExtraState(sanghengArrow[14], sangheng.get(1));
                    break;
                case R.id.sanghengArrow15:
                    showExtraState(sanghengArrow[15], sangheng.get(1));
                    break;
                case R.id.sanghengArrow16:
                    showExtraState(sanghengArrow[16], sangheng.get(1));
                    break;
                case R.id.sanghengArrow17:
                    showExtraState(sanghengArrow[17], sangheng.get(1));
                    break;
                case R.id.sanghengArrow18:
                    showExtraState(sanghengArrow[18], sangheng.get(1));
                    break;
                case R.id.sanghengArrow19:
                    showExtraState(sanghengArrow[19], sangheng.get(1));
                    break;
                case R.id.sanghengArrow20:
                    showExtraState(sanghengArrow[20], sangheng.get(1));
                    break;
                case R.id.sanghengArrow21:
                    showExtraState(sanghengArrow[21], sangheng.get(1));
                    break;
            }
        }
        else{
            noteBalloon.setVisibility(View.GONE);
            balloonEmitted = false;
        }
    }
    public void showExtraState(ImageView view, DeajeonStation station){

        balloonEmitted = true;
        noteBalloon.setVisibility(View.VISIBLE);
        noteBalloon.setX(view.getX());
        noteBalloon.setY(view.getY());
        noteBalloon.setText(station.getExtraState());

        FrameLayout thisFrame = (FrameLayout) this.getActivity().findViewById(R.id.deajeoninnerlayout);
        thisFrame.addView(noteBalloon);
    }
}
