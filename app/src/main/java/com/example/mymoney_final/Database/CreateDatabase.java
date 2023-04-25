package com.example.mymoney_final.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {

    public CreateDatabase(@Nullable Context context) {
        super(context, "MyMoney", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String NguoiDung = "CREATE TABLE IF NOT EXISTS NguoiDung (MaND INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenND VARCHAR(200), Username VARCHAR(200), Password VARCHAR(200), SDT VARCHAR(15))";

        String Vi = "CREATE TABLE IF NOT EXISTS Vi (MaVi INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MaND INTEGER, TenVi VARCHAR(200), SoDu LONG, TrangThai INT)";

        String NhomGD = "CREATE TABLE IF NOT EXISTS NhomGD (MaNhom INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Loai INTEGER, TenNhom VARCHAR(200))";

        String GiaoDich = "CREATE TABLE IF NOT EXISTS GiaoDich (MaGD INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MaND INTEGER, MaVi INTEGER, MaNhom INTEGER, Loai INTEGER, SoTien LONG, GhiChu VARCHAR(500), Ngay Datetime)";


        sqLiteDatabase.execSQL(NguoiDung);
        sqLiteDatabase.execSQL(Vi);
        sqLiteDatabase.execSQL(NhomGD);
        sqLiteDatabase.execSQL(GiaoDich);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase Open() {
        return this.getWritableDatabase();
    }

    //Truy vấn ko trả kq
    public  void QueryData (String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //Truy vấn có trả kq
    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}
