package com.example.dmattd.shopdeviceonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dmattd.shopdeviceonline.R;
import com.example.dmattd.shopdeviceonline.activity.ChitietSanpham;
import com.example.dmattd.shopdeviceonline.model.Sanpham;
import com.example.dmattd.shopdeviceonline.util.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

// Sản phẩm mới nhất
public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {

    Context context;
    ArrayList<Sanpham> arraySanpham;


    public SanphamAdapter(Context context, ArrayList<Sanpham> arraySanpham) {
        this.context = context;
        this.arraySanpham = arraySanpham;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_sanphammoinhat, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        Sanpham sanpham = arraySanpham.get(i);
        itemHolder.txtTensp.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        itemHolder.txtgiasp.setText("Giá: "+decimalFormat.format(sanpham.getGiasp()) + "Đ");
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.delete_button)
                .error(R.drawable.no_image)
                .into(itemHolder.imgHinhsp);
    }

    @Override
    public int getItemCount() {
        return arraySanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhsp;
        public TextView txtTensp, txtgiasp;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhsp = itemView.findViewById(R.id.imageviewsp);
            txtTensp = itemView.findViewById(R.id.textviewTensp);
            txtgiasp = itemView.findViewById(R.id.textviewGiasp);

            //bat su kien cho sp moi nhat
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChitietSanpham.class);
                    intent.putExtra("thongtinsp", arraySanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   // CheckConnection.ShowToast_short(context, arraySanpham.get(getPosition()).getTensp());
                    context.startActivity(intent);

                }
            });

        }
    }
}
