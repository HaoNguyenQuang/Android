package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.btl_android.adapter.adapterTruyen;
import com.example.btl_android.adapter.adapterTruyenSearch;
import com.example.btl_android.database.Database;
import com.example.btl_android.model.Truyen;

import java.util.ArrayList;
import java.util.Locale;

public class MainTimKiem extends AppCompatActivity {

    ListView listView;

    EditText edt;

    ArrayList<Truyen> TruyenArrayList;

    ArrayList<Truyen> arrayList;

    adapterTruyenSearch adapterTruyenSearch;

    Database databasedoctruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tim_kiem);

        listView = findViewById(R.id.listviewTimKiem);
        edt = findViewById(R.id.timkiem);

        initList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainTimKiem.this, MainNoiDung.class);
                String tent = arrayList.get(position).getTenTruyen();
                String noidungt = arrayList.get(position).getNoiDung();
                intent.putExtra("tentruyen", tent);
                intent.putExtra("noidung", noidungt);
                startActivity(intent);
            }
        });

        //edittext search
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text)
    {
        //xoa du lieu mang
        arrayList.clear();

        ArrayList<Truyen> filteredList = new ArrayList<>();

        for(Truyen item : TruyenArrayList)
        {
            if(item.getTenTruyen().toLowerCase().contains(text.toLowerCase()))
            {
                //them item vao filteredlist
                filteredList.add(item);

                //them vao mang
                arrayList.add(item);
            }
        }
        adapterTruyenSearch.filterList(filteredList);
    }

    private void initList() {

        TruyenArrayList = new ArrayList<>();

        arrayList = new ArrayList<>();

        databasedoctruyen = new Database(this);

        Cursor cursor = databasedoctruyen.getData2();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String tentruyen = cursor.getString(1);
            String noidung = cursor.getString(2);
            String anh = cursor.getString(3);
            int id_tk = cursor.getInt(4);

            TruyenArrayList.add(new Truyen(id, tentruyen, noidung, anh, id_tk));

            arrayList.add(new Truyen(id, tentruyen, noidung, anh, id_tk));

            adapterTruyenSearch = new adapterTruyenSearch(getApplicationContext(), TruyenArrayList);

            listView.setAdapter(adapterTruyenSearch);
        }
        cursor.moveToFirst();
        cursor.close();
    }
}