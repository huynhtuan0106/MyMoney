package com.example.mymoney_final.Fragment.ViCuaToi;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymoney_final.Database.CreateDatabase;
import com.example.mymoney_final.MainActivity;
import com.example.mymoney_final.R;

public class EditWallet extends Fragment {

    MainActivity mainActivity;
    EditText walletName, walletBalance;
    RadioButton active, lock;
    Button btnLuu, btnXoa;
    int MaND = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.edit_wallet,container,false);
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
            changeFrament(new MyWallet());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaND = mainActivity.getMaND();
        int MaViClick = mainActivity.getMaViClick();
        walletName = view.findViewById(R.id.walletName);
        walletBalance = view.findViewById(R.id.walletBalance);
        active = view.findViewById(R.id.active);
        lock = view.findViewById(R.id.lock);
        btnLuu = view.findViewById(R.id.btnLuu);
        btnXoa = view.findViewById(R.id.btnXoa);
        CreateDatabase database = new CreateDatabase(getActivity());

        Cursor data = database.GetData("SELECT * FROM Vi where MaVi = " + MaViClick + " and MaND = " + MaND );

        if (data.moveToNext()) {
            walletName.setText(data.getString(2));
            walletBalance.setText(data.getString(3));
            if (data.getInt(4) == 1) {
                active.setChecked(true);
            }
            else  {
                lock.setChecked(true);
            }
        }

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (walletName.getText().toString().equals("") || walletBalance.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                }
                else  {
                    if (active.isChecked() == true) {
                        database.QueryData("UPDATE Vi SET TenVi = '" + walletName.getText().toString()
                                + "', SoDu = '" + walletBalance.getText().toString() + "', TrangThai = 1 where MaVi = "
                                + MaViClick + " and MaND = " + MaND );
                    }
                    else {
                        database.QueryData("UPDATE Vi SET TenVi = '" + walletName.getText().toString()
                                + "', SoDu = '" + walletBalance.getText().toString() + "', TrangThai = 0 where MaVi = "
                                + MaViClick + " and MaND = " + MaND );
                    }
                    Toast.makeText(getActivity(), "Cập nhật ví thành công!", Toast.LENGTH_SHORT).show();
                    changeFrament(new MyWallet());
                }
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDatabase database = new CreateDatabase(getActivity());
                database.QueryData("Delete from Vi where MaVi = " + MaViClick + " and MaND = " + MaND );
                Toast.makeText(getActivity(), "Xoá ví thành công!", Toast.LENGTH_SHORT).show();
                changeFrament(new MyWallet());
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void changeFrament(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }
}

