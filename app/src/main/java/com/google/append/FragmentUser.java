package com.google.append;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
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

public class FragmentUser extends Fragment  {
    ImageButton imgbtlogout;
    TextView txtidf, txtthongtincanhan , txtName,txtNumberVanChuyen,txtEmailDetailedProfile, txtNameDetailedProfile,txtPhoneDetailedProfile ,txtNumberDetailedProfile ;
    LinearLayout LinearDetailedProfile,LinearEditProfile,LinearHistoryProfile,Linearprofile1 ,Linearprofile2,Linearprofile3;
    ScrollView ScrollViewProfiles;
    ListView lvFUser;
    Button btseteditprofile;
    EditText edtEditPhoneProfile,edtEditNameProfile;
    ArrayList<String> aBus;
    ArrayAdapter adapter = null;
    DatabaseReference mdatafiregetbus ,mdatafiresetprofile;
    ConstraintLayout ConstraintLayoutdangkybus;
    static String sIDF;
    static String sEmailF;
    static String sSDTF;
    static String sLinkF;
    ArrayList<Bus> buslist;
    static MainActivity main = new MainActivity();
    public int m1 = 0,m2 = 0,m3 = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View viewFragmentUser = inflater.inflate(R.layout.fragment_layout_user,container,false);
        buslist = new ArrayList<Bus>();
        //intent

        mdatafiresetprofile =FirebaseDatabase.getInstance().getReference();
        lvFUser= viewFragmentUser.findViewById(R.id.lvFUser);
        txtidf= viewFragmentUser.findViewById(R.id.txtidF);
        txtName= viewFragmentUser.findViewById(R.id.txtName);
        edtEditPhoneProfile= viewFragmentUser.findViewById(R.id.edtEditPhoneProfile);
        btseteditprofile= viewFragmentUser.findViewById(R.id.btseteditprofile);
        edtEditNameProfile= viewFragmentUser.findViewById(R.id.edtEditNameProfile);
        Linearprofile1 = viewFragmentUser.findViewById(R.id.Linearprofile1);
        Linearprofile2= viewFragmentUser.findViewById(R.id.Linearprofile2);
        Linearprofile3= viewFragmentUser.findViewById(R.id.Linearprofile3);
        txtNumberVanChuyen= viewFragmentUser.findViewById(R.id.txtNumberVanChuyen);
        txtEmailDetailedProfile= viewFragmentUser.findViewById(R.id.txtEmailDetailedProfile);
        txtNameDetailedProfile= viewFragmentUser.findViewById(R.id.txtNameDetailedProfile);
        txtPhoneDetailedProfile = viewFragmentUser.findViewById(R.id.txtPhoneDetailedProfile);
        txtNumberDetailedProfile= viewFragmentUser.findViewById(R.id.txtNumberDetailedProfile);
        imgbtlogout = viewFragmentUser.findViewById(R.id.imgbtlogout);
        LinearHistoryProfile= viewFragmentUser.findViewById(R.id.LinearHistoryProfile);
        ScrollViewProfiles= viewFragmentUser.findViewById(R.id.ScrollViewProfiles);
        txtthongtincanhan = viewFragmentUser.findViewById(R.id.txtthongtincanhan);
        LinearDetailedProfile= viewFragmentUser.findViewById(R.id.LinearDetailedProfile);
        LinearEditProfile= viewFragmentUser.findViewById(R.id.LinearEditProfile);
        txtNumberVanChuyen.setText(main.mangHistory.size()+"");
        //clickeven
        onlick();
        setProfile();
        return viewFragmentUser;
    }


    private void setProfile() {
        MainActivity main = new MainActivity();
        txtidf.setText(main.sID);
        txtName.setText(main.sName);
        txtEmailDetailedProfile.setText(main.sEmail);
        txtNameDetailedProfile.setText(main.sName);
        txtPhoneDetailedProfile.setText(main.sPhone);
    }

    // EditProfile

    /// onlick
    public void onlick() {
        btseteditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), txtidf.getText().toString()+"   1", Toast.LENGTH_SHORT).show();
//                main.setPhoneUser(txtidf.getText().toString(),edtEditNameProfile.getText().toString()
//                        ,txtEmailDetailedProfile.getText().toString(),
//                        edtEditPhoneProfile.getText().toString(),"lick.com");
            }
        });

        /// ẩn hien liner profile
        Linearprofile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m1 == 0){
                    LinearDetailedProfile.setVisibility(View.VISIBLE);
                    m1 = 1;
                }else{
                    LinearDetailedProfile.setVisibility(View.GONE);
                    m1 = 0;
                }

            }
        });
        Linearprofile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m2 == 0){
                    LinearEditProfile.setVisibility(View.VISIBLE);
                    m2 = 1;
                }else{
                    LinearEditProfile.setVisibility(View.GONE);
                    m2 = 0;
                }
            }
        });
        Linearprofile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (m3 == 0){
//                    LinearHistoryProfile.setVisibility(View.VISIBLE);
//                    m3 = 1;
//                }else{
//                    LinearHistoryProfile.setVisibility(View.GONE);
//                    m3 = 0;
//                }
                HistoryAdapter historyAdapter = new HistoryAdapter(getContext(),R.layout.listview_history,main.mangHistory);
                lvFUser.setAdapter(historyAdapter);
                    historyAdapter.notifyDataSetChanged();
            }
        });

    }


}
