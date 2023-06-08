package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl_android.database.Database;

public class MainDangNhap extends AppCompatActivity {

    //Tạo biến cho đăng nhập
    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnDangKy;

    //Để tạo đối tượng cho databasedoctruyen
    Database databasedoctruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_nhap);

        AnhXa();
        //đối tượng databasedoctruyen
        databasedoctruyen = new Database(this);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDangNhap.this, MainDangKy.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gán cho các biến là giá trị nhập vào từ editText
                String tentaikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                //Sử dụng con trỏ về để lấy dữ liệu, gọi tới getData() để lấy dữ liệu tất cả tk ở SQLite
                Cursor cursor = databasedoctruyen.getData();

                //tạo biến để kiểm tra tài khoản và mật khẩu
                boolean isMatch = false;

                //Thực hiện vòng lặp để lấy dữ liệu từ cursor vowus moveToNext() di chuyển tiếp
                while(cursor.moveToNext())
                {
                    //Lấy dữ liệu và gán vào biến, dữ liệu tài khoản ở ô 1 và mật khẩu ở ô 2, ô 0 là idtaikhoan, ô 3 là email, ô 4 là phân quyền
                    String datatentaikhoan = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);

                    //Nếu tài khoản và mật khẩu nhập vào từ bàn phím khớp với ở database
                    if(datatentaikhoan.equals(tentaikhoan) && datamatkhau.equals(matkhau))
                    {
                        //Lấy dữ liệu phan quyen và id
                        int phanquyen = cursor.getInt(4);
                        int idd = cursor.getInt(0);
                        String email = cursor.getString(3);
                        String tentk = cursor.getString(1);

                        //chuyển qua màn hình MainActivity
                        Intent intent = new Intent(MainDangNhap.this, MainActivity.class);

                        //gửi dữ liệu qua Activity là MainActivity
                        intent.putExtra("phanq", phanquyen);
                        intent.putExtra("idd", idd);
                        intent.putExtra("email", email);
                        intent.putExtra("tentaikhoan", tentk);

                        startActivity(intent);
                        isMatch = true; //tài khoản và mật khẩu khớp
                        break; // thoát khỏi vòng lặp khi tìm thấy kết quả khớp
                    }
                }
                if (!isMatch) { // nếu tài khoản và mật khẩu không khớp với dữ liệu trong cơ sở dữ liệu
                    Toast.makeText(MainDangNhap.this, "Tên tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
                //Thục hiện trả cursor về đầu
                cursor.moveToFirst();
                //đóng khi ko dùng
                cursor.close();
            }
        });

    }
    private void AnhXa()
    {
        edtMatKhau = findViewById(R.id.matkhau);
        edtTaiKhoan = findViewById(R.id.taikhoan);
        btnDangKy = findViewById(R.id.dangky);
        btnDangNhap = findViewById(R.id.dangnhap);
    }
}