package com.example.mymoney_final.Fragment.TrangChu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymoney_final.DTO.GiaoDichDTO;
import com.example.mymoney_final.Database.CreateDatabase;
import com.example.mymoney_final.R;

import java.util.List;

public class Adapter_GanDay extends ArrayAdapter<GiaoDichDTO> {
    public Adapter_GanDay(Context context, @NonNull List<GiaoDichDTO> objects) {
        super(context, R.layout.ganday_item, objects);
    }
    private static class ViewHolder {
        TextView TenNhom, Ngay, SoTien, TenVi;
    }
    String tennhom = "";
    String tenvi = "";

    CreateDatabase createDatabase;

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GiaoDichDTO i = getItem(position);
        ViewHolder v;

        if (convertView == null) {
            v = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.ganday_item, parent, false);
            v.TenNhom = convertView.findViewById(R.id.TenNhom);
            v.Ngay = convertView.findViewById(R.id.Ngay);
            v.SoTien = convertView.findViewById(R.id.SoTien);
            v.TenVi = convertView.findViewById(R.id.TenVi);
            convertView.setTag(v);
        }
        else {
            v = (ViewHolder) convertView.getTag();
        }

        createDatabase = new CreateDatabase(getContext());
        Cursor data = createDatabase.GetData("SELECT * FROM NhomGD where MaNhom = " + i.getMaNhom());
        while (data.moveToNext()) {
            tennhom = data.getString(2);
        }
        Cursor data1 = createDatabase.GetData("SELECT * FROM Vi where MaVi = " + i.getMaVi());
        while (data1.moveToNext()) {
            tenvi = data1.getString(2);
        }

        v.TenVi.setText(tenvi);
        v.TenNhom.setText(tennhom);
        v.Ngay.setText(i.getNgay().toString());
        long k = i.getSoTien();

        String t = "";
        if (k > 999 && k <= 999999) {
            t = String.valueOf(k);
            int len = t.length();
            t = t.substring(0,len-3) + "." + t.substring(len-3,len) + " VNĐ";
        }

        else if (k > 999999 && k <= 999999999) {
            t = String.valueOf(k);
            int len = t.length();
            t = t.substring(0,len-6) + "." + t.substring(len-6,len-3) + "." + t.substring(len-3,len) + " VNĐ";
        }
        else {
            t = String.valueOf(k) + " VNĐ";
        }

        if (i.getLoai() == 0) {
            t = "+ " + t;
        }
        else {
            t = "- " + t;
        }

        v.SoTien.setText(t);
        return convertView;
    }
}
