package com.example.hongssang.subwaykorea;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class WholeViewActivity extends AppCompatActivity implements View.OnClickListener{
    TextView txtView;
    //디버깅용 텍스트뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholeview);

        String firstSeenCity;
        txtView = (TextView) findViewById(R.id.textView);

        SharedPreferences setting;
        setting = PreferenceManager.getDefaultSharedPreferences(this);

        Set<String> a = setting.getStringSet("seoulLineBookmark", null);
        firstSeenCity = setting.getString("firstSeenCity", "1");

        replaceFragment(getFragment(firstSeenCity));
        //세팅값을 받아오기 위한 코드, set은 중복된 개체가 존재하지 않는 집합임, 세팅값들을 저장
        if (a != null) {
            txtView.setText(a.toString());
        }
        //세팅값 디버깅
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.setting:
                //클릭된 개체가 세팅 아이콘일 때
                Log.v("Tag", "setting_clicked");
                Intent forSettingIntent = new Intent(this ,SettingsActivity.class);
                startActivity(forSettingIntent);
                //세팅 액티비티 실행
                break;
            case R.id.button_seoul:
                //서울일 때
                Log.v("Tag", "seoul_clicked");
                replaceFragment(new SeoulFragment());
                //지하철의 위치를 표시하는 레이아웃을 SeoulFragment로 바꿈
                break;
            case R.id.button_deajeon:
                //대전일 때
                Log.v("Tag", "deajeon_clicked");
                replaceFragment(new DeajeonFragment());
                break;
            default:
                break;
        }
    }

    private Fragment getFragment(String fragNumber){
        switch(fragNumber){
            case "1":
                return new SeoulFragment();
            case "2":
                return new DeajeonFragment();
            default:
                return new SeoulFragment();
        }
    }

    /**
     * fragment를 바꿔주는 함수
     * @param frag 바꿀 fragment
     */
    private void replaceFragment(Fragment frag){
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.layout_subway, frag);
        // Commit the transaction
        transaction.commit();
    }
}
