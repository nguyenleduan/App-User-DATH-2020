package com.google.append;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

public class HistoryAdapter extends BaseAdapter {

    Context mycontext;
    int mylayout;
    List<HistoryTransport> arrayHistory;

    public HistoryAdapter(Context mycontext, int mylayout, List<HistoryTransport> mang) {
        this.mycontext = mycontext;
        this.mylayout = mylayout;
        this.arrayHistory = mang;
    }

    @Override
    public int getCount() {
        return arrayHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mylayout,null);
        TextView txtDateHis ,txtLocationBeginHis,txtLocationEndHis,txtStatusHis,txtCostHis;
        txtDateHis = convertView.findViewById(R.id.txtDateHis);
        txtLocationBeginHis = convertView.findViewById(R.id.txtLocationBeginHis);
        txtLocationEndHis = convertView.findViewById(R.id.txtLocationEndHis);
        txtStatusHis = convertView.findViewById(R.id.txtStatusHis);
        txtCostHis = convertView.findViewById(R.id.txtCostHis);
        ConstraintLayout ConListView = convertView.findViewById(R.id.ConListView);
        //
        if (arrayHistory.get(position).StatusHistory.equals("Đã hoàn thành"))
            ConListView.setBackgroundResource(R.drawable.listviewbackround);
        txtDateHis.setText(arrayHistory.get(position).DateHistory);
        txtLocationBeginHis.setText(arrayHistory.get(position).LocationBeginHistory);
        txtLocationEndHis.setText(arrayHistory.get(position).LocationEndHistory);
        txtStatusHis.setText(arrayHistory.get(position).StatusHistory);
        txtCostHis.setText(arrayHistory.get(position).CostHistory);

        return convertView;
    }
}
