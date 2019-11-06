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
import com.example.dmattd.shopdeviceonline.util.Server;

import java.util.HashMap;
import java.util.Map;

public class SuaTenActivity extends AppCompatActivity {
    EditText edt;
    Button btnHuy, btnCapnhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ten);

        AnhXa();
        CapnhatTenmoi();

    }

    private void CapnhatTenmoi() {
        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newTen = edt.getText().toString();


                if( newTen.length() <3){
                    Toast.makeText(getApplicationContext(), "Thất bại: tên cần lớn hơn 3 kí tự!", Toast.LENGTH_SHORT).show();

                }else if(newTen.length() > 15){
                    Toast.makeText(getApplicationContext(), "Thất bạt: tên cần nhỏ hơn 15 ký tự!", Toast.LENGTH_SHORT).show();
                }else if(newTen.equals(CheckStatusUser.ten)){
                    Toast.makeText(getApplicationContext(), "Thất bạt: tên mới không thay đổi!", Toast.LENGTH_SHORT).show();
                }else{
                    CheckStatusUser.ten = newTen;
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdansuatenkh, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TEN", "response: "+response);
                            CheckStatusUser.ten = newTen;
                            Toast.makeText(getApplicationContext(), "Đã đổi tên thành công", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            HashMap<String, String> param = new HashMap<>();
                            param.put("id", String.valueOf(CheckStatusUser.idNgdung));
                            param.put("ten", CheckStatusUser.ten);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);

                }
                Intent intent = new Intent(SuaTenActivity.this, ThongtinKhachhangActivity.class);
                startActivity(intent);

            }

        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuaTenActivity.this, ThongtinKhachhangActivity.class);
                startActivity(intent);
            }
        });
    }


    private void AnhXa() {
        edt = findViewById(R.id.edtnoidungten);
        btnHuy = findViewById(R.id.btnhuyten);
        btnCapnhat = findViewById(R.id.btncapnhatten);

        edt.setText(CheckStatusUser.ten);
    }


}
