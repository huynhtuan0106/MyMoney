package com.example.mymoney_final.DTO;

import java.time.DateTimeException;

public class GiaoDichDTO {
    private int MaGD;
    private int MaND;
    private int MaVi;
    private int MaNhom;
    private int Loai;
    private long SoTien;
    private String GhiChu;
    private String Ngay;

    public GiaoDichDTO(int maGD, int maND, int maVi, int maNhom, int loai, long soTien, String ghiChu, String ngay) {
        MaGD = maGD;
        MaND = maND;
        MaVi = maVi;
        MaNhom = maNhom;
        Loai = loai;
        SoTien = soTien;
        GhiChu = ghiChu;
        Ngay = ngay;
    }

    public int getMaGD() {
        return MaGD;
    }

    public void setMaGD(int maGD) {
        MaGD = maGD;
    }

    public int getMaVi() {
        return MaVi;
    }

    public void setMaVi(int maVi) {
        MaVi = maVi;
    }

    public int getMaND() {
        return MaND;
    }

    public void setMaND(int maND) {
        MaND = maND;
    }

    public int getMaNhom() {
        return MaNhom;
    }

    public void setMaNhom(int maNhom) {
        MaNhom = maNhom;
    }

    public int getLoai() {
        return Loai;
    }

    public void setLoai(int loai) {
        Loai = loai;
    }

    public long getSoTien() {
        return SoTien;
    }

    public void setSoTien(long soTien) {
        SoTien = soTien;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }
}
