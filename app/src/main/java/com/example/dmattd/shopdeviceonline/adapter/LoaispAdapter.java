package com.example.dmattd.shopdeviceonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> arrayListLoaisp;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> arrayListLoaisp, Context context) {
        this.arrayListLoaisp = arrayListLoaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLoaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txtTenloaisp;
        ImageView imgLoaisp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_menu_left, null);

            viewHolder.txtTenloaisp = view.findViewById(R.id.textviewLoaisp);
            viewHolder.imgLoaisp = view.findViewById(R.id.imageviewLoaisp);

            // gan vao trong viewHolder
            view.setTag(viewHolder);
       }else {
            viewHolder = (ViewHolder) view.getTag();


        }
        Loaisp loaisp = (Loaisp) getItem(i);
        viewHolder.txtTenloaisp.setText(loaisp.getTenloaisp());
//        Log.d("MMM", "getView: " + loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.delete_button)
                .into(viewHolder.imgLoaisp);

        return view;
    }
}
