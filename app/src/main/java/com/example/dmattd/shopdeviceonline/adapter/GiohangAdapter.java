package com.example.dmattd.shopdeviceonline.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.activity.GiohangActivity;
import com.example.dmattd.shopdeviceonline.activity.MainActivity;
import com.example.dmattd.shopdeviceonline.model.CheckStatusUser;
import com.example.dmattd.shopdeviceonline.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {

    Context context;
    ArrayList<Giohang> arrayGiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arrayGiohang) {
        this.context = context;
        this.arrayGiohang = arrayGiohang;
    }


    @Override
    public int getCount() {
        return arrayGiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayGiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttenspgh, txtgiaspgh;
        public ImageView imghinhanhspgh;
        public Button btngiamgh, btntanggh, btnsoluonggh;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
      ViewHolder viewHolder = null;
      if(view == null){
          viewHolder = new ViewHolder();
          LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          view = inflater.inflate(R.layout.dong_giohang, null);
          viewHolder.txttenspgh = view.findViewById(R.id.txttenspdonggiphang);
          viewHolder.txtgiaspgh = view.findViewById(R.id.txtgiaspdonggiohang);
          viewHolder.imghinhanhspgh = view.findViewById(R.id.imghinhanhgiohang);
          viewHolder.btngiamgh = view.findViewById(R.id.btngiam);
          viewHolder.btnsoluonggh = view.findViewById(R.id.btnsoluong);
          viewHolder.btntanggh = view.findViewById(R.id.btntang);
          view.setTag(viewHolder);
      }else {
          viewHolder = (ViewHolder) view.getTag();
      }
      //lay du lieu va gan vao layout
        final Giohang giohang = (Giohang) getItem(i);
        viewHolder.txttenspgh.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiaspgh.setText("Giá: " +decimalFormat.format(giohang.getGiasp()) + "Đ");
        Picasso.with(context).load(giohang.getHinhanhsp())
                .placeholder(R.drawable.delete_button)
                .error(R.drawable.no_image)
                .into(viewHolder.imghinhanhspgh);
        //cast vef dang chuoi
        viewHolder.btnsoluonggh.setText(giohang.getSoluongsp() + "");

        int soluonghang = Integer.parseInt(viewHolder.btnsoluonggh.getText().toString());
        if(soluonghang >=10){
            viewHolder.btntanggh.setVisibility(View.INVISIBLE);
            viewHolder.btngiamgh.setVisibility(view.VISIBLE);
        }else if(soluonghang <1){
            viewHolder.btngiamgh.setVisibility(View.INVISIBLE);
        }else if(soluonghang >= 1){
            viewHolder.btngiamgh.setVisibility(View.VISIBLE);
            viewHolder.btntanggh.setVisibility(View.VISIBLE);
        }

        if(MainActivity.manggiohang.get(i).getSoluongsp() == 1){
            MainActivity.manggiohang.get(i).setTonggia(MainActivity.manggiohang.get(i).getGiasp());
        }

        //set su kien cho nut tang
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btntanggh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnsoluonggh.getText().toString()) +1;
                int slhientai = MainActivity.manggiohang.get(i).getSoluongsp();
                long giasp = MainActivity.manggiohang.get(i).getGiasp();

                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giasp * slmoinhat);
                MainActivity.manggiohang.get(i).setTonggia(giamoinhat);

                Log.d("GIA", "Tên: " +MainActivity.manggiohang.get(i).getTensp()+",Giá: "
                        +MainActivity.manggiohang.get(i).getGiasp()+ ",Số lượng: " +MainActivity.manggiohang.get(i).getSoluongsp()+
                        ",Tổng: "+MainActivity.manggiohang.get(i).getTonggia() + ",gia ms nhat: " + giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//                finalViewHolder.txtgiaspgh.setText("Giá: " +decimalFormat.format(giamoinhat) + "Đ");

                GiohangActivity.EventUtils();
                if(slmoinhat >9 ){
                    finalViewHolder.btntanggh.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiamgh.setVisibility(View.VISIBLE);
                    finalViewHolder.btnsoluonggh.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btntanggh.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiamgh.setVisibility(View.VISIBLE);
                    finalViewHolder.btnsoluonggh.setText(String.valueOf(slmoinhat));

                }

            }
        });


        viewHolder.btngiamgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnsoluonggh.getText().toString()) -1;
                int slhientai = MainActivity.manggiohang.get(i).getSoluongsp();
                final long giasp = MainActivity.manggiohang.get(i).getGiasp();
                    if(slmoinhat>=1){
                        MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                        long giamoinhat = giasp * slmoinhat;
                        MainActivity.manggiohang.get(i).setTonggia(giamoinhat);
                        GiohangActivity.EventUtils();
                        finalViewHolder.btntanggh.setVisibility(View.VISIBLE);
                        finalViewHolder.btngiamgh.setVisibility(View.VISIBLE);
                        finalViewHolder.btnsoluonggh.setText(String.valueOf(slmoinhat));
                    }else {
                        finalViewHolder.btntanggh.setVisibility(View.VISIBLE);
                        finalViewHolder.btngiamgh.setVisibility(View.VISIBLE);

                        finalViewHolder.btngiamgh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                                builder.setTitle("Xác nhận xóa sản phẩm");
                                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này không?");
                                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int j) {
                                        CheckStatusUser.idspgiohangxoa = MainActivity.manggiohang.get(i).getIdsp();
                                        int abc = MainActivity.manggiohang.indexOf(CheckStatusUser.idspgiohangxoa);
                                        Log.wtf("ii", ":" +abc);
                                        MainActivity.manggiohang.remove(i);
                                        Intent intent = new Intent(view.getContext(), GiohangActivity.class);
                                        context.startActivity(intent);

                                    }
                                });


                                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                builder.show();
                            }
                        });

                    }

                //}
            }
        });
        return view;
    }
}
