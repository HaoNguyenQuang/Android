package com.example.btl_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.btl_android.adapter.adapterTruyen;
import com.example.btl_android.adapter.adapterchuyenmuc;
import com.example.btl_android.adapter.adapterthongtin;
import com.example.btl_android.database.Database;
import com.example.btl_android.model.ChuyenMuc;
import com.example.btl_android.model.TaiKhoan;
import com.example.btl_android.model.Truyen;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    GridView gridView;
    ListView listView, listViewThongTin;
    DrawerLayout drawerLayout;

    String email;
    String tentaikhoan;

    ArrayList<Truyen> TruyenArraylist;

    adapterTruyen adapterTruyen;

    ArrayList<ChuyenMuc> chuyenMucArrayList;

    ArrayList<TaiKhoan> taiKhoanArrayList;

    Database datbasedoctruyen;

    adapterchuyenmuc adapterchuyenmuc1;
    adapterthongtin adapterthongtin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datbasedoctruyen = new Database(this);

        //nhan su lieu ow man dang nhap gui
        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq", 0);
        int idd = intentpq.getIntExtra("idd", 0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        AnhXa();
        ActionBar();
        ActionViewFlipper();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MainNoiDung.class);

                String tent = TruyenArraylist.get(position).getTenTruyen();
                String noidungt = TruyenArraylist.get(position).getNoiDung();
                intent.putExtra("tentruyen", tent);
                intent.putExtra("noidung", noidungt);
                startActivity(intent);
            }
        });

        //click item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0)
                {
                    if(i==2) {
                        //chuyển qua màn hình MainActivity
                        Intent intent = new Intent(MainActivity.this, MainDangBai.class);

                        //gửi dữ liệu qua Activity là MainActivity
                        intent.putExtra("phanq", i);
                        intent.putExtra("idd", idd);
                        intent.putExtra("email", email);
                        intent.putExtra("tentaikhoan", tentaikhoan);

                        startActivity(intent);

                    }else
                    {
                        Intent intent = new Intent(MainActivity.this, MainThongTin.class);
                        startActivity(intent);
                        /*Toast.makeText(MainActivity.this, "Ban khong co quyen dang bai", Toast.LENGTH_SHORT).show();
                        Log.e("Dang bai: ", "Ban khong co quyen");*/
                    }

                }
                else if(position == 1)
                {
                    if(i==2) {
                        //chuyển qua màn hình MainActivity
                        Intent intent = new Intent(MainActivity.this, MainSua.class);

                        //gửi dữ liệu qua Activity là MainActivity
                        intent.putExtra("phanq", i);
                        intent.putExtra("idd", idd);
                        intent.putExtra("email", email);
                        intent.putExtra("tentaikhoan", tentaikhoan);

                        startActivity(intent);

                    }else
                    {
                        finish();
                        /*Toast.makeText(MainActivity.this, "Ban khong co quyen sua truyen", Toast.LENGTH_SHORT).show();
                        Log.e("Sua Truyen: ", "Ban khong co quyen");*/
                    }
                }
                else if(position == 2)
                {
                    if(i==2) {
                        //chuyển qua màn hình MainActivity
                        Intent intent = new Intent(MainActivity.this, MainAdmin.class);

                        //gửi dữ liệu qua Activity là MainActivity
                        intent.putExtra("phanq", i);
                        intent.putExtra("idd", idd);
                        intent.putExtra("email", email);
                        intent.putExtra("tentaikhoan", tentaikhoan);

                        startActivity(intent);

                    }else
                    {
                        Toast.makeText(MainActivity.this, "Ban khong co quyen xoa truyen", Toast.LENGTH_SHORT).show();
                        Log.e("Xoa Truyen: ", "Ban khong co quyen");
                    }
                }
                else if(position == 3)
                {
                    Intent intent = new Intent(MainActivity.this, MainThongTin.class);
                    startActivity(intent);
                }
                else if(position == 4)
                {
                    finish();
                }
            }
        });
    }

    private void ActionBar()
    {
        //Hàm hỗ trợ toolbar
        setSupportActionBar(toolbar);
        //set nút cho actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //tạo icon cho toolbar
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //phương thức cho chạy slide với Viewfilipper
    private void ActionViewFlipper() {

        //mảng chứa tấm ảnh cho slide
        ArrayList<String> mangquangcao = new ArrayList<>();
        //add ảnh vào slide
        mangquangcao.add("https://zda.vn/wp-content/uploads/2022/03/truyen-tranh-ngon-tinh.jpg");
        mangquangcao.add("https://blog.hamtruyentranh.com/wp-content/uploads/2019/01/Y%C5%8Dkoso-Jitsuryoku-Shij%C5%8D-Shugi-no-Ky%C5%8Dshitsu-e-poster.jpg");
        mangquangcao.add("https://canhrau.com/wp-content/uploads/2022/01/truyen-tranh-ngon-tinh-thanh-xuan-vuon-truong-trung-quoc-hinh-5.png");
        mangquangcao.add("https://i.pinimg.com/736x/2a/3a/3d/2a3a3ddb8b6c2a518eb28f802add0c1f.jpg");

        //Thực hiện vòng lặp gán ảnh vào imageview,rồi từ imgview lên app
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            //sử dụng hàm thư viện piscasso để load ảnh lên imageView
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            //Phương thức chỉnh tấm hình vừa khung slide
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //Thêm ảnh từ imageview vào ViewFlipper
            viewFlipper.addView(imageView);
        }
        //Thiết lập tự động chạy cho ViewFlipper chạy trong 4s
        viewFlipper.setFlipInterval(4000);
        //run auto ViewFlipper
        viewFlipper.setAutoStart(true);

        //gọi amitation cho vào và ra
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        //gọi Animation vào viewFlipper
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void AnhXa()
    {
        //nhan su lieu ow man dang nhap gui
        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq", 0);
        int idd = intentpq.getIntExtra("idd", 0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        gridView = findViewById(R.id.listviewNew);
        listView = findViewById(R.id.listviewmanhinhchinh);
        listViewThongTin = findViewById(R.id.listviewthongtin);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);

        TruyenArraylist = new ArrayList<>();

        Cursor cursor1 = datbasedoctruyen.getData1();
        while(cursor1.moveToNext())
        {
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArraylist.add(new Truyen(id, tentruyen, noidung, anh, id_tk));

            adapterTruyen = new adapterTruyen(getApplicationContext(), TruyenArraylist);
            gridView.setAdapter(adapterTruyen);

            //

        }
        cursor1.moveToFirst();
        cursor1.close();

        //Thong tin
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan, email));

        adapterthongtin1 = new adapterthongtin(this, R.layout.navigation_thongtin, taiKhoanArrayList);
        listViewThongTin.setAdapter(adapterthongtin1);

        //Chuyen Muc
        if(i==2){
            chuyenMucArrayList = new ArrayList<>();
            chuyenMucArrayList.add(new ChuyenMuc("Đăng bài", R.drawable.ic_baseline_post_add_24));
            chuyenMucArrayList.add(new ChuyenMuc("Sửa truyện", R.drawable.ic_baseline_update_24));
            chuyenMucArrayList.add(new ChuyenMuc("Xóa truyện", R.drawable.ic_baseline_delete_24));
            chuyenMucArrayList.add(new ChuyenMuc("Thông tin", R.drawable.ic_baseline_face_24));
            chuyenMucArrayList.add(new ChuyenMuc("Đăng xuất", R.drawable.ic_baseline_login_24));

        }
        else{
            chuyenMucArrayList = new ArrayList<>();
            chuyenMucArrayList.add(new ChuyenMuc("Thông tin", R.drawable.ic_baseline_face_24));
            chuyenMucArrayList.add(new ChuyenMuc("Đăng xuất", R.drawable.ic_baseline_login_24));
        }
        /*chuyenMucArrayList = new ArrayList<>();
        chuyenMucArrayList.add(new ChuyenMuc("Đăng bài", R.drawable.ic_baseline_post_add_24));
        chuyenMucArrayList.add(new ChuyenMuc("Sửa truyện", R.drawable.ic_baseline_update_24));
        chuyenMucArrayList.add(new ChuyenMuc("Xóa truyện", R.drawable.ic_baseline_delete_24));
        chuyenMucArrayList.add(new ChuyenMuc("Thông tin", R.drawable.ic_baseline_face_24));
        chuyenMucArrayList.add(new ChuyenMuc("Đăng xuất", R.drawable.ic_baseline_login_24));*/

        adapterchuyenmuc1 = new adapterchuyenmuc(this, R.layout.chuyenmuc, chuyenMucArrayList);
        listView.setAdapter(adapterchuyenmuc1);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu1:
                Intent intent = new Intent(MainActivity.this, MainTimKiem.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}