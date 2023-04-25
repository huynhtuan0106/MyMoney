package com.example.mymoney_final;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymoney_final.Fragment.*;
import com.example.mymoney_final.Fragment.GiaoDich.Add;
import com.example.mymoney_final.Fragment.LichSuGiaoDich.History;
import com.example.mymoney_final.Fragment.TrangChu.Main;
import com.example.mymoney_final.Fragment.ViCuaToi.CreateWallet;
import com.example.mymoney_final.Fragment.ViCuaToi.MyWallet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer_Layout;
    public BottomNavigationView bottom_NavigationView;

    public Toolbar toolbar;
    public NavigationView navigationView;

    int MaND = 0;
    int MaViClick = 0;
    int MaGDClick = 0;
    Button btnNo,btnYes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer_Layout = findViewById(R.id.drawer_layout);
        bottom_NavigationView = findViewById(R.id.bottom_navigation);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this,drawer_Layout,toolbar,R.string.open,R.string.close);
        drawer_Layout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        changeFrament(new Main());
        navigationView.getMenu().findItem(R.id.Main).setChecked(true);
        bottom_NavigationView.getMenu().findItem(R.id.main).setChecked(true);

        Intent intent = getIntent();
        MaND = intent.getIntExtra("MaND",0);

        Toolbar finalToolbar = toolbar;

        bottom_NavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.main){
                    changeFrament(new Main());
                    navigationView.getMenu().findItem(R.id.Main).setChecked(true);
                    finalToolbar.setTitle("Trang chủ");
                }

                else if (id == R.id.person){
                    changeFrament(new Mypage());
                    navigationView.getMenu().findItem(R.id.Main).setChecked(true);
                    finalToolbar.setTitle("Cá nhân");
                }

                else if (id == R.id.add){
                    changeFrament(new Add());
                    navigationView.getMenu().findItem(R.id.Main).setChecked(true);
                    finalToolbar.setTitle("Thêm giao dịch");
                }

                else if (id == R.id.history){
                    changeFrament(new History());
                    navigationView.getMenu().findItem(R.id.Main).setChecked(true);
                    finalToolbar.setTitle("Lịch sử giao dịch");
                }

                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.DangXuat) {
            Logout_Dialog(Gravity.CENTER);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        toolbar = findViewById(R.id.toolbar);
        Toolbar finalToolbar = toolbar;
        int id = item.getItemId();
        if (id == R.id.Main){
            changeFrament(new MyWallet());
            bottom_NavigationView.getMenu().findItem(R.id.main).setChecked(true);
            finalToolbar.setTitle("Ví của tôi");
        }
        else if (id == R.id.Report) {
            changeFrament(new Report());
            bottom_NavigationView.getMenu().findItem(R.id.main).setChecked(true);
            finalToolbar.setTitle("Báo cáo");
        }

        drawer_Layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer_Layout.isDrawerOpen(GravityCompat.START)) {
            drawer_Layout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void changeFrament(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }

    public void Logout_Dialog (int gravity) {
        final Dialog dialog = new Dialog(this);
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
                finish();
            }
        });

        dialog.show();
    }

    public int getMaND() {
        return MaND;
    }

    public int getMaViClick() {
        return MaViClick;
    }

    public void setMaViClick(int maViClick) {
        MaViClick = maViClick;
    }

    public int getMaGDClick() {
        return MaGDClick;
    }

    public void setMaGDClick(int maGDClick) {
        MaGDClick = maGDClick;
    }
}
