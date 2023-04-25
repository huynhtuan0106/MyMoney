package com.example.mymoney_final.Fragment.LichSuGiaoDich;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class viewPaper extends FragmentStatePagerAdapter {

    public viewPaper(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new All();
            case 1:
                return new Thu();
            case 2:
                return new Chi();
            default:
                return new All();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Tất cả";
            case 1:
                return "Khoản thu";
            case 2:
                return "Khoản chi";
            default:
                return "Tất cả";
        }
    }
}
