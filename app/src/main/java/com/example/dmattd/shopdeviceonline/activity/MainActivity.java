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
import com.example.dmattd.shopdeviceonline.model.Giohang;
import com.example.dmattd.shopdeviceonline.model.LoaiSanPham;
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

    //login made by thủy đáng yêu
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
            CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
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

//        if(CheckStatusUser.isLogin) {
//            mangloaisp.remove(4);
//
//            String labelUser;
//            String urlUser;
//            if (CheckStatusUser.isLogin) {
//                labelUser = userNameMain;
//                urlUser = "https://cdn2.iconfinder.com/data/icons/business-charts-red/512/account_user_people_business_money_office_login-512.png";
//            } else {
//                labelUser = "Dang ki";
//                urlUser = "https://gamasonic.com/wp-content/uploads/2016/02/Product-Registration-Icon.jpg";
//            }
//            Log.d("MMM", "addControls: " + labelUser);
//            mangloaisp.add(4, new Loaisp(0, labelUser, urlUser));
//            //
//        }

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
                            CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // dong man hinh listview
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LoaiSanPhamAcc.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // dong man hinh listview
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienheActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // dong man hinh listview
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ThongtinActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // dong man hinh listview
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckStatusUser.isLogin) {
                            //hien thi thong tin nguoi dung
                        }else {
                            //dang ki
                            Intent intent = new Intent(MainActivity.this, DangkyActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case  5:
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
                            CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
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

    private void GetNavi(){
        mangloaisp.add(0, new Loaisp(0, "Trang chinh", "https://img.icons8.com/cotton/2x/home--v2.png"));
        mangloaisp.add(1, new Loaisp(0, "Loại sp", "data:image/png;base64,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"));
        mangloaisp.add(2, new Loaisp(0, "Liên hệ", "https://phukiensinhnhat.vn/wp-content/uploads/2017/08/Phone-icon.jpg"));
        mangloaisp.add(3, new Loaisp(0, "Thông tin", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSNn5dHbPDP34Uch2WIpMd1DzJoYSKp7-XUCBe_4lK6pVB7qSCG"));


        String labelUser;
        String urlUser;
        if(CheckStatusUser.isLogin) {
            labelUser = userNameMain;
            Log.d("MMM", "GetNavi: " + userNameMain);
            urlUser = "https://cdn2.iconfinder.com/data/icons/business-charts-red/512/account_user_people_business_money_office_login-512.png";
        }
        else {
            labelUser = "Dang ki";
            urlUser = "https://gamasonic.com/wp-content/uploads/2016/02/Product-Registration-Icon.jpg";
        }
        Log.d("MMM", "addControls: " + labelUser);
        mangloaisp.add(4, new Loaisp(0, labelUser, urlUser));


        //
        String label;
        String urlIconLog;
        if(CheckStatusUser.isLogin){
            label = "Dang xuat";
            urlIconLog = "https://cdn1.iconfinder.com/data/icons/basic-ui-elements-coloricon/21/46-512.png";
        } else {
            label = "Dang nhap";
            urlIconLog = "https://previews.123rf.com/images/alexwhite/alexwhite1410/alexwhite141000976/32176618-login-red-modern-web-icon-on-white-background.jpg";

        }
        mangloaisp.add(5, new Loaisp(0, label, urlIconLog));

        //

        loaispAdapter.notifyDataSetChanged();

    }

    private void GetDuLieuLoaisp() {
        // thuc hien cac phuong thuc gui len server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response != null){
                    for (int i = 0; i<response.length() ; i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            // lay du lieu ra
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            Log.d("IMAGE", "onResponse: " + hinhanhloaisp);
                            //mangloaisp.add(i + 1, new Loaisp(id, tenloaisp, hinhanhloaisp));
                            LoaiSanPham loaiSanPham = new LoaiSanPham(id, tenloaisp, hinhanhloaisp);
                            CheckStatusUser.arrayListLoaiSanPham.add(loaiSanPham);
                            Log.d("array", "onResponse: " + CheckStatusUser.arrayListLoaiSanPham);
                            loaispAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    mangloaisp.add(1, new Loaisp(0, "Loại sp", "data:image/png;base64,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"));

                    mangloaisp.add(2, new Loaisp(0, "Liên hệ", "https://phukiensinhnhat.vn/wp-content/uploads/2017/08/Phone-icon.jpg"));
                    mangloaisp.add(3, new Loaisp(0, "Thông tin", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSNn5dHbPDP34Uch2WIpMd1DzJoYSKp7-XUCBe_4lK6pVB7qSCG"));

                    if(CheckStatusUser.isLogin) {
                        mangloaisp.add(4, new Loaisp(0, userNameMain, "https://cdn2.iconfinder.com/data/icons/business-charts-red/512/account_user_people_business_money_office_login-512.png"));
                    }
                    else {
                        mangloaisp.add(4, new Loaisp(0, "Đăng ký", "https://gamasonic.com/wp-content/uploads/2016/02/Product-Registration-Icon.jpg"));

                    }
                    String label = "Đăng nhập";
                    String urlIconLog = "https://previews.123rf.com/images/alexwhite/alexwhite1410/alexwhite141000976/32176618-login-red-modern-web-icon-on-white-background.jpg";
                    if(CheckStatusUser.isLogin){
                        label = "Đăng xuất";
                        urlIconLog = "https://cdn1.iconfinder.com/data/icons/basic-ui-elements-coloricon/21/46-512.png";
                    }
                    mangloaisp.add(5, new Loaisp(0, label, urlIconLog));




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
