package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainThongTin extends AppCompatActivity {

    TextView txtThongtinApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thong_tin);

        txtThongtinApp = findViewById(R.id.textviewthongtin);

        String thongtin = "Ứng dụng được phát triển bởi Nhóm 10\n"+
                "Nguyễn Quang Hào\n"+
                "Nguyễn Tấn Lực\n"+
                "Mai Thị Phượng";
        txtThongtinApp.setText(thongtin);

    }
}