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
           CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
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

                if(ten.length() >0 && sdt.length() > 0 && diachi.length() >0 && matkhau.length() >0 && xacnhanmatkhau.length() >0){
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.nguoidung, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("isExisted")){
                                Log.d("TTT", "onResponse: " +response);
                                Toast.makeText(getApplicationContext(), "Số điện thoại đã được dùng", Toast.LENGTH_SHORT).show();
                            }else if(response.equals("invalidname")){
                                Log.d("TTT", "onResponse: " +response);
                                Toast.makeText(getApplicationContext(), "Tên không hợp lệ(tên từ 3-15 kí tự)", Toast.LENGTH_SHORT).show();
                            }else if(response.equals("invalidpass")){
                                Log.d("TTT", "onResponse: " +response);
                                Toast.makeText(getApplicationContext(), "Mật khẩu không hợp lệ (từ 8 đến 16 kí tự)", Toast.LENGTH_SHORT).show();
                            }else if(response.equals("invalidaddress")){
                                Log.d("TTT", "onResponse: " +response);
                                Toast.makeText(getApplicationContext(), "Địa chỉ không hợp lệ (địa chỉ từ 10 -50 kí tự)", Toast.LENGTH_SHORT).show();
                            }else if(response.equals("invalidphone")){
                                Log.d("TTT", "onResponse: " +response);
                                Toast.makeText(getApplicationContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                            }else if(!matkhau.equals(xacnhanmatkhau)) {
                                Log.d("TTT", "onResponse: " +matkhau + "" + xacnhanmatkhau);
                                Toast.makeText(getApplicationContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                            }else if(matkhau.equals(xacnhanmatkhau)){
                                    Log.d("TTT", "onResponse: " +matkhau + " = " + xacnhanmatkhau);
                                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("onErrorResponse", "onErrorResponse: lỗi đăng ký");
                            Toast.makeText(getApplicationContext(), "sai mật khẩu", Toast.LENGTH_SHORT).show();


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
                            }
                            return hashMap;
                        }
                    };

                    requestQueue.add(stringRequest);

                }else {
                    CheckConnection.ShowToast_short(getApplicationContext(),"Vui lòng kiểm tra lại các thông tin");
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
