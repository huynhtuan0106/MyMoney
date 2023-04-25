package com.example.mymoney_final.Fragment.LichSuGiaoDich;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.mymoney_final.Fragment.LichSuGiaoDich.viewPaper;
import com.example.mymoney_final.Fragment.TrangChu.Main;
import com.example.mymoney_final.Fragment.ViCuaToi.MyWallet;
import com.example.mymoney_final.LoginActivity;
import com.example.mymoney_final.MainActivity;
import com.example.mymoney_final.R;
import com.google.android.material.tabs.TabLayout;

public class History extends Fragment {

    public TabLayout tabLayout;
    public ViewPager viewPager;
    public View view;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.notification,container,false);

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.vp);

        viewPaper viewpp = new viewPaper(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewpp);

        tabLayout.setupWithViewPager(viewPager);

        return view;
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
            mainActivity.bottom_NavigationView.getMenu().findItem(R.id.main).setChecked(true);
            changeFrament(new Main());
            mainActivity.toolbar.setTitle("Trang chủ");
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFrament(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }
}
