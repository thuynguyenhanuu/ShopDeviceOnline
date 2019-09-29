package com.example.dmattd.shopdeviceonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.activity.MainActivity;
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
        Giohang giohang = (Giohang) getItem(i);
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
        }else if(soluonghang <=1){
            viewHolder.btngiamgh.setVisibility(View.INVISIBLE);
        }else if(soluonghang >= 1){
            viewHolder.btngiamgh.setVisibility(View.VISIBLE);
            viewHolder.btntanggh.setVisibility(View.VISIBLE);
        }

        //set su kien cho nut tang
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btntanggh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnsoluonggh.getText().toString()) +1;
                int slhientai = MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(i).getGiasp();

                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat)/slhientai;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiaspgh.setText("Giá: " +decimalFormat.format(giamoinhat) + "Đ");

                com.example.dmattd.shopdeviceonline.activity.Giohang.EventUtils();
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
                long giaht = MainActivity.manggiohang.get(i).getGiasp();

                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat)/slhientai;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiaspgh.setText("Giá: " +decimalFormat.format(giamoinhat) + "Đ");

                com.example.dmattd.shopdeviceonline.activity.Giohang.EventUtils();
                if(slmoinhat <2 ){
                    finalViewHolder.btntanggh.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiamgh.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnsoluonggh.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btntanggh.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiamgh.setVisibility(View.VISIBLE);
                    finalViewHolder.btnsoluonggh.setText(String.valueOf(slmoinhat));

                }
            }
        });
        return view;
    }
}
