package com.example.mymoney_final.Fragment.ViCuaToi;

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

public class CreateWallet extends Fragment {

    MainActivity mainActivity;
    EditText walletName, walletBalance;
    RadioButton active, lock;
    Button btnCreate;
    int MaND = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.create_wallet,container,false);
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
            mainActivity.toolbar.setTitle("Ví của tôi");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MaND = mainActivity.getMaND();
        walletName = view.findViewById(R.id.walletName);
        walletBalance = view.findViewById(R.id.walletBalance);
        active = view.findViewById(R.id.active);
        lock = view.findViewById(R.id.lock);
        btnCreate = view.findViewById(R.id.btnCreat);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (walletName.getText().toString().equals("") || walletBalance.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                }
                else  {
                    CreateDatabase database = new CreateDatabase(getActivity());
                    if (active.isChecked() == true) {
                        database.QueryData("INSERT INTO Vi VALUES (null, " + MaND + ",'"+
                                walletName.getText().toString() + "','" + walletBalance.getText().toString() + "', 1)");
                    }
                    else {
                        database.QueryData("INSERT INTO Vi VALUES (null, " + MaND + ",'"+
                                walletName.getText().toString() + "','" + walletBalance.getText().toString() + "', 0)");
                    }
                    Toast.makeText(getActivity(), "Tạo ví mới thành công!", Toast.LENGTH_SHORT).show();
                    changeFrament(new MyWallet());
                }
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

