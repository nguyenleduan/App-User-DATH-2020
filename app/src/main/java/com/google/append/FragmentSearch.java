package com.google.append;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class FragmentSearch  extends Fragment {
    Button btTimXeSearch,btTimTuDongSearch  ,btTimThuCongSearch ,btTimTheoIDSearch
            ,btTimkiemThuCongSearch, btTimkiemIDSearch ,btTimkiemTuDongSearch;
    Spinner spTinhSearch, spquanSearch ,spPhuongSearch ,spLoaixeSearch, sptrongtaiSearch;
    EditText edtIDXeSearch;
    ListView lvSearch;
    ConstraintLayout ContrainLayout153;
    LinearLayout  LinearTimThuCong , LinearTimTheoID, LinearTimTuDong;
    CheckBox cbTìmDiemSearch;
/////////
     static  ArrayList<Bus> mangBus = new ArrayList<Bus>();

    DatabaseReference  mdatafirebasegetbussearch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View viewFragmentManage = inflater.inflate(R.layout.fragment_layout_manage,container,false);
        lvSearch = getActivity().findViewById(R.id.lvSearch);
        LinearTimThuCong = viewFragmentManage.findViewById(R.id.LinearTimThuCong);
        LinearTimTheoID = viewFragmentManage.findViewById(R.id.LinearTimTheoID);
        LinearTimTuDong = viewFragmentManage.findViewById(R.id.LinearTimTuDong);
        sptrongtaiSearch = viewFragmentManage.findViewById(R.id.sptrongtaiSearch);
        spLoaixeSearch = viewFragmentManage.findViewById(R.id.spLoaixeSearch);
        spPhuongSearch = viewFragmentManage.findViewById(R.id.spPhuongSearch);
        spquanSearch = viewFragmentManage.findViewById(R.id.spquanSearch);
        btTimkiemTuDongSearch = viewFragmentManage.findViewById(R.id.btTimkiemTuDongSearch);
        ContrainLayout153 = viewFragmentManage.findViewById(R.id.ContrainLayout153);
        edtIDXeSearch = viewFragmentManage.findViewById(R.id.edtIDXeSearch);
        btTimkiemIDSearch = viewFragmentManage.findViewById(R.id.btTimkiemIDSearch);
        btTimkiemThuCongSearch = viewFragmentManage.findViewById(R.id.btTimkiemThuCongSearch);
        btTimTheoIDSearch = viewFragmentManage.findViewById(R.id.btTimTheoIDSearch);
        btTimThuCongSearch = viewFragmentManage.findViewById(R.id.btTimThuCongSearch);
        btTimTuDongSearch = viewFragmentManage.findViewById(R.id.btTimTuDongSearch);
        btTimXeSearch = viewFragmentManage.findViewById(R.id.btTimXeSearch);
        spTinhSearch = viewFragmentManage.findViewById(R.id.spTinhSearch);

        //////// firebase
        mdatafirebasegetbussearch= FirebaseDatabase.getInstance().getReference();

//        getBusSearch("123123");
//        getListBusSearch( );



        onlick();
        return viewFragmentManage;
    }

//    private void getBusSearch( String id){
//        mdatafirebasegetbussearch = FirebaseDatabase.getInstance().getReference().child("Bus").child("123");
//        mdatafirebasegetbussearch.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Bus bus = dataSnapshot.getValue(Bus.class);
//                mangBus.add(new Bus(bus.Phuong,bus.Quan,bus.Ten,"Hc mViet Name","","",
//                        "","","","","",""));
//
//                Toast.makeText(getActivity(),mangBus.get(0).Phuong + mangBus.get(0).Tinh , Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        BusAdapter busAdapter = new BusAdapter(getActivity().getApplicationContext(),R.layout.custom_listview_bus, mangBus);
//        busAdapter.notifyDataSetChanged();
//        lvSearch.setAdapter(busAdapter);
//    }
//
//    public void getListBusSearch ( ){
//        mdatafirebasegetbussearch= FirebaseDatabase.getInstance().getReference();
//        mdatafirebasegetbussearch.child("Bus").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Bus bus = dataSnapshot.getValue(Bus.class);
//                if (bus.trangthai.equals("1")){
//                    mangBus.add(new Bus("123Phuong",bus.Ten,bus.Ten,"Hc mViet Name","",bus.iduser,
//                            bus.loaiXe,"","3000","","",""));
//                    Toast.makeText(getActivity(), mangBus.size()+"", Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//    }











    private void onlick() {
        btTimkiemTuDongSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusAdapter  busAdapter = new BusAdapter(getActivity(), R.layout.custom_listview_bus, mangBus);
                busAdapter.notifyDataSetChanged();
                lvSearch.setAdapter(busAdapter);
                ContrainLayout153.setVisibility(View.GONE);
                btTimXeSearch.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Tìm Auto", Toast.LENGTH_SHORT).show();
            }
        });
        btTimkiemThuCongSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                ContrainLayout153.setVisibility(View.GONE);
//                btTimXeSearch.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Tìm thủ công", Toast.LENGTH_SHORT).show();
            }
        });
        btTimkiemIDSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ContrainLayout153.setVisibility(View.GONE);
//                btTimXeSearch.setVisibility(View.VISIBLE);
//                Toast.makeText(getActivity(), "Tìm ID", Toast.LENGTH_SHORT).show();
            }
        });
        btTimXeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btTimXeSearch.setVisibility(View.GONE);
//                ContrainLayout153.setVisibility(View.VISIBLE);
//                LinearTimThuCong.setVisibility(View.GONE);
//                LinearTimTheoID.setVisibility(View.GONE);
//                LinearTimTuDong.setVisibility(View.VISIBLE);
//                btTimTuDongSearch.setBackgroundColor(Color.parseColor("#FF22B924"));
//                btTimThuCongSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
//                btTimTheoIDSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
            }
        });
         btTimTuDongSearch.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 LinearTimThuCong.setVisibility(View.GONE);
//                 LinearTimTheoID.setVisibility(View.GONE);
//                 LinearTimTuDong.setVisibility(View.VISIBLE);
//                 btTimTuDongSearch.setBackgroundColor(Color.parseColor("#FF22B924"));
//                 btTimThuCongSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
//                 btTimTheoIDSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
             }
         });
         btTimThuCongSearch.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 loadSpinner();
//                 LinearTimTuDong.setVisibility(View.GONE);
//                 LinearTimTheoID.setVisibility(View.GONE);
//                 LinearTimThuCong.setVisibility(View.VISIBLE);
//                 btTimThuCongSearch.setBackgroundColor(Color.parseColor("#FF22B924"));
//                 btTimTuDongSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
//                 btTimTheoIDSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
             }
         });
         btTimTheoIDSearch.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//
//                 LinearTimTuDong.setVisibility(View.GONE);
//                 LinearTimThuCong.setVisibility(View.GONE);
//                 LinearTimTheoID.setVisibility(View.VISIBLE);
//                 btTimTheoIDSearch.setBackgroundColor(Color.parseColor("#FF22B924"));
//                 btTimTuDongSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
//                 btTimThuCongSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
             }
         });
    }






    //// load adater  spinner
    private void loadSpinner (){
        ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ListThanhPho,android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTinhSearch.setAdapter(spAdapter);
        ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ListLoaixe,android.R.layout.simple_spinner_item);
        spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaixeSearch.setAdapter(spLoaixeAdapter);
        spLoaixeSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(getActivity()
                            ,R.array.ListTrongTaiXeTai,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearch.setAdapter(spLoaixeAdapter);
                }else if (position == 1){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(getActivity()
                            ,R.array.ListTrongTaiXeBanTai,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearch.setAdapter(spLoaixeAdapter);
                }else if (position == 2){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(getActivity()
                            ,R.array.ListTrongTaiXe3Gac,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearch.setAdapter(spLoaixeAdapter);
                }else if (position == 3){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(getActivity()
                            ,R.array.ListTrongTaiXeMay,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearch.setAdapter(spLoaixeAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //// load dia chỉ
        spTinhSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    ArrayAdapter<CharSequence> spAdapterQuan = ArrayAdapter.createFromResource(getActivity(),R.array.ListQuanHCM,android.R.layout.simple_spinner_item);
                    spAdapterQuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spquanSearch.setAdapter(spAdapterQuan);
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spquanSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position== 0){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(getActivity(),R.array.ListPhuongQuan1HCM,android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 1 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(getActivity(),R.array.ListPhuongQuan2HCM,android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 2 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(getActivity()
                            ,R.array.ListPhuongQuan3HCM,
                            android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 3 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(getActivity()
                            ,R.array.ListPhuongQuan4HCM,
                            android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 4 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(getActivity()
                            ,R.array.ListPhuongQuan5HCM,
                            android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 5 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(getActivity()
                            ,R.array.ListPhuongQuan6HCM,
                            android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 6 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(getActivity()
                            ,R.array.ListPhuongQuan7HCM ,
                            android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
