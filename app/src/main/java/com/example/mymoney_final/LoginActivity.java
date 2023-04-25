package com.example.mymoney_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoney_final.Database.CreateDatabase;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextView tvSignup;
    Button btnLogin;
    TextInputEditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CreateDatabase database = new CreateDatabase(LoginActivity.this);
        database.Open();


        //Toast.makeText(LoginActivity.this, Environment.getDataDirectory().toString(), Toast.LENGTH_LONG).show();


        tvSignup = findViewById(R.id.tvSignup);
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(LoginActivity.this,
                            "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else  {
                    Cursor data = database.GetData("SELECT * FROM NguoiDung where " +
                            "Username = '" + edtUsername.getText().toString()
                    + "' and Password = '" + edtPassword.getText().toString() + "'");
                    if (data.moveToNext()) {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("MaND",data.getInt(0));
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,
                                "Tên đăng nhập hoặc mật khẩu không đúng!",
                                Toast.LENGTH_LONG).show();
                    }
                    }
                }
        });

    }
}
