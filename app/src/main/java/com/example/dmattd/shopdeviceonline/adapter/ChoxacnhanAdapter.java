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
import com.example.dmattd.shopdeviceonline.model.Choxacnhan;
import com.example.dmattd.shopdeviceonline.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChoxacnhanAdapter extends BaseAdapter {


    Context context;
    ArrayList<Choxacnhan> arrayChoxacnhan;

    public ChoxacnhanAdapter(Context context, ArrayList<Choxacnhan> arrayChoxacnhan) {
        this.context = context;
        this.arrayChoxacnhan = arrayChoxacnhan;
    }

    @Override
    public int getCount() {
        return arrayChoxacnhan.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayChoxacnhan.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttensp, txtgiasp, txtsoluong;
        public ImageView imghinhanh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_choxacnhandon, null);
            viewHolder.txttensp = view.findViewById(R.id.tensp1);
            viewHolder.txtgiasp = view.findViewById(R.id.giasp1);
            viewHolder.imghinhanh = view.findViewById(R.id.hinhanhsp1);
            viewHolder.txtsoluong = view.findViewById(R.id.soluongsp1);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //lay du lieu va gan vao layout
        Choxacnhan choxacnhan = (Choxacnhan) getItem(i);
        viewHolder.txttensp.setText(choxacnhan.getTenspcxn());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiasp.setText("Giá: " +decimalFormat.format(choxacnhan.getGiaspcxn()) + "Đ");
        Picasso.with(context).load(choxacnhan.getHinhanhspcxn())
                .placeholder(R.drawable.delete_button)
                .error(R.drawable.no_image)
                .into(viewHolder.imghinhanh);
        //cast vef dang chuoi
        viewHolder.txtsoluong.setText(choxacnhan.getSoluongspcxn() + "");
        return view;
    }
}
