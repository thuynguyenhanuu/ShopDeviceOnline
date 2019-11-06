package com.example.dmattd.shopdeviceonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.dmattd.shopdeviceonline.adapter.SanphamDatmuaAdapter;
import com.example.dmattd.shopdeviceonline.model.CheckStatusUser;
import com.example.dmattd.shopdeviceonline.model.Giohang;
import com.example.dmattd.shopdeviceonline.util.CheckConnection;
import com.example.dmattd.shopdeviceonline.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XacnhanThanhtoanActivity extends AppCompatActivity {
    ListView listViewGiohang;
    //static TextView txtTongtien;
    Button btntdatmua;
    SanphamDatmuaAdapter sanphamDatmuaAdapter;
    TextView txtTongtien;

    TextView ten, sdt, diachi, hinhthucgiaohang, hinhthucthanhtoan;
    Toolbar toolbar;

    long tongtien = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xacnhan_thanhtoan);

        Anhxa();
        EventUtils();
        ActionToolBar();
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

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarxacnhan);
        ten = findViewById(R.id.txtTentt);
        sdt = findViewById(R.id.txtSdttt);
        diachi = findViewById(R.id.txtDiachitt);
        hinhthucgiaohang = findViewById(R.id.txtGiaohangtt);
        hinhthucthanhtoan = findViewById(R.id.txtThanhtoantt);
        listViewGiohang = findViewById(R.id.listviewsanphammua);
        txtTongtien = findViewById(R.id.txtTongtienthanhtoan);
       // tongtien.setText(MainActivity.manggiohang.getGiasp());
        btntdatmua = findViewById(R.id.buttonDathang);
        sanphamDatmuaAdapter = new SanphamDatmuaAdapter(XacnhanThanhtoanActivity.this, MainActivity.manggiohang );
        listViewGiohang.setAdapter(sanphamDatmuaAdapter);
    }
    public void EventUtils() {
        for (int i = 0; i < MainActivity.manggiohang.size(); i++){
//            tongtien += MainActivity.manggiohang.get(i).getGiasp();
            tongtien += MainActivity.manggiohang.get(i).getTonggia();

        }
        Log.d("tongtien", "EventUtils: " + tongtien);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtien) + "Đ");


        ten.setText(CheckStatusUser.ten);
        sdt.setText(CheckStatusUser.sdt);
        diachi.setText(CheckStatusUser.diachigiaohang);
        hinhthucthanhtoan.setText(CheckStatusUser.hinhthucthanhtoan);
        hinhthucgiaohang.setText(CheckStatusUser.hinhthucvanchuyen);

        Log.d("DH", "1"+CheckStatusUser.idNgdung);
        Log.d("DH", "1"+CheckStatusUser.diachigiaohang);
        Log.d("DH", "1"+CheckStatusUser.hinhthucthanhtoan);
        Log.d("DH", "1"+CheckStatusUser.hinhthucvanchuyen);
        Log.d("DH", "1"+tongtien);

        // đặt mua

//        btntdatmua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CheckConnection.ShowToast_short(getApplicationContext(), "Đặt hàng thành công, mời bạn tiếp tục mua hàng");
//                if(MainActivity.manggiohang.size() > 0){
//                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            if(response.equals("1")){
//                                MainActivity.manggiohang.clear();
//                                Log.d("response111", "" + response);
//
//                                RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
//                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Server.Duongdandathang, new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//                                        Log.d("DH", ""+CheckStatusUser.idNgdung);
//                                        Log.d("DH", ""+CheckStatusUser.diachigiaohang);
//                                        Log.d("DH", ""+CheckStatusUser.hinhthucthanhtoan);
//                                        Log.d("DH", ""+CheckStatusUser.hinhthucvanchuyen);
//                                        Log.d("DH", ""+tongtien);
//
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//
//                                    }
//                                }){
//                                    @Override
//                                    protected Map<String, String> getParams() throws AuthFailureError {
//                                        HashMap<String,String> hashMap = new HashMap<String, String>();
//                                        hashMap.put("idkh", String.valueOf(CheckStatusUser.idNgdung));
//                                        hashMap.put("diachigiaohang", CheckStatusUser.diachigiaohang);
//                                        hashMap.put("hinhthucthanhtoan", CheckStatusUser.hinhthucthanhtoan);
//                                        hashMap.put("hinhthucvanchuyen", CheckStatusUser.hinhthucvanchuyen);
//                                        hashMap.put("tongtien", String.valueOf(tongtien));
//                                        return hashMap;
//                                    }
//                                };
//                                requestQueue2.add(stringRequest2);
//                            }
//
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    }){
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            JSONArray jsonArray = new JSONArray();
//                            for(int i = 0; i < MainActivity.manggiohang.size(); i++){
//                                JSONObject jsonObject = new JSONObject();
//                                try {
//                                    jsonObject.put("idkhachhang", CheckStatusUser.idNgdung);
//                                    jsonObject.put("idsanpham", MainActivity.manggiohang.get(i).getIdsp());
//                                    jsonObject.put("tensanpham", MainActivity.manggiohang.get(i).getTensp());
//                                    jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiasp());
//                                    jsonObject.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoluongsp());
////
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                jsonArray.put(jsonObject);
//
//                            }
//                            HashMap<String,String> hashMap = new HashMap<String, String>();
//                            hashMap.put("json", jsonArray.toString());
//                            return hashMap;
//
//                        }
//                    };
//                    requestQueue.add(stringRequest);
//
//                }else {
//                    CheckConnection.ShowToast_short(getApplicationContext(), "Giỏ hàng không có sản phẩm");
//
//                }
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//            }
//
//
//        });


        btntdatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckConnection.ShowToast_short(getApplicationContext(), "Đặt hàng thành công, mời bạn tiếp tục mua hàng");
                if(MainActivity.manggiohang.size() > 0){
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdantaodonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Server.DuongdangetIDdonhang, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response2) {
                                    Log.wtf("IDD", "response2:"+response2);
                                    CheckConnection.ShowToast_short(getApplicationContext(), "response2: "+ response2);
                                    try {
                                        JSONArray jsonarray = new JSONArray(response2);
                                        for (int i = 0; i< jsonarray.length(); i++){
                                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                                            int iddonhang = jsonObject.getInt("id_don");
                                            CheckStatusUser.idDonhang = iddonhang;
                                            Log.wtf("IDD", "iddonhang2: "+iddonhang);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //list hàng đặt mua
                                            final RequestQueue requestQueue3 = Volley.newRequestQueue(getApplicationContext());
                                            StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Server.Duongdanlisthangmua, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response3) {
                                                    if(response3.equals("1")){
                                                        Log.wtf("DDD", "response3: "+response3);
                                                        MainActivity.manggiohang.clear();
                                                    }else {
                                                        CheckConnection.ShowToast_short(getApplicationContext(), "Giỏ hàng không có sản phẩm");
                                                    }
//                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                                    startActivity(intent);

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
                                                            jsonObject.put("id_don", CheckStatusUser.idDonhang);
                                                            jsonObject.put("idsanpham", MainActivity.manggiohang.get(i).getIdsp());
                                                            jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiasp());
                                                            jsonObject.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoluongsp());

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
                                                   requestQueue3.add(stringRequest3);


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.wtf("IDD", "eror: "+error);

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> hashMap = new HashMap<String, String>();
                                    hashMap.put("idkh", String.valueOf(CheckStatusUser.idNgdung));
                                    return hashMap;
                                }
                            };
                            requestQueue2.add(stringRequest2);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("idkh", String.valueOf(CheckStatusUser.idNgdung));
                            hashMap.put("diachigiaohang", CheckStatusUser.diachigiaohang);
                            hashMap.put("hinhthucthanhtoan", CheckStatusUser.hinhthucthanhtoan);
                            hashMap.put("hinhthucvanchuyen", CheckStatusUser.hinhthucvanchuyen);
                            hashMap.put("tongtien", String.valueOf(tongtien));
                            return hashMap;

                        }
                    };
                    requestQueue.add(stringRequest);

                }else {
                    CheckConnection.ShowToast_short(getApplicationContext(), "Giỏ hàng không có sản phẩm");

                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }


        });



    }
}
