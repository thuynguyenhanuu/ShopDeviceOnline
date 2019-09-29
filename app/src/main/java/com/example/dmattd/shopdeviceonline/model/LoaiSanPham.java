package com.example.dmattd.shopdeviceonline.model;

public class LoaiSanPham {
    public int IdLoaiSanPham;
    public String TenLoaiSanPham;
    public String HinhanhLoaiSanPham;

    public LoaiSanPham(int idLoaiSanPham, String tenLoaiSanPham, String hinhanhLoaiSanPham) {
        IdLoaiSanPham = idLoaiSanPham;
        TenLoaiSanPham = tenLoaiSanPham;
        HinhanhLoaiSanPham = hinhanhLoaiSanPham;
    }

    public int getIdLoaiSanPham() {
        return IdLoaiSanPham;
    }

    public void setIdLoaiSanPham(int idLoaiSanPham) {
        IdLoaiSanPham = idLoaiSanPham;
    }

    public String getTenLoaiSanPham() {
        return TenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        TenLoaiSanPham = tenLoaiSanPham;
    }

    public String getHinhanhLoaiSanPham() {
        return HinhanhLoaiSanPham;
    }

    public void setHinhanhLoaiSanPham(String hinhanhLoaiSanPham) {
        HinhanhLoaiSanPham = hinhanhLoaiSanPham;
    }
}
