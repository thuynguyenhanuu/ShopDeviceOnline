package com.example.dmattd.shopdeviceonline.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class ThongtinKhachhangActivity extends AppCompatActivity {
    
    TextView txtten, txtsdt, txtdiachi, txtmatkhau;
    EditText edtten, edtsdt, edtdiachi, edtmatkhau;
    ImageButton imgEditten, imgEditsdt, imgEditdiachi, imgEditmatkhau;
    ImageButton imgSaveten, imgSavesdt, imgSavediachi, imgSavematkhau;
    ImageButton imgCancelten, imgCanceldiachi, imgCancelmatkhau;
    //int idnguoidung = CheckStatusUser.idNgdung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_khachhang);
        Anhxa();
        GetThongtinkhachhang();
        EventButtons();
    }

    private void GetThongtinkhachhang() {
        //Gán id = id đang đăng nhập
        //idnguoidung = CheckStatusUser.idNgdung;



        txtten.setVisibility(View.VISIBLE);
        txtsdt.setVisibility(View.VISIBLE);
        txtdiachi.setVisibility(View.VISIBLE);
        txtmatkhau.setVisibility(View.VISIBLE);

        edtten.setVisibility(View.INVISIBLE);
        edtsdt.setVisibility(View.INVISIBLE);
        edtdiachi.setVisibility(View.INVISIBLE);
        edtmatkhau.setVisibility(View.INVISIBLE);

        imgEditten.setVisibility(View.VISIBLE);
        imgEditsdt.setVisibility(View.INVISIBLE);
        imgEditdiachi.setVisibility(View.VISIBLE);
        imgEditmatkhau.setVisibility(View.VISIBLE);

        imgSaveten.setVisibility(View.INVISIBLE);
        imgSavesdt.setVisibility(View.INVISIBLE);
        imgSavediachi.setVisibility(View.INVISIBLE);
        imgSavematkhau.setVisibility(View.INVISIBLE);

        txtten.setText(CheckStatusUser.ten);
        txtsdt.setText(CheckStatusUser.sdt);
        txtdiachi.setText(CheckStatusUser.diachi);
        txtmatkhau.setText(CheckStatusUser.matkhau);


    }

    private void EventButtons() {
        imgEditten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtten.setVisibility(View.INVISIBLE);
                edtten.setVisibility(View.VISIBLE);
                imgEditten.setVisibility(View.INVISIBLE);
                imgSaveten.setVisibility(View.VISIBLE);
                imgCancelten.setVisibility(View.VISIBLE);
                edtten.setText(CheckStatusUser.ten);
            }
        });

        imgEditdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtdiachi.setVisibility(View.INVISIBLE);
                edtdiachi.setVisibility(View.VISIBLE);
                imgEditdiachi.setVisibility(View.INVISIBLE);
                imgSavediachi.setVisibility(View.VISIBLE);

                edtdiachi.setText(CheckStatusUser.diachi);
            }
        });

        imgEditmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtmatkhau.setVisibility(View.INVISIBLE);
                edtmatkhau.setVisibility(View.VISIBLE);
                imgEditmatkhau.setVisibility(View.INVISIBLE);
                imgSavematkhau.setVisibility(View.VISIBLE);

                edtmatkhau.setText(CheckStatusUser.matkhau);
            }
        });

        imgSaveten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtten.setVisibility(View.VISIBLE);
                edtten.setVisibility(View.INVISIBLE);
                imgEditten.setVisibility(View.VISIBLE);
                imgSaveten.setVisibility(View.INVISIBLE);
                imgCancelten.setVisibility(View.INVISIBLE);


                String newTen = edtten.getText().toString();
               // CheckStatusUser.ten = edtten.getText().toString();

                if( newTen.length() <=3){
                    Toast.makeText(getApplicationContext(), "Thất bại: tên cần lớn hơn 3 kí tự!", Toast.LENGTH_SHORT).show();

                }else if(newTen.length() > 50){
                    Toast.makeText(getApplicationContext(), "Thất bạt: tên cần nhỏ hơn 50 ký tự!", Toast.LENGTH_SHORT).show();
                }else if(newTen.equals(CheckStatusUser.ten)){
                    Toast.makeText(getApplicationContext(), "Thất bạt: tên mới không thay đổi!", Toast.LENGTH_SHORT).show();
                }else{
                    CheckStatusUser.ten = newTen;
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdansuatenkh, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TEN", "response: "+response);
                            txtten.setText(CheckStatusUser.ten);
                            Toast.makeText(getApplicationContext(), "Đã đổi tên thành công", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> param = new HashMap<>();
                            param.put("id", String.valueOf(CheckStatusUser.idNgdung));
                            param.put("ten", CheckStatusUser.ten);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);

                }


            }
        });

        imgCancelten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtten.setVisibility(View.VISIBLE);
                edtten.setVisibility(View.INVISIBLE);
                imgEditten.setVisibility(View.VISIBLE);
                imgSaveten.setVisibility(View.INVISIBLE);
                imgCancelten.setVisibility(View.INVISIBLE);
            }
        });

        imgSavediachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtdiachi.setVisibility(View.VISIBLE);
                edtdiachi.setVisibility(View.INVISIBLE);
                imgEditdiachi.setVisibility(View.VISIBLE);
                imgSavediachi.setVisibility(View.INVISIBLE);
                String newdiachi = edtdiachi.getText().toString();
               // CheckStatusUser.diachi = edtdiachi.getText().toString();
                Log.d("DC", " "+ CheckStatusUser.diachi);

                if( newdiachi.length() <=3){
                    Toast.makeText(getApplicationContext(), "Thất bại: địa chỉ cần lớn hơn 3 kí tự!", Toast.LENGTH_SHORT).show();

                }else if(newdiachi.length() > 50){
                    Toast.makeText(getApplicationContext(), "Thất bạt: địa chỉ cần nhỏ hơn 50 ký tự!", Toast.LENGTH_SHORT).show();
                }else if(newdiachi.equals(CheckStatusUser.diachi)){
                    Toast.makeText(getApplicationContext(), "Thất bạt: địa chỉ mới không thay đổi!", Toast.LENGTH_SHORT).show();
                }else{
                    CheckStatusUser.diachi = newdiachi;

                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdansuadiachi, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            txtdiachi.setText(CheckStatusUser.diachi);
                            Log.d("DC", "response "+ CheckStatusUser.diachi);
                            Toast.makeText(getApplicationContext(), "Đã đổi địa chỉ thành công", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> param = new HashMap<>();
                            param.put("id", String.valueOf(CheckStatusUser.idNgdung));
                            param.put("diachi", CheckStatusUser.diachi);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);

                }


            }
        });

        imgCanceldiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtdiachi.setVisibility(View.VISIBLE);
                edtdiachi.setVisibility(View.INVISIBLE);
                imgEditdiachi.setVisibility(View.VISIBLE);
                imgSavediachi.setVisibility(View.INVISIBLE);
            }
        });

        imgSavematkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtmatkhau.setVisibility(View.VISIBLE);
                edtmatkhau.setVisibility(View.INVISIBLE);
                imgEditmatkhau.setVisibility(View.VISIBLE);
                imgSavematkhau.setVisibility(View.INVISIBLE);

                String newMatkhau = edtmatkhau.getText().toString();

                if( newMatkhau.length() <=8){
                    Toast.makeText(getApplicationContext(), "Thất bại: mật khẩu cần lớn hơn 8 kí tự!", Toast.LENGTH_SHORT).show();

                }else if(newMatkhau.length() > 50){
                    Toast.makeText(getApplicationContext(), "Thất bạt: mật khẩu cần nhỏ hơn 50 ký tự!", Toast.LENGTH_SHORT).show();
                }else if(newMatkhau.equals(CheckStatusUser.matkhau)){
                    Toast.makeText(getApplicationContext(), "Thất bạt: mật khẩu mới không thay đổi!", Toast.LENGTH_SHORT).show();
                }else{
                    CheckStatusUser.matkhau = newMatkhau;

                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdansuamatkhau, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            txtmatkhau.setText(CheckStatusUser.matkhau);
                            Log.d("DC", "response "+ CheckStatusUser.matkhau);
                            Toast.makeText(getApplicationContext(), "Đã đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> param = new HashMap<>();
                            param.put("id", String.valueOf(CheckStatusUser.idNgdung));
                            param.put("matkhau", CheckStatusUser.matkhau);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);

                }


            }
        });

        imgCancelmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtmatkhau.setVisibility(View.VISIBLE);
                edtmatkhau.setVisibility(View.INVISIBLE);
                imgEditmatkhau.setVisibility(View.VISIBLE);
                imgSavematkhau.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void Anhxa() {
        txtten = findViewById(R.id.txtvalueten);
        txtsdt = findViewById(R.id.txtvaluesdt);
        txtdiachi = findViewById(R.id.txtvaluediachi);
        txtmatkhau = findViewById(R.id.txtvaluematkhau);

        edtten = findViewById(R.id.edtvalueten);
        edtsdt = findViewById(R.id.edtvaluesdt);
        edtdiachi = findViewById(R.id.edtvaluediachi);
        edtmatkhau = findViewById(R.id.edtvaluematkhau);

        imgEditten = findViewById(R.id.imagetenedit);
        imgEditsdt = findViewById(R.id.imagesdtedit);
        imgEditdiachi = findViewById(R.id.imagediachiedit);
        imgEditmatkhau = findViewById(R.id.imagematkhauedit);

        imgSaveten = findViewById(R.id.imagetensavet);
        imgSavesdt = findViewById(R.id.imagesdtsave);
        imgSavediachi = findViewById(R.id.imagediachisave);
        imgSavematkhau = findViewById(R.id.imagematkhausave);

        imgCancelten = findViewById(R.id.imagetencancle);
        imgCanceldiachi = findViewById(R.id.imagediachicancle);
        imgCancelmatkhau = findViewById(R.id.imagematkhaucancle);
    }

}
