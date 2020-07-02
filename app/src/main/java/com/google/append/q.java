package com.google.append;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class q extends AppCompatActivity {
    ListView lv;
    DatabaseReference mdatafirebasex,mdatafirebaseaddBus;
    public static  ArrayList<Bus> mangBus = new ArrayList<Bus>();
    ArrayAdapter arrayAdapterz = null;
    TextView  t;
    Button b;
    static String  ea = "";



    DatabaseReference  mdatafirebasegetbussearch;
    int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q);
        lv = findViewById(R.id.lv);
        b = findViewById(R.id.b);

        mdatafirebasex= FirebaseDatabase.getInstance().getReference();
        //        getBusSearch("123123");
      get();
      b.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              BusAdapter busAdapter = new BusAdapter(q.this ,R.layout.custom_listview_bus, mangBus);
              busAdapter.notifyDataSetChanged();
              lv.setAdapter(busAdapter);
          }
      });
        //getBusSearch("123123");
    }
    private void getBusSearch( String id){
        mdatafirebasegetbussearch = FirebaseDatabase.getInstance().getReference().child("Bus");
        mdatafirebasegetbussearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bus bus = dataSnapshot.getValue(Bus.class);
                if (bus.trangthai.equals("1") && bus.loaiXe.equals("Xe Tải")){
                    mangBus.add(new Bus("123Phuong",bus.Ten,bus.Ten,"Hc mViet Name","",bus.iduser,
                            bus.loaiXe,"","3000","","",""));
                    Toast.makeText(q.this, mangBus.size()+"", Toast.LENGTH_SHORT).show();
                    BusAdapter busAdapter = new BusAdapter(q.this ,R.layout.custom_listview_bus, mangBus);
                    busAdapter.notifyDataSetChanged();
                    lv.setAdapter(busAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void asd(){



    }

    private void get (){
        mdatafirebasex.child("Bus").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Bus bus = dataSnapshot.getValue(Bus.class);
//                if (bus.trangthai.equals("1") && bus.loaiXe.equals("Xe Tải")){
                    mangBus.add(new Bus("123Phuong",bus.Ten,bus.Ten,"Hc mViet Name","",bus.iduser,
                            bus.loaiXe,"","3000","","","")  );
//
//                    BusAdapter busAdapter = new BusAdapter(q.this ,R.layout.custom_listview_bus, mangBus);
//                    busAdapter.notifyDataSetChanged();
//                    lv.setAdapter(busAdapter);
//                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
//    public void  getProfile( ){
//        mdatafirebasex= FirebaseDatabase.getInstance().getReference().child("bus").child("1996") ;
//        mdatafirebasex.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ea = "1";
//                Bus bus = dataSnapshot.getValue(Bus.class);
//                ar.add(bus.Name+" - "+bus.idBus);
//                arrayAdapterz.notifyDataSetChanged();
//                for(DataSnapshot  datas: dataSnapshot.getChildren()){
//                    String names=datas.child("Name").getValue().toString();
//                    t.setText(names);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


}
