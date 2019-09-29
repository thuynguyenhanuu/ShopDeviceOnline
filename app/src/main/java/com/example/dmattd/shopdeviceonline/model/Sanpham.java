package com.example.dmattd.shopdeviceonline.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int Id;
    public String Tensp;
    public Integer Giasp;
    public String Hinhanhsp;
    public String Motasp;
    public int Id_loaisp;

    public Sanpham(int id, String tensp, Integer giasp, String hinhanhsp, String motasp, int id_loaisp) {
        Id = id;
        Tensp = tensp;
        Giasp = giasp;
        Hinhanhsp = hinhanhsp;
        Motasp = motasp;
        Id_loaisp = id_loaisp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public Integer getGiasp() {
        return Giasp;
    }

    public void setGiasp(Integer giasp) {
        Giasp = giasp;
    }

    public String getHinhanhsp() {
        return Hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        Hinhanhsp = hinhanhsp;
    }

    public String getMotasp() {
        return Motasp;
    }

    public void setMotasp(String motasp) {
        Motasp = motasp;
    }

    public int getId_loaisp() {
        return Id_loaisp;
    }

    public void setId_loaisp(int id_loaisp) {
        Id_loaisp = id_loaisp;
    }
}
