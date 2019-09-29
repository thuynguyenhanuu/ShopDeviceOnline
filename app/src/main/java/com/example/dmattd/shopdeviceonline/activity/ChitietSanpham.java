package com.example.dmattd.shopdeviceonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.model.CheckStatusUser;
import com.example.dmattd.shopdeviceonline.model.Giohang;
import com.example.dmattd.shopdeviceonline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChitietSanpham extends AppCompatActivity {

    Toolbar toolbarchitietsp;
    ImageView imgchitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua;

    int id = 0;
    String tenchitiet = "";
    int giachitiet = 0;
    String hinhanhchitiet = "";
    String motachitiet = "";
    int id_loaisp = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sanpham);
        AnhXa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
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
                                    MainActivity.manggiohang.get(i).setGiasp(giachitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                                    exites = true;
                                }
                            }
                            if (!exites) {
                                int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                                long giamoi = soluong * giachitiet;
                                MainActivity.manggiohang.add(new Giohang(id, tenchitiet, giamoi, hinhanhchitiet, soluong));


                            }
                        } else {
                            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                            long giamoi = soluong * giachitiet;
                            MainActivity.manggiohang.add(new Giohang(id, tenchitiet, giamoi, hinhanhchitiet, soluong));


                        }
                        Intent intent = new Intent(getApplicationContext(), com.example.dmattd.shopdeviceonline.activity.Giohang.class);
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

    }


}
