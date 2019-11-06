package com.example.dmattd.shopdeviceonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.adapter.Sanpham1Adapter;
import com.example.dmattd.shopdeviceonline.model.CheckStatusUser;
import com.example.dmattd.shopdeviceonline.model.Sanpham;
import com.example.dmattd.shopdeviceonline.util.CheckConnection;
import com.example.dmattd.shopdeviceonline.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Sanpham1Activity extends AppCompatActivity {
    Toolbar toolbarsp;
    ListView listViewsp1;
    Sanpham1Adapter sanpham1Adapter;
    ArrayList<Sanpham> mangsp1;

    int idloaisp1 = CheckStatusUser.idLoaiSanPham;
    String tenloaisp1 = CheckStatusUser.tenLoaiSanPham;
    int page = 1;

    View footerview;
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham1);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetThongtinLoaisp();
            ActionToolBar();
            Getdata(page);
            LoadMoreData();
        }else {
            CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang, menu);
        return true;
    }
    //bat su kien menugio hang

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMoreData() {
        listViewsp1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChitietSanpham.class);
                intent.putExtra("thongtinsp", mangsp1.get(i));
                startActivity(intent);

            }
        });
        listViewsp1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem == totalItem && totalItem != 0 && isLoading  == false && limitData == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void Getdata(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdansp1+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tensp1 = "";
                int giasp1 = 0;
                String hinhanhsp1 = "";
                String motasp1 = "";
                int id_loaisp = 0;

                if(response != null && response.length() != 2){
                    listViewsp1.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensp1 = jsonObject.getString("tensp");
                            Log.d("Gtriloaisp", "onResponse: " + tensp1);
                            giasp1 = jsonObject.getInt("giasp");
                            hinhanhsp1 = jsonObject.getString("hinhanhsp");
                            motasp1 = jsonObject.getString("mieutasp");
                            id_loaisp = jsonObject.getInt("id_loaisp");

                            mangsp1.add(new Sanpham(id, tensp1, giasp1, hinhanhsp1, motasp1, id_loaisp));


                            sanpham1Adapter.notifyDataSetChanged();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("mangsp1", "onResponse: mangsp " + mangsp1.size());
                }else {
                    limitData = true;
                    listViewsp1.removeFooterView(footerview);
                    CheckConnection.ShowToast_short(getApplicationContext(), "Đã hết dữ liệu");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                 param.put("id_loaisp", String.valueOf(idloaisp1));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void ActionToolBar() {
        setSupportActionBar(toolbarsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // get id và tên loại sp
    private void GetThongtinLoaisp() {
        idloaisp1 = getIntent().getIntExtra("id_loaisp", -1);
        tenloaisp1 = getIntent().getStringExtra("ten_loaisp");

    }

    private void Anhxa() {
        toolbarsp = findViewById(R.id.toolbarsp1);
        toolbarsp.setTitle(CheckStatusUser.tenLoaiSanPham);
        listViewsp1 = findViewById(R.id.listviewsp1);
        mangsp1 = new ArrayList<>();
        sanpham1Adapter = new Sanpham1Adapter(getApplicationContext(), mangsp1);


        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = (View) inflater.inflate(R.layout.progressbar, null);
        listViewsp1.setAdapter(sanpham1Adapter);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listViewsp1.addFooterView(footerview);
                    break;
                case 1:
                    Getdata(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
