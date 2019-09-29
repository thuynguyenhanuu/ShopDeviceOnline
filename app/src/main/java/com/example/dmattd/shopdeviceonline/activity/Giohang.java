package com.example.dmattd.shopdeviceonline.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.adapter.GiohangAdapter;
import com.example.dmattd.shopdeviceonline.model.CheckStatusUser;
import com.example.dmattd.shopdeviceonline.model.Dangnhap;
import com.example.dmattd.shopdeviceonline.util.CheckConnection;
import com.example.dmattd.shopdeviceonline.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Giohang extends AppCompatActivity {

    ListView listViewGiohang;
    TextView txtThongbao;
    static TextView txtTongtien;
    Button btnThangtoan, btnTieptucmua;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolbar();
        CheckData();
        EventUtils();
        CatchOnitemListview();
        EventButton();



    }

    private void EventButton() {
        btnTieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnThangtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.manggiohang.size() > 0){
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("1")){
                                MainActivity.manggiohang.clear();
                                Log.d("response111", "" + response);
                                CheckConnection.ShowToast_short(getApplicationContext(), "Bạn đã thêm dữ liệu cho giỏ hàng thành công!");

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                CheckConnection.ShowToast_short(getApplicationContext(), "Mời bạn tiếp tục mua hàng!");
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            JSONArray jsonArray = new JSONArray();
                            for(int i = 0; i < MainActivity.manggiohang.size(); i++){
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("idkhachhang", CheckStatusUser.idNgdung);
                                    jsonObject.put("idsanpham", MainActivity.manggiohang.get(i).getIdsp());
                                    jsonObject.put("tensanpham", MainActivity.manggiohang.get(i).getTensp());
                                    jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiasp());
                                    jsonObject.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoluongsp());
//
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);

                            }
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("json", jsonArray.toString());
                            return hashMap;

                        }
                    };
                    requestQueue.add(stringRequest);

                }else {
                    CheckConnection.ShowToast_short(getApplicationContext(), "Giỏ hàng không có sản phẩm");

                }
            }
        });
    }


    private void CatchOnitemListview() {
        listViewGiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, long l) {
                // khoi tao hop thong bao
                AlertDialog.Builder builder = new AlertDialog.Builder(Giohang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.manggiohang.size() <= 0){
                            txtThongbao.setVisibility(view.VISIBLE);
                        }else {
                            MainActivity.manggiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventUtils();
                            if (MainActivity.manggiohang.size() <= 0){
                                txtThongbao.setVisibility(view.VISIBLE);
                            }else {
                                txtThongbao.setVisibility(view.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventUtils();
                            }
                        }
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUtils();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUtils() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.manggiohang.size(); i++){
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtien) + "Đ");
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size() <= 0){
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            listViewGiohang.setVisibility(View.INVISIBLE);
        }else {
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            listViewGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        listViewGiohang = findViewById(R.id.listviewgiohang);
        txtThongbao = findViewById(R.id.txtthongbao);
        txtTongtien = findViewById(R.id.txtTongtien);
        btnThangtoan = findViewById(R.id.btnThanhtoan);
        btnTieptucmua = findViewById(R.id.btnTieptucmua);
        toolbargiohang = findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(Giohang.this, MainActivity.manggiohang);
        listViewGiohang.setAdapter(giohangAdapter);
    }

}
