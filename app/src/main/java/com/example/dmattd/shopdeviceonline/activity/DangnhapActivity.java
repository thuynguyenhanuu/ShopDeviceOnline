package com.example.dmattd.shopdeviceonline.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangnhapActivity extends AppCompatActivity {

    EditText sdtDangnhap, matkhaudangnhap;
    Button btndangnhap, btnhuy;
    TextView btnchuyentrangdangky;

    String sdtMain;
    String passMain;
    boolean isLogin = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        Anhxa();
        EventButton();
    }

    private void EventButton() {
        btnchuyentrangdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangkyActivity.class);
                startActivity(intent);

            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String sdtinput = sdtDangnhap.getText().toString().trim();
                final String matkhauinput = matkhaudangnhap.getText().toString().trim();

                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandangnhap, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("abc", ""+response);
                            if(response != null && response.length() != 2) {
                                String sdtoutput = "";
                                String matkhauoutput = "";
                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        // Lấy id người dùng
                                        CheckStatusUser.idNgdung = jsonObject.getInt("id");
//                                        Log.d("latthongtin", "onResponse: " + jsonObject.getString("ten"));
//                                        Log.d("latthongtin", "onResponse: " + jsonObject.getString("diachi"));
//                                        Log.d("latthongtin", "onResponse: " + jsonObject.getString("matkhau"));

//                                        Log.d("myidlogin", "idout " + CheckStatusUser.idNgdung);

                                        // lưu thông tin người dùng
                                        CheckStatusUser.sdt = jsonObject.getString("sdt");

                                        CheckStatusUser.ten = jsonObject.getString("ten");
                                        CheckStatusUser.diachi = jsonObject.getString("diachi");
                                        CheckStatusUser.matkhau = jsonObject.getString("matkhau");



                                        sdtoutput = jsonObject.getString("sdt");
                                        Log.d("TEST", "sdtout " + sdtoutput);
                                        matkhauoutput = jsonObject.getString("matkhau");
                                        Log.d("TEST", "matkhauout " + matkhauoutput);

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                    if (sdtinput.equals(sdtoutput) && matkhauinput.equals(matkhauoutput)) {

                                        isLogin = true;
                                        CheckStatusUser.isLogin = true;
                                        sdtMain = sdtoutput;
                                        passMain = matkhauoutput;

                                        Log.d("ok", "trùng khớp");
                                        CheckConnection.ShowToast_short(getApplicationContext(), "Đăng nhập thành công");
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);



                                    }
                            }else {
                                CheckConnection.ShowToast_short(getApplicationContext(), "Đăng nhập thất bại");
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("ERRORR", "onResponse: Lỗi rồi ");
                            CheckConnection.ShowToast_short(getApplicationContext(), "Đăng nhập thất bại");

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> param = new HashMap<String, String>();
                            param.put("sdt", sdtinput);
                            param.put("matkhau", matkhauinput);
                            return param;
                        }
                    };

                requestQueue.add(stringRequest);
            }
        });





        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("daDangNhap",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(isLogin) {
            editor.putString("UserName", sdtMain);
            editor.putString("Password", passMain);
            editor.putBoolean("SAVE", true);
            editor.commit();
        }
    }

    private void Anhxa() {
        sdtDangnhap = findViewById(R.id.edtsdtdnhap);
        matkhaudangnhap = findViewById(R.id.edtmatkhaudnhap);
        btndangnhap = findViewById(R.id.btndangnhap);
        btnhuy = findViewById(R.id.btnhuydnhap);
        btnchuyentrangdangky = findViewById(R.id.btndangkytrangdangnhap);
    }
}
