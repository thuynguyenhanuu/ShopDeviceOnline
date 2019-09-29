package com.example.dmattd.shopdeviceonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.model.LoaiSanPham;
import com.example.dmattd.shopdeviceonline.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends BaseAdapter {
    ArrayList<LoaiSanPham> arrayListLoaiSanPham;
    Context context;


    public LoaiSanPhamAdapter(ArrayList<LoaiSanPham> arrayListLoaiSanPham, Context context) {
        this.arrayListLoaiSanPham = arrayListLoaiSanPham;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaiSanPham.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLoaiSanPham.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txtTenLoaiSanPham;
        ImageView imgLoaiSanPham;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_loai_san_pham, null);

            viewHolder.txtTenLoaiSanPham = view.findViewById(R.id.txtTenloaisanpham);
            viewHolder.imgLoaiSanPham = view.findViewById(R.id.imageviewloaisanpham);

            // gan vao trong viewHolder
            view.setTag(viewHolder);
        }else {
            viewHolder = (LoaiSanPhamAdapter.ViewHolder) view.getTag();


        }
        LoaiSanPham loaiSanPham = (LoaiSanPham) getItem(i);
        viewHolder.txtTenLoaiSanPham.setText(loaiSanPham.getTenLoaiSanPham());
        Picasso.with(context).load(loaiSanPham.getHinhanhLoaiSanPham())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.delete_button)
                .into(viewHolder.imgLoaiSanPham);

        return view;
    }
}
