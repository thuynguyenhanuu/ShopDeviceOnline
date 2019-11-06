package com.example.dmattd.shopdeviceonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.adapter.LoaiSanPhamAdapter;
import com.example.dmattd.shopdeviceonline.adapter.Sanpham1Adapter;
import com.example.dmattd.shopdeviceonline.model.CheckStatusUser;
import com.example.dmattd.shopdeviceonline.model.LoaiSanPham;
import com.example.dmattd.shopdeviceonline.model.Loaisp;
import com.example.dmattd.shopdeviceonline.model.Sanpham;
import com.example.dmattd.shopdeviceonline.util.CheckConnection;
import com.example.dmattd.shopdeviceonline.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoaiSanPhamAcc extends AppCompatActivity {

    Toolbar toolbar;
    GridView gridView;
    ArrayList<LoaiSanPham> mangloaisanpham;
    LoaiSanPhamAdapter loaiSanPhamAdapter;

    ArrayList<Sanpham> mangsp1;
    Sanpham1Adapter sanpham1Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_san_pham_acc);
        Anhxa();
        GetDuLieuLoaisp();
        GetTungLoaiSp();
        ActionToolBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang, menu);
        return true;
    }

    //bat su kien menugio hang

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }



    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarLoaisp);
        gridView = findViewById(R.id.gridviewLoaiSanPham);
        mangloaisanpham = new ArrayList<>();
        loaiSanPhamAdapter = new LoaiSanPhamAdapter(mangloaisanpham, getApplicationContext());
        gridView.setAdapter(loaiSanPhamAdapter);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void GetDuLieuLoaisp() {
        // thuc hien cac phuong thuc gui len server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    Log.d("getdulieu", "onResponse: " + response);
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            // lay du lieu ra
                            int id = jsonObject.getInt("id");
                            Log.d("getdulieu", "onResponse: " + id);

                            String tenloaisp = jsonObject.getString("tenloaisp");
                            Log.d("getdulieu", "onResponse: " + tenloaisp);

                            String hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            Log.d("getdulieu", "onResponse: " + hinhanhloaisp);

                            mangloaisanpham.add(new LoaiSanPham(id, tenloaisp, hinhanhloaisp));
                            loaiSanPhamAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetTungLoaiSp(){
        final int idloaisanpham;
        String tenloaisanpham;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckStatusUser.idLoaiSanPham = (i+1);
                CheckStatusUser.tenLoaiSanPham = mangloaisanpham.get(i).getTenLoaiSanPham();

               // CheckConnection.ShowToast_short(getApplicationContext(), "abc"+CheckStatusUser.tenLoaiSanPham);

                Intent intent = new Intent(getApplicationContext(), Sanpham1Activity.class);
                intent.putExtra("id_loaisp",CheckStatusUser.idLoaiSanPham );
                intent.putExtra("ten_loaisp",CheckStatusUser.tenLoaiSanPham );

                startActivity(intent);

            }
        });


    }
}

