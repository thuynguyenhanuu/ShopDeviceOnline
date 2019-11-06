package com.example.dmattd.shopdeviceonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.dmattd.shopdeviceonline.adapter.NhanxetAdapter;
import com.example.dmattd.shopdeviceonline.model.CheckStatusUser;
import com.example.dmattd.shopdeviceonline.model.Giohang;
import com.example.dmattd.shopdeviceonline.model.Nhanxet;
import com.example.dmattd.shopdeviceonline.model.Sanpham;
import com.example.dmattd.shopdeviceonline.model.Traloinhanxet;
import com.example.dmattd.shopdeviceonline.util.CheckConnection;
import com.example.dmattd.shopdeviceonline.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChitietSanpham extends AppCompatActivity {

    Toolbar toolbarchitietsp;
    ImageView imgchitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua, btnThemnx;
    EditText edtVietnx;

    int id = 0;
    String tenchitiet = "";
    int giachitiet = 0;
    String hinhanhchitiet = "";
    String motachitiet = "";
    int id_loaisp = 0;

    int idnhanxet = 0;
    int idnguoinx = 0;
    String tennguoinx = "";
    String noidungnx = "";
    String timenx = "";
    int idXoa = 0;


    int repnumber = 0;

    // check noidung hop le
    boolean hople = false;

    ArrayList<Nhanxet> mangnhanxet;
    NhanxetAdapter nhanxetAdapter;
    RecyclerView recyclerViewChitietsp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sanpham);
        AnhXa();
        ActionToolbar();
        GetInformation();
        GetNhanxet();


        CatchEventSpinner();

        EventButton();
     //   GetdulieuNhanxet();

        ThemNhanXet();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AnhXa();
        ActionToolbar();
        GetInformation();
        GetNhanxet();
        CatchEventSpinner();
        EventButton();
        ThemNhanXet();
    }

    private void GetTraloiNhanxet(final int idnhanxet) {
        Log.wtf("NX", "idnx  " + idnhanxet);

            }

    private void GetNhanxet() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdanngetnhanxet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            idnhanxet = jsonObject.getInt("id");
                            Log.wtf("NX", "id nhan xet: " +idnhanxet);
                            final int idnhanxeti = jsonObject.getInt("id");
                            Log.wtf("NX", "id nhan xet: " +idnhanxet);


                            idnguoinx = jsonObject.getInt("idnguoidung");
                            Log.wtf("NX", "idNguoi nx: " +idnguoinx);

                            noidungnx = jsonObject.getString("noidung");
                            tennguoinx = jsonObject.getString("ten");
                            timenx = jsonObject.getString("time");
                            Log.wtf("TIME", "time: " +timenx);

                            //đếm số rep
                            RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Server.Duongdangettraloinhanxet, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response2) {
                                    if(response2 != null){
                                        try {
                                            JSONArray jsonArray = new JSONArray(response2);
                                            Log.wtf("NUM", "response2: " +response2 );
                                            Log.wtf("NUM", "response2: " +jsonArray );

                                            int numberrep = jsonArray.length();
                                            Log.wtf("NUM", "num rep: " +numberrep +"idcmt:" +idnhanxeti);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("id", String.valueOf(idnhanxeti));
                                    return hashMap;
                                }
                            };
                            requestQueue2.add(stringRequest2);


                            mangnhanxet.add(new Nhanxet(idnhanxet,idnguoinx, noidungnx, tennguoinx, timenx));
                            nhanxetAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("idsp", CheckStatusUser.idsanpham);
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void ThemNhanXet() {


        btnThemnx.setOnClickListener(new View.OnClickListener() {

            String txtnoidungnx = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());



            @Override
            public void onClick(View view) {

                txtnoidungnx = edtVietnx.getText().toString().trim();
                timenx = currentDateandTime;
                Log.d("NXX", "Nội dung 1: " + txtnoidungnx);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdanthemnhanxet, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("invalidcontent")){
                            hople = false;
                            Toast.makeText(getApplicationContext(), "Nội dung từ 1 đến 500 kí tự ", Toast.LENGTH_SHORT).show();
                        }else{
                            hople = true;
                            if(hople){
                                mangnhanxet.add(new Nhanxet(CheckStatusUser.idNgdung, txtnoidungnx, CheckStatusUser.ten, currentDateandTime));
                                Log.d("NXX", "teen nguoi viet nx" + CheckStatusUser.ten);
                                edtVietnx.setText("");
                                nhanxetAdapter.notifyDataSetChanged();


                            }
                            Toast.makeText(getApplicationContext(), "Viết comment thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("onErrorResponse", "onErrorResponse: lỗi viết nx");

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("idsp", String.valueOf(CheckStatusUser.idsanpham));
                        hashMap.put("idnguoidung", String.valueOf(CheckStatusUser.idNgdung));
                        hashMap.put("noidung", txtnoidungnx);
                        return hashMap;
                    }
                };

                requestQueue.add(stringRequest);



//                mangnhanxet.add(new Nhanxet(CheckStatusUser.idNgdung, txtnoidungnx, CheckStatusUser.ten));
//                Log.d("NXX", "teen nguoi viet nx" + CheckStatusUser.ten);
//                edtVietnx.setText("");
//                nhanxetAdapter.notifyDataSetChanged();


            }
        });
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
                if(CheckStatusUser.isLogin) {
                    Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), DangnhapActivity.class);
                    startActivity(intent);
                }

        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {

            btndatmua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(CheckStatusUser.isLogin) {

                        if (MainActivity.manggiohang.size() > 0) {
                            int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                            boolean exites = false;
                            for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                                if (MainActivity.manggiohang.get(i).getIdsp() == id) {
                                    MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                                    if (MainActivity.manggiohang.get(i).getSoluongsp() >= 10) {
                                        MainActivity.manggiohang.get(i).setSoluongsp(10);
                                    }
                                  MainActivity.manggiohang.get(i).setTonggia(giachitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                                    exites = true;
                                }
                            }
                            if (!exites) {
                                int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                                long giamoi = soluong * giachitiet;
                                Log.d("tongtien", "onClick: " + giamoi);
                                MainActivity.manggiohang.add(new Giohang(id, tenchitiet,giachitiet, hinhanhchitiet, soluong, giamoi));
                            }
                        } else {
                            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                            long giamoi = soluong * giachitiet;
                            MainActivity.manggiohang.add(new Giohang(id, tenchitiet, giachitiet, hinhanhchitiet, soluong, giamoi));


                        }
                        Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                        startActivity(intent);

                    }else {
                        Intent intent = new Intent(getApplicationContext(), DangnhapActivity.class);
                        startActivity(intent);
                    }

                }
            });
        }


    private void CatchEventSpinner() {
        Integer [] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsp");
        id = sanpham.getId();

        CheckStatusUser.idsanpham = String.valueOf(id);
        Log.wtf("NX", "id: " +id);

        Log.wtf("NX", "idsua: " +CheckStatusUser.idsanpham);


        tenchitiet = sanpham.getTensp();
        giachitiet = sanpham.getGiasp();
        hinhanhchitiet = sanpham.getHinhanhsp();
        motachitiet = sanpham.getMotasp();
        id_loaisp = sanpham.getId_loaisp();

        txtten.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: " +decimalFormat.format(giachitiet)+ "Đ");
        txtmota.setText(motachitiet);
        Picasso.with(getApplicationContext()).load(hinhanhchitiet)
                .placeholder(R.drawable.delete_button)
                .error(R.drawable.no_image)
                .into(imgchitiet);

    }



    private void ActionToolbar() {
        setSupportActionBar(toolbarchitietsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitietsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    private void AnhXa() {
        toolbarchitietsp = findViewById(R.id.toolbarChitietsp);
        imgchitiet = findViewById(R.id.imgChitiet);
        txtten = findViewById(R.id.txttenchitiet);
        txtgia = findViewById(R.id.txtgiachitiet);
        txtmota = findViewById(R.id.txtMotachitiet);
        spinner = findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.btnThem);

        btnThemnx = findViewById(R.id.btnVietNhanxet);
        edtVietnx = findViewById(R.id.edtVietNhanxet);

        recyclerViewChitietsp = findViewById(R.id.recycleViewComment);


        mangnhanxet = new ArrayList<>();
        nhanxetAdapter = new NhanxetAdapter(getApplicationContext(), mangnhanxet);

        //đăng ký view cho context menu
        registerForContextMenu(recyclerViewChitietsp);
        nhanxetAdapter.setOnItemClickListener(new RecycleviewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
               // Toast.makeText(getApplicationContext(), "short", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onItemLongClick(View view, int i) {
               // Toast.makeText(getApplicationContext(), "long" + mangnhanxet.get(i).getIdnguoinx(), Toast.LENGTH_SHORT ).show();
                idnguoinx = mangnhanxet.get(i).getIdnguoinx();
                idnhanxet = mangnhanxet.get(i).getId();
                noidungnx = mangnhanxet.get(i).getNoidungnx();
                timenx = mangnhanxet.get(i).getTime();
                idXoa = i;

                Log.d("ID", "id mangnx " );
                //Toast.makeText(getApplicationContext(), "abc"+ idXoa, Toast.LENGTH_SHORT).show();
                view.showContextMenu();

            }
        });


        recyclerViewChitietsp.setHasFixedSize(true);
        recyclerViewChitietsp.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewChitietsp.setAdapter(nhanxetAdapter);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if(idnguoinx == CheckStatusUser.idNgdung){
            getMenuInflater().inflate(R.menu.menu_xoa_sua, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_traloi_cmt, menu);

        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.traloicmt:
                break;
            case  R.id.xoacmt:
                Log.d("ID", "id xoa nx1: "+idnhanxet);
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdanxoanhanxet, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TEST", "sdtout " + response);
                        CheckConnection.ShowToast_short(getApplicationContext(), "xoa ok");

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TEST", "sdtout " + error);

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("id", String.valueOf(idnhanxet));
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
                mangnhanxet.remove(idXoa);
                nhanxetAdapter.notifyDataSetChanged();
                break;
            case R.id.suacmt:
                Log.wtf("ND", "suacmt:" + noidungnx + " " + idnhanxet);

                Intent intent = new Intent(this, SuaCommentActivity.class);
                intent.putExtra("suanhanxet", noidungnx);
                intent.putExtra("id", idnhanxet);
                startActivity(intent);
                nhanxetAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }
}
