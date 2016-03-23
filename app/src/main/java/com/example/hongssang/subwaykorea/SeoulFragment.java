package com.example.hongssang.subwaykorea;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SeoulFragment extends Fragment implements OnClickListener {
    ImageView imgView;
    boolean balloonEmitted;
    PopUp noteBalloon;

    public void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onCreate()");
        super.onCreate(savedInstanceState);
        balloonEmitted = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seoul, container, false);
        imgView = (ImageView) view.findViewById(R.id.seoulmap);
        imgView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

            if(balloonEmitted) {
                switch (v.getId()) {
                    case R.id.seoulmap:
                        Log.v("Tag", "clicked");
                        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        noteBalloon = (PopUp) layoutInflater.inflate(R.layout.popup_subwayinfo, null);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(500, 300);
                        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        noteBalloon.setLayoutParams(layoutParams);
                        noteBalloon.setVisibility(View.VISIBLE);

                        noteBalloon.setX(200);
                        noteBalloon.setY(200);
                        noteBalloon.setText("서울");

                        FrameLayout alo = (FrameLayout) this.getActivity().findViewById(R.id.innerlayout);
                        alo.addView(noteBalloon);
                        balloonEmitted = false;
                }
            }
        else {
                switch (v.getId()) {
                    case R.id.seoulmap:
                        noteBalloon.setVisibility(View.GONE);
                        balloonEmitted = true;
                }
            }
    }
}
