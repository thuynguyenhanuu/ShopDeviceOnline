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


import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.adapter.GiohangAdapter;
import com.example.dmattd.shopdeviceonline.model.Dangnhap;
import com.example.dmattd.shopdeviceonline.util.CheckConnection;

import java.text.DecimalFormat;

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
//                    Intent intent = new Intent(getApplicationContext(), DangnhapActivity.class);
//                    startActivity(intent);

                    Log.d("THANHTOAN", "onClick: thanh toan thanh cong");

                }else {
                    CheckConnection.ShowToast_short(getApplicationContext(), "Gio hang k co sp");

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
                builder.setTitle("Xac nhan xoa sp");
                builder.setMessage("Ban co chac muon xoa sp nay");
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
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

                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
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
