package com.example.btl_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.ContentUris;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.btl_android.adapter.adapterTruyen;
import com.example.btl_android.adapter.adapterTruyenSearch;
import com.example.btl_android.adapter.adapterchuyenmuc;
import com.example.btl_android.adapter.adapterthongtin;
import com.example.btl_android.database.Database;
import com.example.btl_android.model.ChuyenMuc;
import com.example.btl_android.model.TaiKhoan;
import com.example.btl_android.model.Truyen;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainSua extends AppCompatActivity {

    Toolbar toolbar;
    //ViewFlipper viewFlipper;
    NavigationView navigationView;
    GridView gridView;
    ListView listView, listViewThongTin;
    DrawerLayout drawerLayout;

    String email;
    String tentaikhoan;


    ArrayList<ChuyenMuc> chuyenMucArrayList;

    ArrayList<TaiKhoan> taiKhoanArrayList;


    adapterchuyenmuc adapterchuyenmuc1;
    adapterthongtin adapterthongtin1;


    ArrayList<Truyen> TruyenArrayList;

    adapterTruyenSearch adapterTruyenSearch;
    Database databasedoctruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        databasedoctruyen = new Database(this);
        //nhan su lieu ow main activity
        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq", 0);
        int idd = intentpq.getIntExtra("idd", 0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");
        AnhXa();
        ActionBar();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0)
                {
                    //chuyển qua màn hình MainActivity
                    Intent intent = new Intent(MainSua.this, MainActivity.class);

                    //gửi dữ liệu qua Activity là MainActivity
                    intent.putExtra("phanq", i);
                    intent.putExtra("idd", idd);
                    intent.putExtra("email", email);
                    intent.putExtra("tentaikhoan", tentaikhoan);

                    startActivity(intent);

                }
                else if(position == 1)
                {
                    Intent intent = new Intent(MainSua.this, MainDangBai.class);
                    //gửi dữ liệu qua Activity là MainActivity
                    intent.putExtra("phanq", i);
                    intent.putExtra("idd", idd);
                    intent.putExtra("email", email);
                    intent.putExtra("tentaikhoan", tentaikhoan);
                    startActivity(intent);
                }
                else if(position == 2)
                {
                    Intent intent = new Intent(MainSua.this, MainAdmin.class);
                    //gửi dữ liệu qua Activity là MainActivity
                    intent.putExtra("phanq", i);
                    intent.putExtra("idd", idd);
                    intent.putExtra("email", email);
                    intent.putExtra("tentaikhoan", tentaikhoan);
                    startActivity(intent);
                }
                else if(position == 3)
                {
                    Intent intent = new Intent(MainSua.this, MainThongTin.class);
                    startActivity(intent);
                }
                else if(position == 4)
                {
                    Intent intent = new Intent(MainSua.this, MainDangNhap.class);
                    startActivity(intent);
                }
            }
        });

        listView = findViewById(R.id.listviewAdmin);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent  intent = new Intent(MainSua.this,MainSuaTruyen.class);
                //gửi dữ liệu qua Activity là MainActivity
                intent.putExtra("phanq", i);
                intent.putExtra("idd", idd);
                intent.putExtra("email", email);
                intent.putExtra("tentaikhoan", tentaikhoan);

                startActivity(intent);
                String tent = TruyenArrayList.get(position).getTenTruyen();
                String noidungt = TruyenArrayList.get(position).getNoiDung();
                String anht = TruyenArrayList.get(position).getAnh();
                int idtr = TruyenArrayList.get(position).getID();
                intent.putExtra("id", idtr);
                intent.putExtra("tentruyen",tent);
                intent.putExtra("noidung",noidungt);
                intent.putExtra("anh",anht);
                startActivity(intent);
                return false;
            }
        });
        initList();

    }

    private void initList() {
        TruyenArrayList = new ArrayList<>();

        databasedoctruyen = new Database(this);
        Cursor cursor1 = databasedoctruyen.getData2();

        while(cursor1.moveToNext())
        {
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArrayList.add(new Truyen(id, tentruyen, noidung, anh,id_tk));

            adapterTruyenSearch = new adapterTruyenSearch(getApplicationContext(), TruyenArrayList);

            listView.setAdapter(adapterTruyenSearch);
            adapterTruyenSearch.notifyDataSetChanged();
        }
        cursor1.moveToFirst();
        cursor1.close();
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


    private void AnhXa()
    {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        //viewFlipper = findViewById(R.id.viewflipper);
        gridView = findViewById(R.id.listviewNew);
        listView = findViewById(R.id.listviewmanhinhchinh);
        listViewThongTin = findViewById(R.id.listviewthongtin);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);


        //Thong tin
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan, email));

        adapterthongtin1 = new adapterthongtin(this, R.layout.navigation_thongtin, taiKhoanArrayList);
        listViewThongTin.setAdapter(adapterthongtin1);
        adapterthongtin1.notifyDataSetChanged();

        //Chuyen Muc
        chuyenMucArrayList = new ArrayList<>();
        chuyenMucArrayList.add(new ChuyenMuc("Home", R.drawable.ic_baseline_home_24));
        chuyenMucArrayList.add(new ChuyenMuc("Đăng Bài", R.drawable.ic_baseline_post_add_24));
        chuyenMucArrayList.add(new ChuyenMuc("Xóa Truyện", R.drawable.ic_baseline_delete_24));
        chuyenMucArrayList.add(new ChuyenMuc("Thông tin", R.drawable.ic_baseline_face_24));
        chuyenMucArrayList.add(new ChuyenMuc("Đăng xuất", R.drawable.ic_baseline_login_24));

        adapterchuyenmuc1 = new adapterchuyenmuc(this, R.layout.chuyenmuc, chuyenMucArrayList);
        listView.setAdapter(adapterchuyenmuc1);
        adapterchuyenmuc1.notifyDataSetChanged();

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
                Intent intent = new Intent(MainSua.this, MainTimKiem.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}