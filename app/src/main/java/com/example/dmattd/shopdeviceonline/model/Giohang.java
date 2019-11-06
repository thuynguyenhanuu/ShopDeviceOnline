package com.example.dmattd.shopdeviceonline.model;

public class Giohang {
    public int idsp;
    public String tensp;
    public long giasp;
    public String hinhanhsp;
    public int soluongsp;
    public long tonggia;

//    public GiohangActivity(int idsp, String tensp, long giasp, String hinhanhsp, int soluongsp) {
//        this.idsp = idsp;
//        this.tensp = tensp;
//        this.giasp = giasp;
//        this.hinhanhsp = hinhanhsp;
//        this.soluongsp = soluongsp;
//    }

    public Giohang(int idsp, String tensp, long giasp, String hinhanhsp, int soluongsp, long tonggia) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanhsp = hinhanhsp;
        this.soluongsp = soluongsp;
        this.tonggia = tonggia;
    }

    public long getTonggia() {
        return tonggia;
    }

    public void setTonggia(long tonggia) {
        this.tonggia = tonggia;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }
}

