package com.example.dmattd.shopdeviceonline.model;

public class Dangnhap {
    public String sdtdangnhap;
    public String matkhaudnhap;

    public Dangnhap(String sdtdangnhap, String matkhaudnhap) {
        this.sdtdangnhap = sdtdangnhap;
        this.matkhaudnhap = matkhaudnhap;
    }

    public String getSdtdangnhap() {
        return sdtdangnhap;
    }

    public void setSdtdangnhap(String sdtdangnhap) {
        this.sdtdangnhap = sdtdangnhap;
    }

    public String getMatkhaudnhap() {
        return matkhaudnhap;
    }

    public void setMatkhaudnhap(String matkhaudnhap) {
        this.matkhaudnhap = matkhaudnhap;
    }
}
