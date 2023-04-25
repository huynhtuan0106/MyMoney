package com.example.mymoney_final;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mymoney_final.Database.*;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    TextView tvLogin;
    EditText edtName, edtSDT, edtUsername, edtPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tvLogin = findViewById(R.id.tvLogin);
        edtName = findViewById(R.id.edtName);
        edtSDT = findViewById(R.id.edtSDT);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().equals("") || edtSDT.getText().toString().equals("")
                        || edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this,
                            "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                }
                else  {
                    CreateDatabase database = new CreateDatabase(SignupActivity.this);
                    database.QueryData("INSERT INTO NguoiDung VALUES (null,'"+
                            edtName.getText().toString() + "','" + edtUsername.getText().toString() + "','"
                            + edtPassword.getText().toString() + "','" + edtSDT.getText().toString() + "')");
                    Toast.makeText(SignupActivity.this,
                            "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
