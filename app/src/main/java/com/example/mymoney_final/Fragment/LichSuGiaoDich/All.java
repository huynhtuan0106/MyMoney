package com.example.mymoney_final.Fragment.LichSuGiaoDich;


import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymoney_final.DTO.GiaoDichDTO;
import com.example.mymoney_final.Database.CreateDatabase;
import com.example.mymoney_final.MainActivity;
import com.example.mymoney_final.R;

import java.util.ArrayList;
import java.util.List;

public class All extends Fragment {
    static ListView list;
    static List<GiaoDichDTO> items = new ArrayList<>();
    static Adapter_LSGD adapter;
    CreateDatabase createDatabase;
    MainActivity mainActivity;
    TextView LoaiGD, LoaiVi, LoaiNhom, SoTien, Ngay, GhiChu;
    Button btnDong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.viewpaper_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        items.clear();
        list = view.findViewById(R.id.list);
        int MaND = mainActivity.getMaND();
        adapter = new Adapter_LSGD(getActivity(),items);
        list.setAdapter(adapter);
        createDatabase = new CreateDatabase(getActivity());

        Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + MaND + " ORDER BY Ngay DESC ");
        while (data.moveToNext()) {
            int MaGD = data.getInt(0);
            int MaVi = data.getInt(2);
            int MaNhom = data.getInt(3);
            int Loai = data.getInt(4);
            long SoTien = data.getLong(5);
            String GhiChu = data.getString(6);
            String Ngay = data.getString(7);

            items.add(new GiaoDichDTO(MaGD,MaND,MaVi,MaNhom,Loai,SoTien,GhiChu,Ngay));
        }
        adapter.notifyDataSetChanged();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainActivity.setMaGDClick(items.get(position).getMaGD());
                ChiTietGD_Dialog(Gravity.CENTER);
            }
        });
    }

    public void ChiTietGD_Dialog (int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_gd);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);


        int loaigd = 0, mavi = 0, manhom = 0;
        LoaiGD = dialog.findViewById(R.id.LoaiGD);
        LoaiVi = dialog.findViewById(R.id.LoaiVi);
        LoaiNhom = dialog.findViewById(R.id.LoaiNhom);
        btnDong = dialog.findViewById(R.id.btnDong);
        SoTien = dialog.findViewById(R.id.SoTien);
        Ngay = dialog.findViewById(R.id.Ngay);
        GhiChu = dialog.findViewById(R.id.GhiChu);
        long k = 0;

        int MaGD = mainActivity.getMaGDClick();

        Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaGD = " + MaGD);
        while (data.moveToNext()) {
            loaigd = data.getInt(4);
            manhom = data.getInt(3);
            mavi = data.getInt(2);
            k = data.getLong(5);
            GhiChu.setText(data.getString(6));
            Ngay.setText(data.getString(7));
        }

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


        SoTien.setText(t);

        Cursor data1 = createDatabase.GetData("SELECT * FROM Vi where MaVi = " + mavi);
        while (data1.moveToNext()) {
            LoaiVi.setText(data1.getString(2));
        }

        Cursor data2 = createDatabase.GetData("SELECT * FROM NhomGD where MaNhom = " + manhom);
        while (data2.moveToNext()) {
            LoaiNhom.setText(data2.getString(2));
        }

        if (loaigd == 0) {
            LoaiGD.setText("Khoản thu");
        }
        else {
            LoaiGD.setText("Khoản chi");
        }


        btnDong.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                dialog.dismiss();
            }

        });

        dialog.show();
    }
}
