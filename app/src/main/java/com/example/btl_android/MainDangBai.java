package com.example.btl_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.btl_android.database.Database;
import com.example.btl_android.model.Truyen;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainDangBai extends AppCompatActivity {

    Button btnChonAnh;
    ImageView imgPhoto;
    EditText edtTenTruyen, edtNoiDung, edtAnh;
    Button btnDangBai;
    Database databasedoctruyen;
    String email;
    String tentaikhoan;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgPhoto.setImageBitmap(bitmap);
                edtAnh.setText(""+uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_bai);

        //nhan su lieu ow main admin
        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq", 0);
        int idd = intentpq.getIntExtra("idd", 0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        imgPhoto = findViewById(R.id.img_photo);
        btnChonAnh = findViewById(R.id.btnChonAnh);
        edtAnh = findViewById(R.id.dbimg);
        edtTenTruyen = findViewById(R.id.dbTenTruyen);
        edtNoiDung = findViewById(R.id.dbNoiDung);
        btnDangBai = findViewById(R.id.dbdangbai);

        databasedoctruyen = new Database(this);

        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 123);
            }
        });

        btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tentruyen = edtTenTruyen.getText().toString();
                String noidung = edtNoiDung.getText().toString();
                String img = edtAnh.getText().toString();

                Truyen truyen = CreateTruyen();

                if(tentruyen.equals("") || noidung.equals("") || img.equals("")){
                    Toast.makeText(MainDangBai.this, "yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    Log.e("Err: ", "Nhập đầy đủ thông tin");

                }
                else{
                    databasedoctruyen.AddTruyen(truyen);

                    //chuyển qua màn hình MainActivity
                    Intent intent = new Intent(MainDangBai.this, MainActivity.class);

                    //gửi dữ liệu qua Activity là MainActivity
                    intent.putExtra("phanq", i);
                    intent.putExtra("idd", idd);
                    intent.putExtra("email", email);
                    intent.putExtra("tentaikhoan", tentaikhoan);

                    startActivity(intent);
                    //finish();
                    //startActivity(intent);
                }
            }
        });
    }
    //Phuong thuc tao truyen
    private Truyen CreateTruyen(){
        String tentruyen = edtTenTruyen.getText().toString();
        String noidung = edtNoiDung.getText().toString();
        String img = edtAnh.getText().toString();

        Intent intent = getIntent();

        int id = intent.getIntExtra("Id", 0);

        Truyen truyen = new Truyen(tentruyen,noidung,img, id);

        return truyen;
    }
}