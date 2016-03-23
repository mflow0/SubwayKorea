package com.example.hongssang.subwaykorea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {
    ArrayList<DeajeonStation> deajeonSangheng = new ArrayList(22);
    ArrayList<DeajeonStation> deajeonHaheng = new ArrayList(22);
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        txtView = (TextView)findViewById(R.id.textview);

        for(int i = 0; i < 22; i++){
            txtView.setText(Integer.toString(i));
            deajeonSangheng.add(i, new DeajeonStation(101 + i, 0));
            deajeonHaheng.add(i, new DeajeonStation(101 + i, 1));
        }//대전 지하철의 상하행 시간표 정보를 모두 저장함.(네트워크 접속)

        Intent intent = new Intent(this, WholeViewActivity.class);
        //다른 액티비티에 정보를 넘겨주기 위한 인텐트
        intent.putExtra("deajeonSangheng", deajeonSangheng);
        intent.putExtra("deajeonHaheng", deajeonHaheng);
        //데이터에 이름을 붙여 넘겨줄 준비를 함
        startActivity(intent);
        finish();
    }
}
