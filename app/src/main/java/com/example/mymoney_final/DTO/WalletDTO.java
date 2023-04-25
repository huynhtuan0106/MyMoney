package com.example.mymoney_final.DTO;

public class WalletDTO {
    private String name;
    private int MaVi;
    private int MaND;
    private long balance;
    private int status;

    public WalletDTO(int maVi, int maND, String name, long balance, int status) {
        this.name = name;
        MaVi = maVi;
        MaND = maND;
        this.balance = balance;
        this.status = status;
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

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
