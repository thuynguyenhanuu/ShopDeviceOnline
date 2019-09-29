package com.example.dmattd.shopdeviceonline.model;

import java.io.Serializable;

public class Dangky implements Serializable {
    public String ten;
    public String sdt;
    public String diachi;
    public String matkhau;
    public String xacnhanmk;

    public Dangky(String ten,String sdt, String diachi, String matkhau, String xacnhanmk) {
        this.ten = ten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.matkhau = matkhau;
        this.xacnhanmk = xacnhanmk;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getXacnhanmk() {
        return xacnhanmk;
    }

    public void setXacnhanmk(String xacnhanmk) {
        this.xacnhanmk = xacnhanmk;
    }
}
