package com.example.mymoney_final.Fragment.GiaoDich;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymoney_final.Database.CreateDatabase;
import com.example.mymoney_final.Fragment.LichSuGiaoDich.History;
import com.example.mymoney_final.MainActivity;
import com.example.mymoney_final.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Add extends Fragment {
    CreateDatabase createDatabase;
    Spinner LoaiGD, LoaiVi, LoaiNhom;
    EditText SoTien, Ngay, GhiChu;
    Button btnThem;
    MainActivity mainActivity;
    List<String> TenVi = new ArrayList<>();
    List<String> TenNhom = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.add,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createDatabase = new CreateDatabase(getActivity());
        String[] Loai = {"Khoản chi", "Khoản thu"};

        int MaND = mainActivity.getMaND();
        LoaiGD = view.findViewById(R.id.LoaiGD);
        LoaiVi = view.findViewById(R.id.LoaiVi);
        LoaiNhom = view.findViewById(R.id.LoaiNhom);
        btnThem = view.findViewById(R.id.btnThem);
        SoTien = view.findViewById(R.id.SoTien);
        Ngay = view.findViewById(R.id.Ngay);
        GhiChu = view.findViewById(R.id.GhiChu);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);

        Ngay.setText(strDate);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                Loai);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.LoaiGD.setAdapter(adapter);

        LoaiGD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = LoaiGD.getSelectedItem().toString();
                if (tmp.equals("Khoản thu")) {
                    TenNhom.clear();
                    Cursor data1 = createDatabase.GetData("SELECT * FROM NhomGD where Loai = 0");
                    while (data1.moveToNext()) {
                        TenNhom.add(data1.getString(2));
                        ArrayAdapter<String> nhom = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item,
                                TenNhom);

                        nhom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        LoaiNhom.setAdapter(nhom);
                    }
                }
                else if (tmp.equals("Khoản chi")){
                    TenNhom.clear();
                    Cursor data1 = createDatabase.GetData("SELECT * FROM NhomGD where Loai = 1");
                    while (data1.moveToNext()) {
                        TenNhom.add(data1.getString(2));
                        ArrayAdapter<String> nhom = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item,
                                TenNhom);

                        nhom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        LoaiNhom.setAdapter(nhom);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Cursor data = createDatabase.GetData("SELECT * FROM Vi where MaND = " + MaND);
        while (data.moveToNext()) {
            TenVi.add(data.getString(2));
        }


        ArrayAdapter<String> wallet = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                TenVi);

        wallet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        LoaiVi.setAdapter(wallet);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SoTien.getText().toString().equals("") || Ngay.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                }
                else  {
                    Cursor dataMaVi = createDatabase.GetData("SELECT * FROM Vi where MaND = " + MaND +
                            " and TenVi = '" + LoaiVi.getSelectedItem().toString() + "'");
                    dataMaVi.moveToNext();
                    int MaVi = dataMaVi.getInt(0);
                    long SoDu = dataMaVi.getInt(3);
                    Cursor dataNhom = createDatabase.GetData("SELECT * FROM NhomGD where TenNhom = '" +
                            LoaiNhom.getSelectedItem().toString() + "'");
                    dataNhom.moveToNext();
                    int MaNhom = dataNhom.getInt(0);
                    int LoaiNhom = dataNhom.getInt(1);
                    long sotien = Integer.parseInt(SoTien.getText().toString());
                    long ConLai = 0;
                    if (LoaiNhom == 0) {
                        ConLai =  SoDu + sotien;
                    }
                    else {
                        ConLai =  SoDu - sotien;
                    }

                    createDatabase.QueryData("INSERT INTO GiaoDich values (null, " + MaND + ","+ MaVi + "," + MaNhom + ","
                            + LoaiNhom + ",'" + Integer.parseInt(SoTien.getText().toString()) + "','"
                            + GhiChu.getText().toString() + "', '" + Ngay.getText().toString() + "')");

                    createDatabase.QueryData("UPDATE Vi SET SoDu = '" + ConLai  + "' where MaVi = " + MaVi + " and MaND = " + MaND );
                    Toast.makeText(getActivity(), "Thêm giao dịch mới thành công", Toast.LENGTH_SHORT).show();
                    mainActivity.bottom_NavigationView.getMenu().findItem(R.id.history).setChecked(true);
                    changeFrament(new History());
                    mainActivity.toolbar.setTitle("Lịch sử giao dịch");

                }
            }
        });

    }

    private void changeFrament(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }
}
