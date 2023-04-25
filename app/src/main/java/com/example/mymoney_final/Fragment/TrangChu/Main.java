package com.example.mymoney_final.Fragment.TrangChu;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymoney_final.DTO.GiaoDichDTO;
import com.example.mymoney_final.Database.CreateDatabase;
import com.example.mymoney_final.Fragment.LichSuGiaoDich.History;
import com.example.mymoney_final.Fragment.Report;
import com.example.mymoney_final.Fragment.ViCuaToi.MyWallet;
import com.example.mymoney_final.MainActivity;
import com.example.mymoney_final.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main extends Fragment implements OnChartValueSelectedListener {
    CreateDatabase createDatabase;
    PieChart mChart;
    TextView TongSoDu;
    ListView lv_giaodich;
    MainActivity mainActivity;
    LinearLayout Sum,GanDay,BaoCao;
    static List<GiaoDichDTO> items = new ArrayList<>();
    static Adapter_GanDay adapter;
    List<Long> y = new ArrayList<>();
    List<String> x = new ArrayList<>();
    OnChartValueSelectedListener tuan = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.main,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int MaND = mainActivity.getMaND();
        TongSoDu = view.findViewById(R.id.TongSoDu);
        Sum = view.findViewById(R.id.Sum);
        GanDay = view.findViewById(R.id.GanDay);
        BaoCao = view.findViewById(R.id.BaoCao);
        lv_giaodich = view.findViewById(R.id.lv_giaodich);
        mChart = view.findViewById(R.id.piechart);

        createDatabase = new CreateDatabase(getActivity());

        TongSoDu(MaND);
        GDGanDay(MaND);
        draw(MaND);

        Sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrament(new MyWallet());
                mainActivity.toolbar.setTitle("Ví của tôi");
            }
        });

        GanDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrament(new History());
                mainActivity.toolbar.setTitle("Lịch sử giao dịch");
                mainActivity.bottom_NavigationView.getMenu().findItem(R.id.history).setChecked(true);
            }
        });

        BaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrament(new Report());
                mainActivity.toolbar.setTitle("Báo cáo");
            }
        });
    }

    private void TongSoDu(int MaND){
        Cursor data = createDatabase.GetData("SELECT (sum(SoDu)) as Tong FROM Vi where MaND = " + MaND + " and TrangThai = 1");
        if (data.moveToNext()) {
            long k = data.getInt(0);
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

            TongSoDu.setText(String.valueOf(t));
        }
    }

    private void GDGanDay(int MaND) {
        items.clear();
        int i = 0;

        adapter = new Adapter_GanDay(getActivity(),items);

        lv_giaodich.setAdapter(adapter);

        createDatabase = new CreateDatabase(getActivity());

        Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + MaND + " ORDER BY MaGD DESC ");
        while (data.moveToNext() && i <=2) {
            int MaGD = data.getInt(0);
            int MaVi = data.getInt(2);
            int MaNhom = data.getInt(3);
            int Loai = data.getInt(4);
            long SoTien = data.getLong(5);
            String GhiChu = data.getString(6);
            String Ngay = data.getString(7);
            i++;
            items.add(new GiaoDichDTO(MaGD,MaND,MaVi,MaNhom,Loai,SoTien,GhiChu,Ngay));
        }
        adapter.notifyDataSetChanged();
    }

    private void changeFrament(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }

    public void draw(int maND) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        String strDate = formatter.format(date);

        y.add(0L);
        y.add(0L);
        x.add("Tổng thu");
        x.add("Tổng chi");


        Cursor data = createDatabase.GetData("SELECT * FROM GiaoDich where MaND = " + maND + " ORDER BY Ngay DESC ");
        while (data.moveToNext()) {
            String thangne = data.getString(7).substring(3,5);
            if (thangne.equals(strDate)) {
                int maloai = data.getInt(4);
                if (maloai == 0) {
                    Long tmp = y.get(0);
                    tmp = tmp + data.getLong(5);
                    y.set(0, tmp);
                }
                else {
                    Long tmp = y.get(1);
                    tmp = tmp + data.getLong(5);
                    y.set(1, tmp);
                }
            }
        }

        for (int i = 0; i < y.size(); i++) {
            if (y.get(i) == 0) {
                y.remove(i);
                x.remove(i);
                i = i - 1;
            }
        }


        mChart.setRotationEnabled(true);
        mChart.setDescription(null);
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterTextSize(16);
        mChart.setDrawEntryLabels(true);

        addDataSet(mChart, y, x);
        mChart.setOnChartValueSelectedListener(tuan);
    }

    private static void addDataSet(PieChart pieChart, List<Long> y,List<String> x) {
        ArrayList<PieEntry> yEntrys_ = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();


        for (int i = 0; i < y.size();i++){
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

        for (int i = 1; i < ColorTemplate.COLORFUL_COLORS.length; i++) {
            colors.add(ColorTemplate.COLORFUL_COLORS[i]);
        }


        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        legend.setTextSize(14);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.animateXY(800,100);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
