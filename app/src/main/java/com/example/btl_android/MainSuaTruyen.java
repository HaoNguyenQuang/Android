package com.example.btl_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.btl_android.adapter.adapterTruyen;
import com.example.btl_android.database.Database;
import com.example.btl_android.model.Truyen;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainSuaTruyen extends AppCompatActivity {

    Button btnChonAnh;
    ImageView imgPhoto;
    EditText edtTenTruyen,edtNoiDung,edtAnh;
    Button btnUpdateTruyen;
    String email;
    String tentaikhoan;
    ArrayList<Truyen> TruyenArrayList;
    adapterTruyen adapterTruyen;
    Database databasedoctruyen;
    Truyen truyen;
    @SuppressLint("MissingInflatedId")

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
        setContentView(R.layout.activity_main_sua_truyen);
        //nhan su lieu ow main admin
        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq", 0);
        int idd = intentpq.getIntExtra("idd", 0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        imgPhoto = findViewById(R.id.img_photo);
        btnChonAnh = findViewById(R.id.btnChonAnh);
        edtTenTruyen = findViewById(R.id.edttentruyen);
        edtNoiDung = findViewById(R.id.edtnoidung);
        edtAnh = findViewById(R.id.edtimg);
        btnUpdateTruyen = findViewById(R.id.btnSuaTruyen);
        Database db= new Database(this);

        //lấy dữ liệu
        Intent intent = getIntent();
        String tentruyen = intent.getStringExtra("tentruyen");
        String noidung = intent.getStringExtra("noidung");
        String anh = intent.getStringExtra("anh");

        edtTenTruyen.setText(tentruyen);
        edtNoiDung.setText(noidung);
        edtAnh.setText(anh);
        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 123);
            }
        });
        btnUpdateTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                //sửa thông tin truyện
                Truyen truyen = new Truyen();

                //nguoi dung nhap vao
                truyen.setID(intent.getIntExtra("id", 0));
                truyen.setTenTruyen(edtTenTruyen.getText().toString());
                truyen.setNoiDung(edtNoiDung.getText().toString());
                truyen.setAnh(edtAnh.getText().toString());

                db.UpdateTruyen(truyen);
                db.close();

                //chuyển qua màn hình MainActivity
                intent = new Intent(MainSuaTruyen.this, MainSua.class);

                //gửi dữ liệu qua Activity là MainActivity
                intent.putExtra("phanq", i);
                intent.putExtra("idd", idd);
                intent.putExtra("email", email);
                intent.putExtra("tentaikhoan", tentaikhoan);

                startActivity(intent);
                /*finish();
                startActivity(intent1);*/
                Toast.makeText(MainSuaTruyen.this,"Sửa truyện thành công",Toast.LENGTH_SHORT).show();

            }
        });
    }
}