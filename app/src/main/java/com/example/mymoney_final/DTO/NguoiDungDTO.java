package com.example.mymoney_final.DTO;

public class NguoiDungDTO {
    private int MaND;
    private String TenND;
    private String Username;
    private String Password;
    private String SDT;

    public NguoiDungDTO(int maND, String tenND, String username, String password, String SDT) {
        this.MaND = maND;
        this.TenND = tenND;
        this.Username = username;
        this.Password = password;
        this.SDT = SDT;
    }

    public int getMaND() {
        return MaND;
    }

    public void setMaND(int maND) {
        MaND = maND;
    }

    public String getTenND() {
        return TenND;
    }

    public void setTenND(String tenND) {
        TenND = tenND;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}
