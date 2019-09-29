package com.example.dmattd.shopdeviceonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.model.CheckStatusUser;
import com.example.dmattd.shopdeviceonline.util.CheckConnection;
import com.example.dmattd.shopdeviceonline.util.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangkyActivity extends AppCompatActivity {

    EditText edtten, edtsdt, edtmkhau, edtnhaplaimkhau, edtdiachi;
    Button btndky, btnhuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangkytaikhoan);

        Anhxa();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
       if (CheckConnection.haveNetworkConnection(getApplicationContext())){
           EventButton();
       }else {
           CheckConnection.ShowToast_short(getApplicationContext(), "ban hay kt ket noi");
       }
    }

    private void EventButton() {
        btndky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ten = edtten.getText().toString().trim();
                final String sdt = (edtsdt.getText().toString().trim());
                final String diachi = edtdiachi.getText().toString().trim();
                final String matkhau = edtmkhau.getText().toString().trim();
                final String xacnhanmatkhau = edtnhaplaimkhau.getText().toString().trim();

//                if(ten.length() >0 && sdt.length() > 0 && diachi.length() >0 && matkhau.length() >0 && xacnhanmatkhau.length() >0){
//                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.nguoidung, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            if(response.equals("isExisted")){
//                                Log.d("idngdung","thông báo" + response );
//                                Toast.makeText(getApplicationContext(), "Số đt đã tồn tại", Toast.LENGTH_SHORT).show();
//                            }
//
//                            if(Integer.parseInt(response) > 0){
//
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.d("onErrorResponse", "onErrorResponse: lỗi đăng ký");
//                            Toast.makeText(getApplicationContext(), "sai mật khẩu", Toast.LENGTH_SHORT).show();
//
//
//                        }
//                    }){
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            HashMap<String, String> hashMap = new HashMap<String, String>();
//                            hashMap.put("ten", ten);
//                            hashMap.put("sdt", sdt);
//                            hashMap.put("diachi", diachi);
//                            if(matkhau.equals(xacnhanmatkhau)){
//                                hashMap.put("matkhau", matkhau);
//                            }
//                            return hashMap;
//                        }
//                    };
//
//                    requestQueue.add(stringRequest);
//
//                }else {
//                    CheckConnection.ShowToast_short(getApplicationContext(),"Vui lòng kiểm tra lại các thông tin");
//                }





                if(ten.length() >0 && sdt.length() > 0 && diachi.length() >0 && matkhau.length() >0 && xacnhanmatkhau.length() >0 ){
                    // doc du lieu
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.nguoidung, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String idkhachhang) {
                            Log.d("mak", "onResponse: nguoi dung");
                            Log.d("makh", idkhachhang);
                            if(Integer.parseInt(idkhachhang) > 0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            MainActivity.manggiohang.clear();
                                            CheckConnection.ShowToast_short(getApplicationContext(), "ban da them du lieu gio hang thanh cong");

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_short(getApplicationContext(), "Moi ban tiep tuc mua hang");
                                        }else {
                                            CheckConnection.ShowToast_short(getApplicationContext(), "Du lieu gio hang cua ban da dk gui");

                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("onErrorResponse", "onErrorResponse: loi roi");

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i = 0; i <MainActivity.manggiohang.size(); i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("idkhachhang", idkhachhang);
                                                jsonObject.put("idsanpham", MainActivity.manggiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham", MainActivity.manggiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoluongsp());


                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("onErrorResponse", "onErrorResponse: loi roi");
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("ten", ten);
                            hashMap.put("sdt", sdt);
                            hashMap.put("diachi", diachi);
                            if(matkhau.equals(xacnhanmatkhau)){
                                hashMap.put("matkhau", matkhau);
                            }else{
                                Log.d("mkhausai", "sai sai sai" + matkhau + " " + xacnhanmatkhau);
                                Toast.makeText(getApplicationContext(), "Mat khau sai", Toast.LENGTH_SHORT).show();
                            }
                            return hashMap;

                        }
                    };


                    requestQueue.add(stringRequest);
                }else {
                    CheckConnection.ShowToast_short(getApplicationContext(), "ktra lai thong tin");
                }





            }
        });
    }

    private void Anhxa() {
        edtten = findViewById(R.id.edttendky);
        edtsdt = findViewById(R.id.edtsodienthoađky);
        edtdiachi = findViewById(R.id.edtdiachiđky);
        edtmkhau = findViewById(R.id.edtmatkhaudky);
        edtnhaplaimkhau = findViewById(R.id.edtxacnhanmatkhaudky);
        btndky = findViewById(R.id.btndangkydky);
        btnhuy = findViewById(R.id.btnhuydky);

    }
}
