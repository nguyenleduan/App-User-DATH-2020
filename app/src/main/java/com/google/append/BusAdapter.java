package com.google.append;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

public class BusAdapter extends BaseAdapter {
    Context mycontext;
    int mylayout;
    List<Bus> arrayBus;
    public BusAdapter(Context context, int layout, List<Bus> mangBus){
        mycontext = context;
        mylayout = layout;
        arrayBus = mangBus;
    }
    @Override
    public int getCount() {
        return arrayBus.size();
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
        ImageView imgbuslisetview = convertView.findViewById(R.id.imgbuslisetview);
        TextView txtNameBus,txttrangthaiListView,txtLocationBus,txtDiem, txtLoaiXe ,txtTrongTai;
        ConstraintLayout ConstrainCustomListView;
        txttrangthaiListView = convertView.findViewById(R.id.txttrangthaiListView);
        ConstrainCustomListView = convertView.findViewById(R.id.ConstrainCustomListView);
        txtNameBus = convertView.findViewById(R.id.txtNameBus);
        txtLocationBus = convertView.findViewById(R.id.txtLocationBus);
        txtDiem = convertView.findViewById(R.id.txtDiem);
        txtLoaiXe = convertView.findViewById(R.id.txtLoaiXe);
        txtTrongTai = convertView.findViewById(R.id.txtTrongTai);
        //
        txtNameBus.setText(arrayBus.get(position).Ten);
        txtLocationBus.setText( arrayBus.get(position).Tinh +", "+arrayBus.get(position).Phuong);
        txtDiem.setText(arrayBus.get(position).diem);
        txtLoaiXe.setText(arrayBus.get(position).loaiXe);
        txtTrongTai.setText(arrayBus.get(position).trongtai);
        if (arrayBus.get(position).trangthai.toString().equals("1")){
            txttrangthaiListView.setText("Đang chờ");
        }else if (arrayBus.get(position).trangthai.toString().equals("0")){
            txttrangthaiListView.setText("Đang vận chuyển");
        }else if (arrayBus.get(position).trangthai.toString().equals("3")){
            txttrangthaiListView.setText("Tạm ngừng hoạt động");
        }else
            txttrangthaiListView.setText(" Loading...");







        if (position%2==0){
            ConstrainCustomListView.setBackgroundResource(R.drawable.layout_custom1);
//            ConstrainCustomListView.setBackgroundColor(Color.parseColor("#FFFEC515"));
        }

        if (arrayBus.get(position).loaiXe.equals("Xe Tải")){
            imgbuslisetview.setImageResource(R.drawable.ic_bus_transport);
        }
        if (arrayBus.get(position).loaiXe.equals("Xe Bán Tải")){
            imgbuslisetview.setImageResource(R.drawable.ic_bus_pickuptruck);
        }
        if (arrayBus.get(position).loaiXe.equals("Xe 3 Gác")){
            imgbuslisetview.setImageResource(R.drawable.ic_bus_3wheel);
        }
        if (arrayBus.get(position).loaiXe.equals("Xe Máy"))
            imgbuslisetview.setImageResource(R.drawable.ic_bus_motorycle);
        return convertView;
    }
}
