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

// Sản phẩm nói chung
public class Sanpham1Adapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayListSanpham1;

    public Sanpham1Adapter(Context context, ArrayList<Sanpham> arrayListSanpham1) {
        this.context = context;
        this.arrayListSanpham1 = arrayListSanpham1;
    }

    @Override
    public int getCount() {
        return arrayListSanpham1.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListSanpham1.get(i);
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
       ViewHolder viewHolder = null;
       if(view == null){
           viewHolder = new ViewHolder();
           LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = layoutInflater.inflate(R.layout.dong_sanpham1, null);
           viewHolder.txtxtensp = view.findViewById(R.id.txtTensp1);
           viewHolder.txtgiasp = view.findViewById(R.id.txtGiasp1);
           viewHolder.txtmotasp = view.findViewById(R.id.txtMotasp1);
           viewHolder.imgsp = view.findViewById(R.id.imageviewsp1);
           view.setTag(viewHolder);
       }else {
           viewHolder = (ViewHolder) view.getTag();
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
