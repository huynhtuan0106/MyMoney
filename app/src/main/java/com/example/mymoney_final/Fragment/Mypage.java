package com.example.mymoney_final.Fragment;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymoney_final.DTO.WalletDTO;
import com.example.mymoney_final.Database.CreateDatabase;
import com.example.mymoney_final.Fragment.ViCuaToi.MyWallet;
import com.example.mymoney_final.LoginActivity;
import com.example.mymoney_final.MainActivity;
import com.example.mymoney_final.R;
import com.google.android.material.textfield.TextInputEditText;

public class Mypage extends Fragment {
    MainActivity mainActivity;
    CreateDatabase createDatabase;
    Button vi, dangxuat, doimk;
    TextView phone, name;
    Button btnNo, btnYes, btnHuy, btnLuu;
    TextInputEditText mkcu,mkmoi,mkmoi2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.mypage,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int MaND = mainActivity.getMaND();
        vi = view.findViewById(R.id.vi);
        dangxuat = view.findViewById(R.id.dangxuat);
        doimk = view.findViewById(R.id.doimk);
        phone = view.findViewById(R.id.phone);
        name = view.findViewById(R.id.name);

        createDatabase = new CreateDatabase(getActivity());

        Cursor data = createDatabase.GetData("SELECT * FROM NguoiDung where MaND = " + MaND);
        while (data.moveToNext()) {
            name.setText(data.getString(1));
            phone.setText(data.getString(4));
        }

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrament(new MyWallet());
                mainActivity.toolbar.setTitle("Ví của tôi");
                mainActivity.bottom_NavigationView.getMenu().findItem(R.id.main).setChecked(true);
            }
        });

        doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoiMK_Dialog(Gravity.CENTER);
            }
        });

        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout_Dialog(Gravity.CENTER);
            }
        });
    }

    private void changeFrament(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }

    public void Logout_Dialog (int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);


        btnNo = dialog.findViewById(R.id.btnNo);
        btnYes = dialog.findViewById(R.id.btnYes);



        btnNo.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                dialog.dismiss();
            }

        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.finish();
            }
        });

        dialog.show();
    }

    public void DoiMK_Dialog (int gravity) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.doimk);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);


        btnHuy = dialog.findViewById(R.id.btnHuy);
        btnLuu = dialog.findViewById(R.id.btnLuu);
        mkcu = dialog.findViewById(R.id.mkcu);
        mkmoi = dialog.findViewById(R.id.mkmoi);
        mkmoi2 = dialog.findViewById(R.id.mkmoi2);


        btnHuy.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                dialog.dismiss();
            }

        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mkcu.getText().toString().equals("")|| mkmoi.getText().toString().equals("") || mkmoi2.getText().toString().equals("")) {
                    Toast.makeText(getActivity(),
                            "Vui lòng nhập đầy đủ thông tin.",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    int MaND = mainActivity.getMaND();
                    Cursor data = createDatabase.GetData("SELECT * FROM NguoiDung where MaND = " + MaND);
                    if (data.moveToNext()) {
                        if (data.getString(3).trim().equals(mkcu.getText().toString()) != true) {
                            Toast.makeText(getActivity(),
                                    "Mật khẩu hiện tại không đúng.",
                                    Toast.LENGTH_LONG).show();
                        } else if (mkmoi.getText().toString().equals(mkmoi2.getText().toString()) != true) {
                            Toast.makeText(getActivity(),
                                    "Mật khẩu mới và xác nhận mật khẩu phải giống nhau.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            createDatabase.QueryData("Update NguoiDung set Password = '" + mkmoi.getText().toString() + "' where MaND = " + MaND);
                            Toast.makeText(getActivity(),
                                    "Đổi mật khẩu thành công.",
                                    Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                }
            }
        });

        dialog.show();
    }
}
