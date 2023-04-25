package com.example.mymoney_final.Fragment.ViCuaToi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymoney_final.DTO.WalletDTO;
import com.example.mymoney_final.R;

import java.util.List;

public class Adapter_wallet extends ArrayAdapter<WalletDTO> {
    public Adapter_wallet(Context context, @NonNull List<WalletDTO> objects) {
        super(context, R.layout.mywallet_item, objects);
    }
    private static class ViewHolder {
        TextView walletName, walletBalance;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WalletDTO i = getItem(position);
        ViewHolder v;

        if (convertView == null) {
            v = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.mywallet_item, parent, false);
            v.walletName = convertView.findViewById(R.id.walletName);
            v.walletBalance = convertView.findViewById(R.id.walletBalance);
            convertView.setTag(v);
        }
        else {
            v = (ViewHolder) convertView.getTag();
        }
        v.walletName.setText(i.getName());
        long k = i.getBalance();
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
        v.walletBalance.setText(t);
        return convertView;
    }
}
