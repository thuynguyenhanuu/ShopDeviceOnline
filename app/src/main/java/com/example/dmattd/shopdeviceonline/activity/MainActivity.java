package com.example.dmattd.shopdeviceonline.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.adapter.LoaispAdapter;
import com.example.dmattd.shopdeviceonline.adapter.SanphamAdapter;
import com.example.dmattd.shopdeviceonline.model.CheckStatusUser;
import com.example.dmattd.shopdeviceonline.model.Dangnhap;
import com.example.dmattd.shopdeviceonline.model.Giohang;
import com.example.dmattd.shopdeviceonline.model.Loaisp;
import com.example.dmattd.shopdeviceonline.model.Sanpham;
import com.example.dmattd.shopdeviceonline.util.CheckConnection;
import com.example.dmattd.shopdeviceonline.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //login made by khai
    String userNameMain;
    String passWordMain;


    android.support.v7.widget.Toolbar toolbar;
    //Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;

    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;

    int id = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";

    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;

    public static boolean daDangnhap = false;

    public static ArrayList<Giohang> manggiohang;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
            GetDuLieuSPMoiNhat();
            CatchOnItemListView();

        }else {
            CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy ktra kết nỗi");
            finish();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("daDangNhap",MODE_PRIVATE);
        String username = sharedPreferences.getString("UserName","");
        String password = sharedPreferences.getString("Password","");
        boolean save = sharedPreferences.getBoolean("SAVE",false);
        Log.d("dangnhap", "onResume: " + username + "/" + password);

        if(save)
        {
            userNameMain = username;
            passWordMain = password;
            CheckStatusUser.isLogin = true;
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
                if(CheckStatusUser.isLogin) {
                    Intent intent = new Intent(getApplicationContext(), com.example.dmattd.shopdeviceonline.activity.Giohang.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, DangnhapActivity.class);
                    startActivity(intent);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // bắt sự kiện cho từng item trong listview
    private void CatchOnItemListView() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    // trang chinh
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(), "Ban hay kt lai ket noi");
                        }
                        // dong man hinh listview
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, Sanpham1Activity.class);

                            //chuyen man hinh
                            intent.putExtra("id_loaisp", mangloaisp.get(i).getId());

                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(), "Ban hay kt lai ket noi");
                        }
                        // dong man hinh listview
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, Sanpham2Activity.class);
                            intent.putExtra("id_loaisp", mangloaisp.get(i).getId());
                            Log.d("loaisanpham2", "onItemClick: loai san pham" + mangloaisp.get(i).getId());
                            startActivity(intent);

                            //chuyen man hinh

                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(), "Ban hay kt lai ket noi");
                        }
                        // dong man hinh listview
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienheActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(), "Ban hay kt lai ket noi");
                        }
                        // dong man hinh listview
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ThongtinActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(), "Ban hay kt lai ket noi");
                        }
                        // dong man hinh listview
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckStatusUser.isLogin) {
                            //hien thi thong tin nguoi dung
                        }else {
                            //dang ki
                            Intent intent = new Intent(MainActivity.this, DangkyActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case  6:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Log.d("DANGXUAT", "onItemClick: thoat");
                            if (CheckStatusUser.isLogin) {
                                CheckStatusUser.isLogin = false;
                                SharedPreferences sharedPreferences = getSharedPreferences("daDangNhap", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("UserName", null);
                                editor.putString("Password", null);
                                editor.putBoolean("SAVE", false);
                                editor.commit();
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this, DangnhapActivity.class);
                                startActivity(intent);
                            }

                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(), "Ban hay kt lai ket noi");
                        }
                        // dong man hinh listview
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });

    }

    private void GetDuLieuSPMoiNhat() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duondanspmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("SPMN", "onResponse: " + response);
                if(response != null){
                    int ID = 0;
                    String Tensp = "";
                    Integer Giasp = 0;
                    String Hinhanhsp = "";
                    String Motasp = "";
                    int ID_loaisp = 0;

                    for (int i = 0; i < response.length(); i++){
                        try {


                            JSONObject jsonObject = response.getJSONObject(i);
                            Log.d("Getsanpham", "onResponse: " + jsonObject);
                            ID = jsonObject.getInt("id");
                            Log.d("Getsanpham", "onResponse: " + ID);

                            Tensp = jsonObject.getString("tensp");
                            Log.d("Getsanpham", "onResponse: " + Tensp);

                            Giasp = jsonObject.getInt("giasp");
                            Log.d("Getsanpham", "onResponse: " + Giasp);

                            Hinhanhsp = jsonObject.getString("hinhanhsp");
                            Log.d("Getsanpham", "onResponse: " + Hinhanhsp);

                            Motasp = jsonObject.getString("mieutasp");
                            Log.d("Getsanpham", "onResponse: " + Motasp);

                            ID_loaisp = jsonObject.getInt("id_loaisp");
                            Log.d("Getsanpham", "onResponse: " + ID_loaisp);

                            mangsanpham.add(new Sanpham(ID, Tensp, Giasp, Hinhanhsp, Motasp, ID_loaisp));
                            sanphamAdapter.notifyDataSetChanged();


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


    private void GetDuLieuLoaisp() {
        // thuc hien cac phuong thuc gui len server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("SPMN", "onResponse: " + response);
                if(response != null){
                    for (int i = 0; i<response.length() ; i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            // lay du lieu ra
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            Log.d("IMAGE", "onResponse: " + hinhanhloaisp);
                            mangloaisp.add(i + 1, new Loaisp(id, tenloaisp, hinhanhloaisp));

                            loaispAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    mangloaisp.add(3, new Loaisp(0, "Liên hệ", "https://phukiensinhnhat.vn/wp-content/uploads/2017/08/Phone-icon.jpg"));
                    mangloaisp.add(4, new Loaisp(0, "Thông tin", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSNn5dHbPDP34Uch2WIpMd1DzJoYSKp7-XUCBe_4lK6pVB7qSCG"));

                    if(CheckStatusUser.isLogin) {
                        mangloaisp.add(5, new Loaisp(0, userNameMain, "https://cdn2.iconfinder.com/data/icons/business-charts-red/512/account_user_people_business_money_office_login-512.png"));
                    }
                    else {
                        mangloaisp.add(5, new Loaisp(0, "Dang ki", "https://gamasonic.com/wp-content/uploads/2016/02/Product-Registration-Icon.jpg"));

                    }
                    String label = "Dang nhap";
                    String urlIconLog = "https://previews.123rf.com/images/alexwhite/alexwhite1410/alexwhite141000976/32176618-login-red-modern-web-icon-on-white-background.jpg";
                    if(CheckStatusUser.isLogin){
                        label = "Dang xuat";
                        urlIconLog = "https://cdn1.iconfinder.com/data/icons/basic-ui-elements-coloricon/21/46-512.png";
                    }
                    mangloaisp.add(6, new Loaisp(0, label, urlIconLog));



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

    private void ActionViewFlipper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        String urlF = "https://sachsuthattphcm.com.vn/wp-content/uploads/2019/08/banner.jpg";
        String urlS = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcReNI1t3rGCDAzzO11D5uEwJEGtNTlfKFe2FjT0Zw_0qCO4-4IQ";
        String urlT = "http://sachluathongbich.com/images/content/advbanner_s13.jpg";
        mangQuangCao.add(urlF);
        mangQuangCao.add(urlS);
        mangQuangCao.add(urlT);

        for (int i = 0;i < mangQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }


    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setSupportActionBar(Toolbar toolbar) {
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }



    private void addControls() {
        toolbar = findViewById(R.id.toolbarManHinhChinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerView);
        navigationView = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listviewManHinhChinh);
        drawerLayout = findViewById(R.id.drawerLayout);

        mangloaisp = new ArrayList<>();
        mangloaisp.add(0, new Loaisp(0, "Trang chinh", "https://img.icons8.com/cotton/2x/home--v2.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp, getApplicationContext());
        listViewManHinhChinh.setAdapter(loaispAdapter);
        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(), mangsanpham);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        recyclerViewManHinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewManHinhChinh.setAdapter(sanphamAdapter);

        if(manggiohang != null){

        }else {
            manggiohang = new ArrayList<>();
        }
    }



}
