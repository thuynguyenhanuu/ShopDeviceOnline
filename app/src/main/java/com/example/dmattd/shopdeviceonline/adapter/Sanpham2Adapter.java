package com.example.dmattd.shopdeviceonline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Sanpham2Adapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayListSanpham2;

    public Sanpham2Adapter(Context context, ArrayList<Sanpham> arrayListSanpham2) {
        this.context = context;
        this.arrayListSanpham2 = arrayListSanpham2;
    }

    @Override
    public int getCount() {
        return arrayListSanpham2.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListSanpham2.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txtxtensp, txtgiasp, txtmotasp;
        public ImageView imgsp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Sanpham2Adapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new Sanpham2Adapter.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dong_sanpham2, null);
            viewHolder.txtxtensp = view.findViewById(R.id.txtTensp2);
            viewHolder.txtgiasp = view.findViewById(R.id.txtGiasp2);
            viewHolder.txtmotasp = view.findViewById(R.id.txtMotasp2);
            viewHolder.imgsp = view.findViewById(R.id.imageviewsp2);
            view.setTag(viewHolder);
        }else {
            viewHolder = (Sanpham2Adapter.ViewHolder) view.getTag();
        }

        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txtxtensp.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiasp.setText("Giá: " +decimalFormat.format(sanpham.getGiasp()) + "Đ");
        // set mo ta gom co 2 dong
        viewHolder.txtmotasp.setMaxLines(2);
        viewHolder.txtmotasp.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotasp.setText(sanpham.getMotasp());

        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.delete_button)
                .error(R.drawable.no_image)
                .into(viewHolder.imgsp);

        return view;
    }
}
