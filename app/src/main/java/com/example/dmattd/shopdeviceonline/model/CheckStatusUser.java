package com.example.dmattd.shopdeviceonline.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckStatusUser implements Serializable {

    public static boolean isLogin;

    public static int idNgdung;

    public static ArrayList<LoaiSanPham> arrayListLoaiSanPham = new ArrayList<>();

    public static int idLoaiSanPham;
    public static String tenLoaiSanPham;

    public static  String ten, sdt, diachi, matkhau;

    public static String idsanpham;

    public static String diachigiaohang, hinhthucvanchuyen, hinhthucthanhtoan;

    // xoa sp gio hang

    public static int idspgiohangxoa;

    //id don hang;
    public static int idDonhang;
}
