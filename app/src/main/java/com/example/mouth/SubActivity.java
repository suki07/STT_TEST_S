package com.example.mouth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import android.os.Bundle;



public class SubActivity extends AppCompatActivity {
    //생성자가 끝나면 자동으로 onCreate 실행
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
    }
}