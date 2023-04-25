package com.example.mymoney_final.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.mymoney_final.DTO.GiaoDichDTO;
import com.example.mymoney_final.Database.CreateDatabase;
import com.example.mymoney_final.Fragment.TrangChu.Main;
import com.example.mymoney_final.Fragment.ViCuaToi.CreateWallet;
import com.example.mymoney_final.MainActivity;
import com.example.mymoney_final.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class Report extends Fragment implements OnChartValueSelectedListener {
    private PieChart mChart;
    Spinner ChonVi, ChonThang, ChonLoai;
    MainActivity mainActivity;
    CreateDatabase createDatabase;
    List<String> TenVi = new ArrayList<>();
    List<Float> y = new ArrayList<>();
    List<String> x = new ArrayList<>();
    OnChartValueSelectedListener tuan = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.report,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int maND = mainActivity.getMaND();
        ChonThang = view.findViewById(R.id.ChonThang);
        ChonVi = view.findViewById(R.id.ChonVi);
        ChonLoai = view.findViewById(R.id.ChonLoai);
        createDatabase = new CreateDatabase(getActivity());
        mChart = view.findViewById(R.id.piechart);



        String[] Loai = {"Tất cả", "Khoản chi", "Khoản thu"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                Loai);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.ChonLoai.setAdapter(adapter);

        String[] Thang = {"Tất cả", "Tháng 01", "Tháng 02", "Tháng 03", "Tháng 04", "Tháng 05", "Tháng 06", "Tháng 07", "Tháng 08", "Tháng 09", "Tháng 10", "Tháng 11", "Tháng 12"};
        ArrayAdapter<String> thang = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                Thang);

        thang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.ChonThang.setAdapter(thang);

        TenVi.clear();
        TenVi.add("Tất cả");
        Cursor vi = createDatabase.GetData("SELECT * FROM Vi where MaND = " + maND);
        while (vi.moveToNext()) {
            TenVi.add(vi.getString(2));
        }


        ArrayAdapter<String> wallet = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                TenVi);

        wallet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ChonVi.setAdapter(wallet);

        ChonLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String loai = ChonLoai.getSelectedItem().toString();
                String vi = ChonVi.getSelectedItem().toString();
                String thang_name = ChonThang.getSelectedItem().toString();

                draw(maND,loai,vi,thang_name);
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ChonVi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String loai = ChonLoai.getSelectedItem().toString();
                String vi = ChonVi.getSelectedItem().toString();

                String thang_name = ChonThang.getSelectedItem().toString();
                draw(maND,loai,vi,thang_name);
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ChonThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String loai = ChonLoai.getSelectedItem().toString();
                String vi = ChonVi.getSelectedItem().toString();
                String thang_name = ChonThang.getSelectedItem().toString();

                draw(maND,loai,vi,thang_name);
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void draw(int maND, String loai, String vi, String thang_name) {
        long Sum = 0;
        if (loai.equals("Tất cả")) {
            y.clear();
            x.clear();
            if (vi.equals("Tất cả")) {
                if (thang_name.equals("Tất cả")) {
                    Cursor sum = createDatabase.GetData("SELECT sum(SoTien) FROM GiaoDich where MaND = " + maND);
                    if (sum.moveToNext()) {
                        Sum = sum.getLong(0);
                    }

                    Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " ORDER BY Ngay DESC ");
                    while (data.moveToNext()) {
                        int MaNhom = data.getInt(3);
                        Cursor data1 = createDatabase.GetData("SELECT * FROM NhomGD where MaNhom = " + MaNhom);
                        if (data1.moveToNext()){
                            boolean containsElement = x.contains(data1.getString(2));
                            if (containsElement == false) {
                                x.add(data1.getString(2));
                                long SoTien = data.getLong(5);
                                y.add((float) (SoTien*100.0/Sum));
                            }
                            else {
                                for(int i = 0; i < x.size();i++) {
                                    if (x.get(i).equals(data1.getString(2))) {
                                        double tmp = y.get(i);
                                        tmp = tmp + data.getLong(5)*100.0/Sum;
                                        y.set(i, (float) tmp);
                                    }
                                }
                            }
                        }
                    }
                }


                else {
                    String thang = thang_name.substring(6,8);

                    Cursor sum = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND);

                    while (sum.moveToNext()) {
                        if (sum.getString(7).substring(3,5).equals(thang)) {
                            Sum = Sum + sum.getLong(5);
                        }
                    }

                    Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " ORDER BY Ngay DESC ");
                    while (data.moveToNext()) {
                        if (data.getString(7).substring(3,5).equals(thang)) {
                            int MaNhom = data.getInt(3);
                            Cursor data1 = createDatabase.GetData("SELECT * FROM NhomGD where MaNhom = " + MaNhom);
                            if (data1.moveToNext()) {
                                boolean containsElement = x.contains(data1.getString(2));
                                if (containsElement == false) {
                                    x.add(data1.getString(2));
                                    long SoTien = data.getLong(5);
                                    y.add((float) (SoTien * 100.0 / Sum));
                                } else {
                                    for (int i = 0; i < x.size(); i++) {
                                        if (x.get(i).equals(data1.getString(2))) {
                                            double tmp = y.get(i);
                                            tmp = tmp + data.getLong(5) * 100.0 / Sum;
                                            y.set(i, (float) tmp);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //Ví
            else {
                int mavine = 0;
                Cursor tenvi = createDatabase.GetData("SELECT * FROM Vi where MaND = " + maND );
                while (tenvi.moveToNext()) {
                    if (tenvi.getString(2).equals(vi)) {
                        mavine = tenvi.getInt(0);
                    }
                }


                if (thang_name.equals("Tất cả")) {
                    Cursor sum = createDatabase.GetData("SELECT sum(SoTien) FROM GiaoDich where MaND = " + maND + " and MaVi = " + mavine);
                    if (sum.moveToNext()) {
                        Sum = sum.getLong(0);
                    }

                    Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " and MaVi = " + mavine + " ORDER BY Ngay DESC ");
                    while (data.moveToNext()) {
                        int MaNhom = data.getInt(3);
                        Cursor data1 = createDatabase.GetData("SELECT * FROM NhomGD where MaNhom = " + MaNhom);
                        if (data1.moveToNext()){
                            boolean containsElement = x.contains(data1.getString(2));
                            if (containsElement == false) {
                                x.add(data1.getString(2));
                                long SoTien = data.getLong(5);
                                y.add((float) (SoTien*100.0/Sum));
                            }
                            else {
                                for(int i = 0; i < x.size();i++) {
                                    if (x.get(i).equals(data1.getString(2))) {
                                        double tmp = y.get(i);
                                        tmp = tmp + data.getLong(5)*100.0/Sum;
                                        y.set(i, (float) tmp);
                                    }
                                }
                            }
                        }
                    }
                }


                else {
                    String thang = thang_name.substring(6,8);

                    Cursor sum = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " and MaVi = " + mavine);

                    while (sum.moveToNext()) {
                        if (sum.getString(7).substring(3,5).equals(thang)) {
                            Sum = Sum + sum.getLong(5);
                        }
                    }

                    Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " and MaVi = " + mavine + " ORDER BY Ngay DESC ");
                    while (data.moveToNext()) {
                        if (data.getString(7).substring(3,5).equals(thang)) {
                            int MaNhom = data.getInt(3);
                            Cursor data1 = createDatabase.GetData("SELECT * FROM NhomGD where MaNhom = " + MaNhom);
                            if (data1.moveToNext()) {
                                boolean containsElement = x.contains(data1.getString(2));
                                if (containsElement == false) {
                                    x.add(data1.getString(2));
                                    long SoTien = data.getLong(5);
                                    y.add((float) (SoTien * 100.0 / Sum));
                                } else {
                                    for (int i = 0; i < x.size(); i++) {
                                        if (x.get(i).equals(data1.getString(2))) {
                                            double tmp = y.get(i);
                                            tmp = tmp + data.getLong(5) * 100.0 / Sum;
                                            y.set(i, (float) tmp);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //Loại GD

        else {
            y.clear();
            x.clear();
            int maloai = 0;
            if (loai.equals("Khoản chi")) {
                maloai = 1;
            }
            if (vi.equals("Tất cả")) {
                if (thang_name.equals("Tất cả")) {
                    Cursor sum = createDatabase.GetData("SELECT sum(SoTien) FROM GiaoDich where MaND = " + maND + " and Loai = " + maloai);
                    if (sum.moveToNext()) {
                        Sum = sum.getLong(0);
                    }

                    Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " and Loai = " + maloai + " ORDER BY Ngay DESC ");
                    while (data.moveToNext()) {
                        int MaNhom = data.getInt(3);
                        Cursor data1 = createDatabase.GetData("SELECT * FROM NhomGD where MaNhom = " + MaNhom);
                        if (data1.moveToNext()){
                            boolean containsElement = x.contains(data1.getString(2));
                            if (containsElement == false) {
                                x.add(data1.getString(2));
                                long SoTien = data.getLong(5);
                                y.add((float) (SoTien*100.0/Sum));
                            }
                            else {
                                for(int i = 0; i < x.size();i++) {
                                    if (x.get(i).equals(data1.getString(2))) {
                                        double tmp = y.get(i);
                                        tmp = tmp + data.getLong(5)*100.0/Sum;
                                        y.set(i, (float) tmp);
                                    }
                                }
                            }
                        }
                    }
                }


                else {
                    String thang = thang_name.substring(6,8);

                    Cursor sum = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " and Loai = " + maloai);

                    while (sum.moveToNext()) {
                        if (sum.getString(7).substring(3,5).equals(thang)) {
                            Sum = Sum + sum.getLong(5);
                        }
                    }

                    Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " and Loai = " + maloai + " ORDER BY Ngay DESC ");
                    while (data.moveToNext()) {
                        if (data.getString(7).substring(3,5).equals(thang)) {
                            int MaNhom = data.getInt(3);
                            Cursor data1 = createDatabase.GetData("SELECT * FROM NhomGD where MaNhom = " + MaNhom);
                            if (data1.moveToNext()) {
                                boolean containsElement = x.contains(data1.getString(2));
                                if (containsElement == false) {
                                    x.add(data1.getString(2));
                                    long SoTien = data.getLong(5);
                                    y.add((float) (SoTien * 100.0 / Sum));
                                } else {
                                    for (int i = 0; i < x.size(); i++) {
                                        if (x.get(i).equals(data1.getString(2))) {
                                            double tmp = y.get(i);
                                            tmp = tmp + data.getLong(5) * 100.0 / Sum;
                                            y.set(i, (float) tmp);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //Ví
            else {
                int mavine = 0;
                Cursor tenvi = createDatabase.GetData("SELECT * FROM Vi where MaND = " + maND );
                while (tenvi.moveToNext()) {
                    if (tenvi.getString(2).equals(vi)) {
                        mavine = tenvi.getInt(0);
                    }
                }


                if (thang_name.equals("Tất cả")) {
                    Cursor sum = createDatabase.GetData("SELECT sum(SoTien) FROM GiaoDich where MaND = " + maND + " and MaVi = " + mavine + " and Loai = " + maloai);
                    if (sum.moveToNext()) {
                        Sum = sum.getLong(0);
                    }

                    Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " and MaVi = " + mavine + " and Loai = " + maloai + " ORDER BY Ngay DESC ");
                    while (data.moveToNext()) {
                        int MaNhom = data.getInt(3);
                        Cursor data1 = createDatabase.GetData("SELECT * FROM NhomGD where MaNhom = " + MaNhom);
                        if (data1.moveToNext()){
                            boolean containsElement = x.contains(data1.getString(2));
                            if (containsElement == false) {
                                x.add(data1.getString(2));
                                long SoTien = data.getLong(5);
                                y.add((float) (SoTien*100.0/Sum));
                            }
                            else {
                                for(int i = 0; i < x.size();i++) {
                                    if (x.get(i).equals(data1.getString(2))) {
                                        double tmp = y.get(i);
                                        tmp = tmp + data.getLong(5)*100.0/Sum;
                                        y.set(i, (float) tmp);
                                    }
                                }
                            }
                        }
                    }
                }


                else {
                    String thang = thang_name.substring(6,8);

                    Cursor sum = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " and MaVi = " + mavine + " and Loai = " + maloai);

                    while (sum.moveToNext()) {
                        if (sum.getString(7).substring(3,5).equals(thang)) {
                            Sum = Sum + sum.getLong(5);
                        }
                    }

                    Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " and MaVi = " + mavine + " and Loai = " + maloai + " ORDER BY Ngay DESC ");
                    while (data.moveToNext()) {
                        if (data.getString(7).substring(3,5).equals(thang)) {
                            int MaNhom = data.getInt(3);
                            Cursor data1 = createDatabase.GetData("SELECT * FROM NhomGD where MaNhom = " + MaNhom);
                            if (data1.moveToNext()) {
                                boolean containsElement = x.contains(data1.getString(2));
                                if (containsElement == false) {
                                    x.add(data1.getString(2));
                                    long SoTien = data.getLong(5);
                                    y.add((float) (SoTien * 100.0 / Sum));
                                } else {
                                    for (int i = 0; i < x.size(); i++) {
                                        if (x.get(i).equals(data1.getString(2))) {
                                            double tmp = y.get(i);
                                            tmp = tmp + data.getLong(5) * 100.0 / Sum;
                                            y.set(i, (float) tmp);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Float sort_value;
        String sort_name;
        for(int i = 0; i < y.size(); i++)
        {
            for(int j = i; j > 0; j--)
            {
                if (y.get(j) > y.get(j-1))
                {
                    sort_value = y.get(j);
                    y.set(j,y.get(j-1));
                    y.set(j-1,sort_value);

                    sort_name = x.get(j);
                    x.set(j,x.get(j-1));
                    x.set(j-1,sort_name);
                }
            }
        }

        mChart.setRotationEnabled(true);
        mChart.setDescription(null);
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterTextSize(16);
        mChart.setDrawEntryLabels(true);

        List<Float> y_final = new ArrayList<>();
        List<String> x_final = new ArrayList<>();

        if (y.size()>5) {
            float other = 0;
            for(int i = 0; i <= 4; i++)
            {
                y_final.add(y.get(i));
                x_final.add(x.get(i));
            }
            for(int i = 5; i < y.size(); i++)
            {
                other = other + y.get(i);
            }
            y_final.add(other);
            x_final.add("Giao dịch khác");
            addDataSet(mChart, y_final, x_final);
            mChart.setOnChartValueSelectedListener(tuan);
        }

        else {
            addDataSet(mChart, y, x);
            mChart.setOnChartValueSelectedListener(tuan);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        inflater.inflate(R.menu.back,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.back) {
            changeFrament(new Main());
            mainActivity.toolbar.setTitle("Trang chủ");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    private static void addDataSet(PieChart pieChart, List<Float> y,List<String> x) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<PieEntry> yEntrys_ = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();


        for (int i = 0; i < y.size();i++){
            yEntrys.add(new PieEntry(y.get(i),x.get(i)));
            yEntrys_.add(new PieEntry(y.get(i),x.get(i)));
        }
        for (int i = 0; i < x.size();i++){
            xEntrys.add(x.get(i));
        }


        PieDataSet pieDataSet = new PieDataSet(yEntrys_,"");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setXValuePosition(null);


        ArrayList<Integer> colors = new ArrayList<>();



        colors.add(ColorTemplate.MATERIAL_COLORS[2]);
        colors.add(ColorTemplate.JOYFUL_COLORS[1]);
        colors.add(ColorTemplate.MATERIAL_COLORS[1]);
        colors.add(ColorTemplate.MATERIAL_COLORS[0]);
        colors.add(ColorTemplate.MATERIAL_COLORS[3]);
        colors.add(Color.BLUE);

        for (int i = 0; i < ColorTemplate.MATERIAL_COLORS.length; i++) {
            colors.add(ColorTemplate.MATERIAL_COLORS[i]);
        }

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        legend.setTextSize(14);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.animateXY(800,100);
    }

    @Override
    public void onNothingSelected() {

    }

    private void changeFrament(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }
}
