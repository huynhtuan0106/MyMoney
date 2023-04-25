package com.example.mymoney_final.Fragment.ViCuaToi;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymoney_final.Database.CreateDatabase;
import com.example.mymoney_final.DTO.WalletDTO;
import com.example.mymoney_final.Fragment.TrangChu.Main;
import com.example.mymoney_final.MainActivity;
import com.example.mymoney_final.R;

import java.util.ArrayList;
import java.util.List;

public class MyWallet extends Fragment {
    static ListView walletList;
    static List<WalletDTO> items = new ArrayList<>();
    static Adapter_wallet adapter;
    CreateDatabase createDatabase;
    MainActivity mainActivity;
    private View mView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.mywallet,container,false);
        mainActivity = (MainActivity) getActivity();
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        inflater.inflate(R.menu.menu_mywallet,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addwallet) {
            changeFrament(new CreateWallet());
            mainActivity.toolbar.setTitle("Thêm ví mới");
        }
        if (id == R.id.back) {
            changeFrament(new Main());
            mainActivity.toolbar.setTitle("Trang chủ");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        items.clear();
        walletList = view.findViewById(R.id.walletList);
        int MaND = mainActivity.getMaND();
        adapter = new Adapter_wallet(getActivity(),items);
        walletList.setAdapter(adapter);
        createDatabase = new CreateDatabase(getActivity());

        Cursor data = createDatabase.GetData("SELECT * FROM Vi where MaND = " + MaND);
        while (data.moveToNext()) {
            int MaVi = data.getInt(0);
            String TenVi = data.getString(2);
            String SoDu = data.getString(3);
            int TrangThai = data.getInt(4);
            if (TrangThai == 0) {
                TenVi = TenVi + " (Tạm khoá)";
            }
            items.add(new WalletDTO(MaVi,MaND,TenVi,Long.valueOf(SoDu),TrangThai));
        }
        adapter.notifyDataSetChanged();

        walletList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainActivity.setMaViClick(items.get(position).getMaVi());
                changeFrament(new EditWallet());
                mainActivity.toolbar.setTitle("Chi tiết ví");
            }
        });
    }

    private void changeFrament(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }
}
