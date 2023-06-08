package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_android.database.Database;
import com.example.btl_android.model.TaiKhoan;

import java.util.regex.Pattern;


public class MainDangKy extends AppCompatActivity {

    EditText edtDKTaiKhoan, edtDKMatKhau, edtDKEmail;
    Button btnDKDangKy;
    TextView txtDKDangNhap;

    Database databasedoctruyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_ky);

        databasedoctruyen = new Database(this);

        AnhXa();
        //Su kien cho btn Dang Ky
        btnDKDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = edtDKTaiKhoan.getText().toString();
                String matkhau = edtDKMatKhau.getText().toString();
                String email = edtDKEmail.getText().toString();

                TaiKhoan taiKhoan1 = CreateTaiKhoan();
                if(taikhoan.equals("") || matkhau.equals("")||email.equals(""))
                {
                    Toast.makeText(MainDangKy.this, "Chưa điền đủ thông tin", Toast.LENGTH_LONG).show();
                }

                // Kiểm tra định dạng email đúng
                else if (!isValidEmail(email)) {
                    Toast.makeText(MainDangKy.this, "Email không đúng định dạng", Toast.LENGTH_LONG).show();
                    return;
                }

                //Kiểm tra xem tài khoản đã tồn tại chưa
                else if (databasedoctruyen.isTaiKhoanExists(taikhoan)) {
                    Toast.makeText(MainDangKy.this, "Tài khoản đã tồn tại", Toast.LENGTH_LONG).show();
                    return;
                }
                //Thêm tài khoản vào nếu chưa tồn tại trong sqlite
                else
                {
                    databasedoctruyen.AddTaiKhoan(taiKhoan1);
                    Toast.makeText(MainDangKy.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                    // Đặt giá trị rỗng cho các EditText
                    edtDKTaiKhoan.setText("");
                    edtDKMatKhau.setText("");
                    edtDKEmail.setText("");
                }
            }
        });

        txtDKDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Phuong thuc tao tk
    private TaiKhoan CreateTaiKhoan()
    {
        String taikhoan = edtDKTaiKhoan.getText().toString();
        String matkhau = edtDKMatKhau.getText().toString();
        String email = edtDKEmail.getText().toString();
        int phanquyen = 1;

        TaiKhoan tk = new TaiKhoan(taikhoan, matkhau, email, phanquyen);
        return tk;
    }

    private void AnhXa()
    {
        edtDKTaiKhoan = findViewById(R.id.dkTaiKhoan);
        edtDKEmail = findViewById(R.id.dkEmail);
        edtDKMatKhau = findViewById(R.id.dkMatKhau);
        btnDKDangKy = findViewById(R.id.dkDangKy);
        txtDKDangNhap = findViewById(R.id.dkDangNhap);
    }

    // Phương thức kiểm tra email đúng định dạng
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}