package com.google.append;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import java.text.NumberFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerDragListener
        ,GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {

    Spinner spTinhSearch, spquanSearch ,spPhuongSearch ,spLoaixeSearch,spLoaixeSearchAuto, sptrongtaiSearch, sptrongtaiSearchAuto;

    EditText edtIDXeSearch;

    ListView lvSearch,lvSearchLocation,lvSearchID;

    ConstraintLayout ContrainLayout153,layoutsearch ;

    LinearLayout LinearTimThuCong,LinearEND ,LinearTransport,LinearHome156,linearLayout9, LinearTimTheoID, LinearTimTuDong,LinearMapTestLocation
            ,LinearAddLocationBus,LinearDetail,LinearAdd,LinearDetailLocation;

    static  ArrayList<Bus> mangBus = new ArrayList<Bus>();
    public static  ArrayList<HistoryTransport> mangHistory = new ArrayList<HistoryTransport>();

    TextView txtIDBusDetaildialog,txtloatxe1,txttt1,txtSDTTran,txtChiPhiDialog, txtNameBusDialog, txtLocationBusDialog, txtLoaiXeBusDialog,
            txtTrongTaiBusDialog, txtSDTBusDialog,txtPointBusDialog,txtDetailBeginSearch
            ,txtDetailEndSearch,txtDetailLocationBegin,
            txtDetailLocationEnd,txtDetailLocationVND,txtPhiVanChuyenSearch;

    EditText edtsetphonedialog,edtLocationEnd,edtLocationBegin;

    Button  btsetphonedialog ,btEditLocationSearch, bthuysetphonedialog,btDongdialogDetailBus,btDatXeDialogDetailBus ,
            btTestLocationBegin,btTestLocationEnd,btMyLocationEnd,btMyLocationBegin,btXacNhanLocation,
            btTimXeSearch,btTimTuDongSearch  ,btTimThuCongSearch ,btTimTheoIDSearch
            ,btTimkiemThuCongSearch, btTimkiemIDSearch ,btTimkiemTuDongSearch;

    ImageView imgSearch, imgHome,imgUser;

    DatabaseReference mdatafire , mdatafiresetprofile,mdatafirebasegetbussearch,mdatafiresetGetlocationBus;

    public static String sName, sLinkAvatar, sPhone,sEmail,sID,sstatusAccuracy;

    Dialog  dialogloading,dialogsetphone, dialogDetailBus;

    static DatabaseReference mdatafiregetBusLocation,  mdatafirebasex
            ,mDataFirebaseSetAccuracy,mdatafirebaseAccuracy  ;

    FrameLayout FramContent;

    static  double dRange,dLat,dLong,dMyLatitude , dMyLongitude,dLatitudeBegin,dLongitudeBegin,
            dLatBegin=0,dLoBegin=0,dLatEnd=0,dLoEnd=0 ;
//
    // marker
    Marker markerBegin,markerEnd;
    Timer T,TIME;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Fragment fragment = null;
    /// google maps API
   static int status = 0 ,keyhistory,statusCheckBegin = 0,statusCheckEnd = 0,countsearch=0,countime;
    static String sTimeNow = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    static   int countimes = 0;
    private static GoogleMap mMap;
    public  double  x = 10.807453,y=106.7157249 ,end_x,end_y ,yend;
    public static float  numberKm = 0;
    private FusedLocationProviderClient client;
    private GoogleApiClient clientgoogle;
    private LocationRequest locationRequest;
    private Location lastlocation , myLocation;
    private static Marker currenMarker,mMarkerBus ,mMe,mMarkerBgin ,mMarkerEnd ;
    public  static  final int RE_LOCATION_CODE = 99;
    static SupportMapFragment mapTest , mapFragmentBus ;
    public static HistoryAdapter hhistoryAdapter;
    static boolean dstatus = false ,statusTranBus=false,clickerCheckBegin=false, clickerCheckEnd=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        sID=intent.getStringExtra("KeyID" );
        inten();
        T=new Timer();
        mapTest = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapTestlocation);
        mapFragmentBus = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapTran);

        mapTest.getMapAsync(this);
        mapFragmentBus.getMapAsync(MainActivity.this);

        client = LocationServices.getFusedLocationProviderClient(this);
        mdatafiresetprofile= FirebaseDatabase.getInstance().getReference();
        mdatafiresetGetlocationBus= FirebaseDatabase.getInstance().getReference();
        dialogloading = new Dialog(this);
        dialogDetailBus = new Dialog(this);
        dialogsetphone = new Dialog(this);
        dialogDetailBus.setContentView(R.layout.dialog_custom_detail_bus_listview);
        dialogloading.setContentView(R.layout.dialog_loading);
        dialogsetphone.setContentView(R.layout.dialog_custom_setsdt);
        btsetphonedialog = dialogsetphone.findViewById(R.id.btsetphonedialog);
        bthuysetphonedialog = dialogsetphone.findViewById(R.id.bthuysetphonedialog);
        edtsetphonedialog = dialogsetphone.findViewById(R.id.edtsetphonedialog);
        dialogloading.show();
        getProfileUser(sID);
        onlick();
        ///// google maps APi
        loadSpinner();
        ///
        fragment = new FragmentHome();
        fragmentTransaction.replace(R.id.FramContent,fragment);
        // set Lineart search GONE
        LinearHome156.setVisibility(View.GONE);
        //add Fragment
        fragmentTransaction.commit();
        evenlistview();
        DialogDetailBus();
        listHistory(sID);


    }
    // get SDT bus
    private void SDTBUS (String id){
        mdatafirebasex= FirebaseDatabase.getInstance().getReference().child("profileuser").child(id);
        mdatafirebasex.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    ProfileUser profile = dataSnapshot.getValue(ProfileUser.class);
                    txtSDTBusDialog.setText(profile.Phonenumber);
                    mdatafirebasex.removeEventListener(this);
                }catch (Exception e){
                    txtSDTBusDialog.setText("Chưa bổ sung SĐT");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    // list Histpry
    public void listHistory(final String id){
//        final FragmentUser fragmentUser = (FragmentUser) getFragmentManager().findFragmentById(R.id.FramContent);
        mdatafiregetBusLocation= FirebaseDatabase.getInstance().getReference().child("HistoryTransport").child(id) ;
        mdatafiregetBusLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mangHistory.clear();
                mdatafiregetBusLocation= FirebaseDatabase.getInstance().getReference().child("HistoryTransport").child(id) ;
                mdatafiregetBusLocation.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.getValue()!=null){
                            HistoryTransport his = dataSnapshot.getValue(HistoryTransport.class);
                            if (his.BusHistory.equals("0")) {
                                mangHistory.add( new HistoryTransport(his.BusHistory,his.CostHistory,his.DateHistory
                                        ,his.IDBusHistory,his.IDUserHistory,his.LocationBeginHistory,his.LocationEndHistory
                                        ,his.SpeciesBusHistory,his.StatusHistory,his.TonnageHistory));
                            }
                        }
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void ramdom(){
        Random r = new Random();
        int key = (r.nextInt(100000) + 1);
        keyhistory = key;
    }
    // save lịch sửa vận chuyển
    private void savehistoryTransport(int key,String id,String bus, String cost, String Date, String idBus, String idUser, String LocationBegin
            , String LocationEnd, String Species, String stastus, String Tonnage){
        HistoryTransport historyTransport = new HistoryTransport(bus,cost,Date,idBus,idUser,LocationBegin
                ,LocationEnd,Species,stastus,Tonnage);
        mdatafiresetprofile.child("HistoryTransport/"+id+"/"+key).setValue(historyTransport);
    }
    // Updet trang thai vận chuyển
    private void updateTransport(final String id, final String skeyhistory, final String stastus){
        mdatafiregetBusLocation= FirebaseDatabase.getInstance().getReference().child("HistoryTransport").child(id).child(skeyhistory);
        mdatafiregetBusLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HistoryTransport his = dataSnapshot.getValue(HistoryTransport.class);
                HistoryTransport historyTransport = new HistoryTransport(his.BusHistory,his.CostHistory,his.DateHistory
                            ,his.IDBusHistory,his.IDUserHistory,his.LocationBeginHistory,his.LocationEndHistory
                            ,his.SpeciesBusHistory,stastus,his.TonnageHistory);
                mdatafiresetprofile.child("HistoryTransport/"+id+"/"+skeyhistory).setValue(historyTransport);
                mdatafiregetBusLocation.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    // gửi yếu cầu
    private void SendAccuracy(String sidUser , String sidBus,String slocationBegin,String slocationEnd,String sCost,
                              String sLatBegin,String sLatEnd,String sLongbegin,String sLongEnd  ){
        final Accuracy accuracy = new Accuracy(sCost,sidUser,sLatBegin,sLatEnd,slocationBegin,
                slocationEnd,sLongbegin,sLongEnd,"5" );
        mdatafiresetprofile.child("Accuracy/"+sidBus).setValue(accuracy);
    }
        /// kiem tra cái google map nay
    public void  getBusLocation(final String idBus ){
        mdatafiregetBusLocation= FirebaseDatabase.getInstance().getReference().child("Bus").child(idBus);
        mdatafiregetBusLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double a  , b;
                Bus bussss = dataSnapshot.getValue(Bus.class);
                a = Double.parseDouble(bussss.viTrix);
                b = Double.parseDouble(bussss.viTriy);
                if (mMarkerBus!=null){
                    mMarkerBus.remove();
                }
                LatLng sydney = new LatLng(a,b );
                if (bussss.loaiXe.equals("Xe Tải")){
                    mMarkerBus = mMap.addMarker(new MarkerOptions()
                            .position(sydney)
                            .title("Đang di chuyển" )
                            .snippet(""+sPhone)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_markerxetai)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
                }else if (bussss.loaiXe.equals("Xe Bán Tải")){
                    mMarkerBus = mMap.addMarker(new MarkerOptions()
                            .position(sydney)
                            .title("Đang di chuyển" )
                            .snippet(""+sPhone)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_markerxebantai)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
                }else if (bussss.loaiXe.equals("Xe 3 Gác")){

                    mMarkerBus = mMap.addMarker(new MarkerOptions()
                            .position(sydney)
                            .title("Đang di chuyển" )
                            .snippet(""+sPhone)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_markerxe3gac)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
                }else  if (bussss.loaiXe.equals("Xe Máy")){
                    mMarkerBus = mMap.addMarker(new MarkerOptions()
                            .position(sydney)
                            .title("Đang di chuyển" )
                            .snippet(""+sPhone)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_markerxemay)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    //load marker location
    private void loadMarkerLocaton(String sid){
        mdatafire= FirebaseDatabase.getInstance().getReference().child("Accuracy").child(sid);
        mdatafire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Accuracy ac = dataSnapshot.getValue(Accuracy.class);
                double a  = Double.parseDouble(ac.LatBeginAccuracy);
                double b  = Double.parseDouble(ac.LongBeginAccuracy);
                LatLng sydney1 = new LatLng(a,b );
                mMe = mMap.addMarker(new MarkerOptions()
                        .position(sydney1)
                        .title("Điểm bắt đầu" )
                        .snippet(ac.LocationBeginAccuracy)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_markerbegin)));
                a  = Double.parseDouble(ac.LatEndAccuracy );
                 b  = Double.parseDouble(ac.LongEndAccuracy );
                LatLng ew = new LatLng(a,b );
                mMe = mMap.addMarker(new MarkerOptions()
                        .position(ew)
                        .title("Điểm kết thúc")
                        .snippet(ac.LocationEndAccuracy)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_markerend)));
                mdatafire.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void range(){
        // double latBegin,double longBegin,double latEnd, double longEnd

        // kiểm tra mylocation có gia tri không
        if (edtLocationBegin.getText().toString().equals("Vị trí hiện tại")
                        ||edtLocationEnd.getText().toString().equals("Vị trí hiện tại")){
            float resul[] = new float[10];
             Location.distanceBetween(dMyLatitude,dMyLongitude,dLatEnd,dLoEnd,resul);
            int a = (int) (resul[0]+(resul[0]*0.36));
            txtPhiVanChuyenSearch.setText(  a+""  );
        }else  {
            float resuls[] = new float[10];
            Location.distanceBetween(dLatBegin,dLoBegin,dLatEnd,dLoEnd,resuls);
            int a = (int) (resuls[0]+(resuls[0]*0.36));
            txtPhiVanChuyenSearch.setText(  a+""  );
        }
    }

    // time xác thực
    private void choXacNhan(final String id){
        dialogloading.show();
        getXacThuc(id);
        countime = 0;
        TIME =new Timer();
        TIME.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {countime++;
                        if (countime==20 ){
                            if (sstatusAccuracy== "1")
                                T.cancel();
                           if (sstatusAccuracy=="5"){
                               dialogloading.dismiss();
                               dialogDetailBus.dismiss();
                               T.cancel();
                           }else{
                               T.cancel();
                           }

                        }
                    }
                });
            }
        }, 1000, 1000);
    }
    // set Accuracy
    private void setAccuracyUser(String id,String value){
        mDataFirebaseSetAccuracy =FirebaseDatabase.getInstance().getReference();
        mDataFirebaseSetAccuracy.child("Accuracy").child(id).setValue(value);
        // project Bus check
    }
    // get xac 1 thuc
    private void getXacThuc(final String id){
        mdatafirebaseAccuracy= FirebaseDatabase.getInstance().getReference().child("Accuracy").child(id);
        mdatafirebaseAccuracy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Accuracy accuracy = dataSnapshot.getValue(Accuracy.class);
                if (accuracy.StatusAccuracy.equals("1")) {
                    mapTest = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.mapTran);
                    mapTest.getMapAsync(MainActivity.this);
                    // load marker
                    loadMarkerLocaton(id);
                    getBusLocation(txtIDBusDetaildialog.getText().toString());
                    dstatus= true;
                    dialogloading.dismiss();
                    dialogDetailBus.dismiss();
                    sstatusAccuracy = dataSnapshot.getValue().toString();
                    Toast.makeText(MainActivity.this, "Bắt đầu vận chuyển", Toast.LENGTH_SHORT).show();
                    LinearHome156.setVisibility(View.GONE);
                    LinearTransport.setVisibility(View.VISIBLE);
                    FramContent.setVisibility(View.GONE);
                    txtDetailLocationEnd.setText(edtLocationEnd.getText().toString());
                    txtDetailLocationBegin.setText(edtLocationBegin.getText().toString());
                    ramdom();
                    savehistoryTransport(keyhistory,sID,"0",accuracy.CostAccuracy,sTimeNow,dataSnapshot.getKey(),
                            sID,accuracy.LocationBeginAccuracy,accuracy.LocationEndAccuracy,txtloatxe1.getText().toString()
                            ,"Đang cận chuyển",txttt1.getText().toString() );
                    savehistoryTransport(keyhistory,dataSnapshot.getKey(),"1",accuracy.CostAccuracy,sTimeNow,dataSnapshot.getKey(),
                            sID,accuracy.LocationBeginAccuracy,accuracy.LocationEndAccuracy,txtloatxe1.getText().toString()
                            ,"Đang cận chuyển",txttt1.getText().toString() );
                    mdatafirebasex= FirebaseDatabase.getInstance().getReference().child("Bus").child(txtIDBusDetaildialog.getText().toString());
                    mdatafirebasex.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                Bus a = dataSnapshot.getValue(Bus.class);
                                Bus bus = new Bus(a.Phuong,a.Quan,a.Ten,a.Tinh,a.diem
                                        ,a.iduser,a.loaiXe,a.soxe,"0",a.trongtai,a.viTrix,a.viTriy );
                                mdatafiresetprofile.child("Bus/"+a.iduser).setValue(bus);
                                mdatafirebasex.removeEventListener(this);
                            }catch (Exception e){
                                Toast.makeText(MainActivity.this, e+"", Toast.LENGTH_SHORT).show();
                                mdatafirebasex.removeEventListener(this);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    statusTranBus = true;
                    mdatafirebaseAccuracy.removeEventListener(this);
                }else if (accuracy.StatusAccuracy.equals("2")){
                    dialogloading.dismiss();
                    dialogDetailBus.dismiss();
                    sstatusAccuracy = dataSnapshot.getValue().toString();
                    Toast.makeText(MainActivity.this, "Từ chối vận chuyển", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Vui lòng chọn phương tiện khác", Toast.LENGTH_SHORT).show();
                    mdatafirebaseAccuracy.removeEventListener(this);
                }else if (accuracy.StatusAccuracy.equals("3")){
                    dialogloading.dismiss();
                    dialogDetailBus.dismiss();
                    Toast.makeText(MainActivity.this, "Vui lòng chọn phương tiện khác", Toast.LENGTH_SHORT).show();
                     mdatafirebaseAccuracy.removeEventListener(this);
                }else if (accuracy.StatusAccuracy.equals("4")){
                    sstatusAccuracy = dataSnapshot.getValue().toString();
                }

                countime = 0;
                TIME =new Timer();
                TIME.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {countime++;
                                if (countime==10 ){
                                    mdatafire= FirebaseDatabase.getInstance().getReference().child("Accuracy").child(id);
                                    mdatafire.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Accuracy ac = dataSnapshot.getValue(Accuracy.class);
                                            Accuracy aec = new Accuracy(
                                                    ac.CostAccuracy,
                                                     ac.IDUserSendAccuracy,
                                                      ac.LatBeginAccuracy,
                                                       ac.LatEndAccuracy,
                                                        ac.LocationBeginAccuracy,
                                                        ac.LocationEndAccuracy,
                                                        ac.LongBeginAccuracy,
                                                          ac.LongEndAccuracy ,
                                                            "6");
                                            mdatafiresetprofile.child("Accuracy/"+id).setValue(aec);
                                            mdatafire.removeEventListener(this);
                                            dialogloading.dismiss();
                                            Toast.makeText(MainActivity.this, "Quá thời gia phản hồi \n Hãy chọn phương tiện khác", Toast.LENGTH_SHORT).show();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }

                                    });
                                    TIME.cancel();
                                }
                            }
                        });
                    }
                }, 1000, 1000);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    public void searchLocation (String r){
        String location = r;
        List<Address>  addressList = null;
        if (location != null || !location.equals("")){
            if (clickerCheckBegin==true){
                clickerCheckBegin=false;
                if (edtLocationBegin.getText().toString().equals("Vị trí hiện tại")){
                    LinearMapTestLocation.setVisibility(View.VISIBLE);
                    client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            onMyLocationClick(location);
                            T=new Timer();
                            T.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {countime++;
                                            if (countime==2 ){
                                                LatLng e = new LatLng(dMyLatitude,dMyLongitude );
                                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(e,15));
                                                countime=0;
                                                T.cancel();
                                            }
                                        }
                                    });
                                }
                            }, 1000, 1000);
                        }
                    });
                }else {
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address ad = addressList.get(0);
                    dLat = ad.getLatitude();
                    dLong = ad.getLongitude();
                    LatLng e = new LatLng(dLat,dLong );
                    if (statusCheckBegin == 1){
                        dLatBegin = dLat;
                        dLoBegin = dLong;
                        markerBegin = mMap.addMarker(new MarkerOptions().position(e).title( location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(e,15));
                        statusCheckBegin=0;
                    }else if (statusCheckEnd == 1){
                        dLatEnd = dLat;
                        dLoEnd = dLong;
                        markerEnd = mMap.addMarker(new MarkerOptions().position(e).title( location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(e,15));
                        statusCheckEnd=0;
                    }
                }
            }
            if (clickerCheckEnd==true){
                clickerCheckEnd=false;
                if (edtLocationEnd.getText().toString().equals("Vị trí hiện tại")){
                    LinearMapTestLocation.setVisibility(View.VISIBLE);
                    client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            onMyLocationClick(location);
                            T=new Timer();
                            T.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {countime++;
                                            if (countime==2 ){
                                                LatLng e = new LatLng(dMyLatitude,dMyLongitude );
                                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(e,15));
                                                countime=0;
                                                T.cancel();
                                            }
                                        }
                                    });
                                }
                            }, 1000, 1000);
                        }
                    });
                }else {
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address ad = addressList.get(0);
                    dLat = ad.getLatitude();
                    dLong = ad.getLongitude();
                    LatLng e = new LatLng(dLat,dLong );
                    if (statusCheckBegin == 1){
                        dLatBegin = dLat;
                        dLoBegin = dLong;
                        markerBegin = mMap.addMarker(new MarkerOptions().position(e).title( location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(e,15));
                        statusCheckBegin=0;
                    }else if (statusCheckEnd == 1){
                        dLatEnd = dLat;
                        dLoEnd = dLong;
                        markerEnd = mMap.addMarker(new MarkerOptions().position(e).title( location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(e,15));
                        statusCheckEnd=0;
                    }
                }
            }
        }
    }

    /// dialog  detail bus
    private void DialogDetailBus(){
        txtIDBusDetaildialog = dialogDetailBus.findViewById(R.id.txtIDBusDetaildialog);
        btDongdialogDetailBus = dialogDetailBus.findViewById(R.id.btDongdialogDetailBus);
        btDatXeDialogDetailBus = dialogDetailBus.findViewById(R.id.btDatXeDialogDetailBus);
        txtNameBusDialog = dialogDetailBus.findViewById(R.id.txtDetailNameBus);
        txtLocationBusDialog = dialogDetailBus.findViewById(R.id.txtLocationBusDialog);
        txtChiPhiDialog = dialogDetailBus.findViewById(R.id.txtChiPhiDialog);
        txtloatxe1 = dialogDetailBus.findViewById(R.id.txtloatxe1);
        txttt1 = dialogDetailBus.findViewById(R.id.txttt1);
        txtSDTBusDialog = dialogDetailBus.findViewById(R.id.txtSDTBusDialog);
        txtPointBusDialog = dialogDetailBus.findViewById(R.id.txtPointBusDialog);
        btDongdialogDetailBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDetailBus.dismiss();
            }
        });
        btDatXeDialogDetailBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDetailLocationVND.setText(txtChiPhiDialog.getText().toString());
                txtSDTTran.setText(txtSDTBusDialog.getText().toString());
                if (dLoBegin==0){
                    SendAccuracy(sID,txtIDBusDetaildialog.getText().toString(),
                            txtDetailBeginSearch.getText().toString(),txtDetailEndSearch.getText().toString(),
                            txtPhiVanChuyenSearch.getText().toString(),dMyLatitude+"",
                            dLatEnd+"",dMyLongitude+"",
                            dLoEnd+"");
                }else if(dLoEnd ==0){
                    SendAccuracy(sID,txtIDBusDetaildialog.getText().toString(),  txtDetailBeginSearch.getText().toString(),txtDetailEndSearch.getText().toString(),
                            txtPhiVanChuyenSearch.getText().toString(),dLatBegin+"",dMyLatitude+"",dLoBegin+"",
                            dMyLongitude+"");
                }else if (dLoBegin !=0&& dLatEnd != 0){
                    SendAccuracy(sID,txtIDBusDetaildialog.getText().toString(),  txtDetailBeginSearch.getText().toString(),txtDetailEndSearch.getText().toString(),
                            txtPhiVanChuyenSearch.getText().toString(),dLatBegin+"",dLatEnd+"",
                            dLoBegin+"",dLoEnd+"");
                }

                choXacNhan(txtIDBusDetaildialog.getText().toString());
                Toast.makeText(MainActivity.this, "Đã gửi yếu cầu \n Xin chờ....", Toast.LENGTH_SHORT).show();
            }
        });

    }
    static int  TinhTien(String loaixe,String trongtai,String km){
        int m = Integer.parseInt(km);
        if (loaixe.equals("Xe Tải")){
            if (trongtai.equals("7 Tấn")){
                return m*60+100000;
            }else if (trongtai.equals("5 Tấn")){
                return m*50+60000;
            }else if (trongtai.equals("3 Tấn")){
                return m*45+40000;
            }else if (trongtai.equals("1 Tấn")){
                return m*45+40000;
            }
        }else if (loaixe.equals("Xe Bán Tải")){
            return m*45+30000;
        }else if (loaixe.equals("Xe 3 Gác")){
            return m*30+25000;
        }else if (loaixe.equals("Xe Máy")){
            return m*20+20000;
        }
        return 1;
    }

    private void evenlistview(){
        //listview tim thủ công
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogDetailBus.show();
                SDTBUS(mangBus.get(position).iduser);
                Locale locale = new Locale("en", "EN");
                NumberFormat en = NumberFormat.getInstance(locale);
                String str1 = en.format(TinhTien(mangBus.get(position).loaiXe,mangBus.get(position).trongtai,
                        txtPhiVanChuyenSearch.getText().toString()));
                txtChiPhiDialog.setText( str1+" VND"  );
                txtloatxe1.setText(mangBus.get(position).loaiXe);
                txttt1.setText(mangBus.get(position).trongtai);
                txtIDBusDetaildialog.setText(mangBus.get(position).iduser);
                txtNameBusDialog.setText(mangBus.get(position).Ten);
                txtLocationBusDialog.setText( mangBus.get(position).Phuong+", "+mangBus.get(position).Quan );
                txtPointBusDialog.setText(mangBus.get(position).diem);
            }
        });
        /// listview tìm tự động
        lvSearchLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogDetailBus.show();
                SDTBUS(mangBus.get(position).iduser);
                Locale locale = new Locale("en", "EN");
                NumberFormat en = NumberFormat.getInstance(locale);
                String str1 = en.format(TinhTien(mangBus.get(position).loaiXe,mangBus.get(position).trongtai,
                        txtPhiVanChuyenSearch.getText().toString()));
                txtChiPhiDialog.setText( str1+" VND"  );
                txtloatxe1.setText(mangBus.get(position).loaiXe);
                txttt1.setText(mangBus.get(position).trongtai);
                txtIDBusDetaildialog.setText(mangBus.get(position).iduser);
                txtNameBusDialog.setText(mangBus.get(position).Ten);
                txtLocationBusDialog.setText( mangBus.get(position).Phuong+", "+mangBus.get(position).Quan );
                txtPointBusDialog.setText(mangBus.get(position).diem);
            }
        });
        // lisetview tim theo ID
        lvSearchID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogDetailBus.show();
                SDTBUS(mangBus.get(position).iduser);
                Locale locale = new Locale("en", "EN");
                NumberFormat en = NumberFormat.getInstance(locale);
                String str1 = en.format(TinhTien(mangBus.get(position).loaiXe,mangBus.get(position).trongtai,
                        txtPhiVanChuyenSearch.getText().toString()));
                txtChiPhiDialog.setText( str1+" VND"  );
                txtloatxe1.setText(mangBus.get(position).loaiXe);
                txttt1.setText(mangBus.get(position).trongtai);
                txtIDBusDetaildialog.setText(mangBus.get(position).iduser);
                txtNameBusDialog.setText(mangBus.get(position).Ten);
                txtLocationBusDialog.setText( mangBus.get(position).Phuong+", "+mangBus.get(position).Quan );
                txtPointBusDialog.setText(mangBus.get(position).diem);
            }
        });

    }
/// ánh xạ
    private void inten() {
        txtDetailLocationEnd = findViewById(R.id.txtDetailLocationEnd);
        txtDetailLocationBegin = findViewById(R.id.txtDetailLocationBegin);
        txtDetailLocationVND = findViewById(R.id.txtDetailLocationVND);
        btEditLocationSearch = findViewById(R.id.btEditLocationSearch);
        txtSDTTran = findViewById(R.id.txtSDTTran);
        LinearDetailLocation = findViewById(R.id.LinearDetailLocation);
        txtDetailBeginSearch = findViewById(R.id.txtDetailBeginSearch);
        txtDetailEndSearch = findViewById(R.id.txtDetailEndSearch);
        txtPhiVanChuyenSearch = findViewById(R.id.txtPhiVanChuyenSearch);
        LinearEND = findViewById(R.id.LinearEND);
        LinearTransport = findViewById(R.id.LinearTransport);
        LinearAdd = findViewById(R.id.LinearAdd);
        LinearDetail = findViewById(R.id.LinearDetail);
        LinearAddLocationBus = findViewById(R.id.LinearAddLocationBus);
        btXacNhanLocation = findViewById(R.id.btXacNhanLocation);
        LinearMapTestLocation = findViewById(R.id.LinearMapTestLocation);
        btMyLocationEnd = findViewById(R.id.btMyLocationEnd);
        btMyLocationBegin = findViewById(R.id.btMyLocationBegin);
        btTestLocationEnd = findViewById(R.id.btTestLocationEnd);
        btTestLocationBegin = findViewById(R.id.btTestLocationBegin);
        edtLocationEnd = findViewById(R.id.edtLocationEnd);
        edtLocationBegin = findViewById(R.id.edtLocationBegin);
        lvSearchLocation = findViewById(R.id.lvSearchLocation);
        lvSearchID = findViewById(R.id.lvSearchID);
        LinearHome156 = findViewById(R.id.LinearHome156);
        layoutsearch = findViewById(R.id.layoutsearch);
        lvSearch = findViewById(R.id.lvSearch);
        LinearTimThuCong = findViewById(R.id.LinearTimThuCong);
        LinearTimTheoID = findViewById(R.id.LinearTimTheoID);
        LinearTimTuDong = findViewById(R.id.LinearTimTuDong);
        sptrongtaiSearch = findViewById(R.id.sptrongtaiSearch);
        sptrongtaiSearchAuto = findViewById(R.id.sptrongtaiSearchAuto);
        spLoaixeSearch = findViewById(R.id.spLoaixeSearch);
        spLoaixeSearchAuto  = findViewById(R.id.spLoaixeSearchAuto);
        spPhuongSearch = findViewById(R.id.spPhuongSearch);
        spquanSearch = findViewById(R.id.spquanSearch);
        btTimkiemTuDongSearch = findViewById(R.id.btTimkiemTuDongSearch);
        ContrainLayout153 = findViewById(R.id.ContrainLayout153);
        edtIDXeSearch = findViewById(R.id.edtIDXeSearch);
        btTimkiemIDSearch = findViewById(R.id.btTimkiemIDSearch);
        btTimkiemThuCongSearch = findViewById(R.id.btTimkiemThuCongSearch);
        btTimTheoIDSearch = findViewById(R.id.btTimTheoIDSearch);
        btTimThuCongSearch = findViewById(R.id.btTimThuCongSearch);
        btTimTuDongSearch = findViewById(R.id.btTimTuDongSearch);
        btTimXeSearch =  findViewById(R.id.btTimXeSearch);
        spTinhSearch = findViewById(R.id.spTinhSearch);
        FramContent = findViewById(R.id.FramContent);
        imgSearch = findViewById(R.id.imgS);
        lvSearch = findViewById(R.id.lvSearch);
        imgHome = findViewById(R.id.imgH);
        imgUser = findViewById(R.id.imgU);
    }
    //even lick
    public void onlick(){
        btsetphonedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtsetphonedialog.getText().equals("")){
                    Toast.makeText(MainActivity.this, "Chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }else {
                    setPhoneUser(sID,sName,sEmail,edtsetphonedialog.getText().toString(),sLinkAvatar);
                    getProfileUser(sID);
                    dialogsetphone.dismiss();
                }
            }

        });
    }
    public void setPhoneUser(String id,String name,String email,String phone,String avatar)
    {
        final ProfileUser profileUser = new ProfileUser(avatar,email,name,phone);
        mdatafiresetprofile.child("profileuser/"+id).setValue(profileUser);
        getProfileUser(sID);
    }
    // gte Profile
    public void  getProfileUser(final String s){
        mdatafirebasex= FirebaseDatabase.getInstance().getReference().child("profileuser").child(s);
        mdatafirebasex.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileUser profile = dataSnapshot.getValue(ProfileUser.class);
//                 xác thực user có sdt hay chưa
                sPhone = profile.Phonenumber;
                if (sPhone.equals("") )
                {
                    dialogloading.dismiss();
                    dialogsetphone.show();
                    Toast.makeText(MainActivity.this, "Chưa có sdt", Toast.LENGTH_SHORT).show();
                }
                if (profile.Email.equals("")){
                }else {
                    // set dữ liệu lên biến
                    sName = profile.Name;
                    sEmail = profile.Email;
                    sPhone = profile.Phonenumber;
                    sLinkAvatar= profile.AvatarLink;
                    dialogloading.dismiss();
                    Toast.makeText(MainActivity.this, "Wellcome " +sName, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
// lấy list phuong tiện BUS
    public void getListBus ( ){

        mdatafirebasegetbussearch= FirebaseDatabase.getInstance().getReference();
        mdatafirebasegetbussearch.child("Bus").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bus bus = dataSnapshot.getValue(Bus.class);
//                if (spTinhSearch.getSelectedItem().equals(bus.Quan))
                if (bus.trangthai.equals("1")){
                        if (spTinhSearch.getSelectedItem().equals("Tất cả")){
                            // tất cả tỉnh
                            if (spLoaixeSearch.getSelectedItem().equals("Tất cả")){
                                mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                        bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                busAdapter.notifyDataSetChanged();
                                lvSearch.setAdapter(busAdapter);
                            }else if( spLoaixeSearch.getSelectedItem().equals(bus.loaiXe)) {
                                if (sptrongtaiSearch.getSelectedItem().equals("Tất cả")) {
                                    // code add
                                    mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                            bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                    BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                    busAdapter.notifyDataSetChanged();
                                    lvSearch.setAdapter(busAdapter);
                                } else if (sptrongtaiSearch.getSelectedItem().equals(bus.trongtai)) {
                                    // code add
                                    mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                            bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                    BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                    busAdapter.notifyDataSetChanged();
                                    lvSearch.setAdapter(busAdapter);
                                }
                            }
                        } else if (spTinhSearch.getSelectedItem().equals(bus.Tinh)){
                            // có tỉnh
                             if (spquanSearch.getSelectedItem().equals("Tất cả")){
                                 // quận all
                                if (spLoaixeSearch.getSelectedItem().equals("Tất cả")){
                                    mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                            bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                    BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                    busAdapter.notifyDataSetChanged();
                                    lvSearch.setAdapter(busAdapter);
                                }else if( spLoaixeSearch.getSelectedItem().equals(bus.loaiXe)){
                                    if (sptrongtaiSearch.getSelectedItem().equals("Tất cả")){
                                        // code add
                                        mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                                bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                        BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                        busAdapter.notifyDataSetChanged();
                                        lvSearch.setAdapter(busAdapter);
                                    }else if (sptrongtaiSearch.getSelectedItem().equals(bus.trongtai)){
                                        // code add
                                        mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                                bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                        BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                        busAdapter.notifyDataSetChanged();
                                        lvSearch.setAdapter(busAdapter);
                                    }

                                }
                             }else if (spquanSearch.getSelectedItem().equals(bus.Quan)){
                                 // quận ==
                                 if (spPhuongSearch.getSelectedItem().equals("Tất cả")){
                                     // phường all
                                     //code
                                     if (spLoaixeSearch.getSelectedItem().equals("Tất cả")){
                                         mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                                 bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                         BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                         busAdapter.notifyDataSetChanged();
                                         lvSearch.setAdapter(busAdapter);
                                     }else if( spLoaixeSearch.getSelectedItem().equals(bus.loaiXe)){
                                         if (sptrongtaiSearch.getSelectedItem().equals("Tất cả")){
                                             // code add
                                             mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                                     bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                             BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                             busAdapter.notifyDataSetChanged();
                                             lvSearch.setAdapter(busAdapter);
                                         }else if (sptrongtaiSearch.getSelectedItem().equals(bus.trongtai)){
                                             // code add
                                             mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                                     bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                             BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                             busAdapter.notifyDataSetChanged();
                                             lvSearch.setAdapter(busAdapter);
                                         }

                                     }
                                 }else if (spPhuongSearch.getSelectedItem().equals(bus.Phuong)){
                                     if (spLoaixeSearch.getSelectedItem().equals("Tất cả")){
                                         mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                                 bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                         BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                         busAdapter.notifyDataSetChanged();
                                         lvSearch.setAdapter(busAdapter);
                                     }else if( spLoaixeSearch.getSelectedItem().equals(bus.loaiXe)){
                                         if (sptrongtaiSearch.getSelectedItem().equals("Tất cả")){
                                             // code add
                                             mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                                     bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                             BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                             busAdapter.notifyDataSetChanged();
                                             lvSearch.setAdapter(busAdapter);
                                         }else if (sptrongtaiSearch.getSelectedItem().equals(bus.trongtai)){
                                             // code add
                                             mangBus.add(new Bus(bus.Phuong, bus.Quan, bus.Ten, bus.Tinh, bus.diem, bus.iduser,
                                                     bus.loaiXe, bus.soxe, bus.trangthai, bus.trongtai, bus.viTrix, bus.viTriy));
                                             BusAdapter busAdapter = new BusAdapter(MainActivity.this, R.layout.custom_listview_bus, mangBus);
                                             busAdapter.notifyDataSetChanged();
                                             lvSearch.setAdapter(busAdapter);
                                         }

                                     }
                                 }
                             }
                        }

                }
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
    /// search ID
    private void getBus ( String id){
        mdatafirebasegetbussearch = FirebaseDatabase.getInstance().getReference().child("Bus").child(id);
        mdatafirebasegetbussearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mangBus.clear();
                Bus bus = dataSnapshot.getValue(Bus.class);
                mangBus.add(new Bus(bus.Phuong,bus.Quan,bus.Ten,bus.Tinh,bus.diem,bus.iduser,
                        bus.loaiXe,bus.soxe,bus.trangthai,bus.trongtai,bus.viTrix,bus.viTriy));
                BusAdapter busAdapter = new BusAdapter(MainActivity.this,R.layout.custom_listview_bus, mangBus);
                busAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, bus.iduser, Toast.LENGTH_SHORT).show();
                lvSearchID.setAdapter(busAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    // Search Bus Gần nhất
    public void GetdistanceBus (  ){
        numberKm = 0;
        mdatafirebasegetbussearch= FirebaseDatabase.getInstance().getReference();
        mdatafirebasegetbussearch.child("Bus").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bus bus = dataSnapshot.getValue(Bus.class);
                if (bus.trangthai.equals("1"))
                    if (spLoaixeSearchAuto.getSelectedItem().toString().equals(bus.loaiXe)
                                    && sptrongtaiSearchAuto.getSelectedItem().toString().equals(bus.trongtai) )
                    {
                        float resuls[] = new float[10];
                        Location.distanceBetween(10.807453,106.7157249,
                                Double.parseDouble(bus.viTrix),Double.parseDouble(bus.viTriy),resuls);
                        if (numberKm == 0){
                            mangBus.clear();
                            mangBus.add(new Bus(bus.Phuong,bus.Quan,bus.Ten,bus.Tinh,bus.diem,bus.iduser,
                                    bus.loaiXe,bus.soxe,bus.trangthai,bus.trongtai,bus.viTrix,bus.viTriy));
                            BusAdapter busAdapter = new BusAdapter(MainActivity.this,R.layout.custom_listview_bus, mangBus);
                            busAdapter.notifyDataSetChanged();
                            lvSearchLocation.setAdapter(busAdapter);
                            numberKm =  resuls[0];
                        }else  if (numberKm >= resuls[0]){
                            numberKm = resuls[0];
                                mangBus.clear();
                                mangBus.add(new Bus(bus.Phuong,bus.Quan,bus.Ten,bus.Tinh,bus.diem,bus.iduser,
                                        bus.loaiXe,bus.soxe,bus.trangthai,bus.trongtai,bus.viTrix,bus.viTriy));
                                BusAdapter busAdapter = new BusAdapter(MainActivity.this,R.layout.custom_listview_bus, mangBus);
                                busAdapter.notifyDataSetChanged();
                                lvSearchLocation.setAdapter(busAdapter);
                        }else
                            Toast.makeText(MainActivity.this, "Fail" + bus.Ten, Toast.LENGTH_SHORT).show();

                     }


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
    /// Even Click search
    public  void onClickSearch (View view){
        switch (view.getId()){
            case R.id.imgS:
                checkphoneuser();
                if (statusTranBus==false){
                    LinearTransport.setVisibility(View.GONE);
                    FramContent.setVisibility(View.GONE);
                    LinearHome156.setVisibility(View.VISIBLE);
                }
                if( dstatus == true){
                    LinearHome156.setVisibility(View.GONE);
                    LinearTransport.setVisibility(View.VISIBLE);
                }
                imgSearch.setBackgroundColor(Color.parseColor("#8AA0EC47"));
                imgUser.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                imgHome.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                break;
            case R.id.btTimXeSearch:
                if (countsearch == 1){
                    btTimXeSearch.setVisibility(View.GONE);
                    ContrainLayout153.setVisibility(View.VISIBLE);
                    LinearTimThuCong.setVisibility(View.GONE);
                    LinearTimTheoID.setVisibility(View.GONE);
                    LinearTimTuDong.setVisibility(View.VISIBLE);
                    btTimTuDongSearch.setBackgroundColor(Color.parseColor("#FF22B924"));
                    btTimThuCongSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
                    btTimTheoIDSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
                }else
                    Toast.makeText(this, "Chưa xác nhận thông tin vận chuyển", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btTimTuDongSearch:
                LinearTimThuCong.setVisibility(View.GONE);
                LinearTimTheoID.setVisibility(View.GONE);
                LinearTimTuDong.setVisibility(View.VISIBLE);
                btTimTuDongSearch.setBackgroundColor(Color.parseColor("#FF22B924"));
                btTimThuCongSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
                btTimTheoIDSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
                break;
            case R.id.btTimThuCongSearch:
                LinearTimTuDong.setVisibility(View.GONE);
                LinearTimTheoID.setVisibility(View.GONE);
                LinearTimThuCong.setVisibility(View.VISIBLE);
                btTimThuCongSearch.setBackgroundColor(Color.parseColor("#FF22B924"));
                btTimTuDongSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
                btTimTheoIDSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
                break;
            case R.id.btTimTheoIDSearch:

                LinearTimTuDong.setVisibility(View.GONE);
                LinearTimThuCong.setVisibility(View.GONE);
                LinearTimTheoID.setVisibility(View.VISIBLE);
                btTimTheoIDSearch.setBackgroundColor(Color.parseColor("#FF22B924"));
                btTimTuDongSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
                btTimThuCongSearch.setBackgroundColor(Color.parseColor("#F1EDED"));
                break;
            case R.id.btTimkiemIDSearch:
                lvSearchLocation.setVisibility(View.GONE);
                lvSearch.setVisibility(View.GONE);
                lvSearchID.setVisibility(View.VISIBLE);
                getBus(edtIDXeSearch.getText().toString());
                ContrainLayout153.setVisibility(View.GONE);
                btTimXeSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.btTimkiemThuCongSearch:
                lvSearchLocation.setVisibility(View.GONE);
                lvSearchID.setVisibility(View.GONE);
                lvSearch.setVisibility(View.VISIBLE);
                mangBus.clear();
                getListBus();
                ContrainLayout153.setVisibility(View.GONE);
                btTimXeSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.btTimkiemTuDongSearch:
                lvSearch.setVisibility(View.GONE);
                lvSearchID.setVisibility(View.GONE);
                lvSearchLocation.setVisibility(View.VISIBLE);
                mangBus.clear();
                GetdistanceBus();
                ContrainLayout153.setVisibility(View.GONE);
                btTimXeSearch.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void checkBegin(){
        mapTest = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapTestlocation);
        mapTest.getMapAsync(MainActivity.this);
        clickerCheckBegin=true;
        if (edtLocationBegin.getText().toString().equals("Vị trí hiện tại")){
        }else {
            if(edtLocationBegin.getText().toString().equals("")){
                Toast.makeText(this, "Chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
            }else {
                statusCheckBegin = 1;
                if (markerBegin != null){
                    markerBegin.remove();
                }
                dLoBegin = dLatBegin = 0;
                searchLocation(edtLocationBegin.getText().toString());
                LinearMapTestLocation.setVisibility(View.VISIBLE);
            }
        }
    }
    private void checkEnd(){
        mapTest = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapTestlocation);
        mapTest.getMapAsync(MainActivity.this);

        mMap.setOnMyLocationClickListener(this);
        if (edtLocationEnd.getText().toString().equals("Vị trí hiện tại")){
        }
        if (edtLocationEnd.getText().toString().equals("") ) {
            Toast.makeText(this, "Chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
        }else {
            statusCheckEnd = 1;
            if (markerEnd != null) {
                markerEnd.remove();
            }
            dLoEnd = dLatEnd = 0;
            clickerCheckEnd=true;
            searchLocation(edtLocationEnd.getText().toString());
            LinearMapTestLocation.setVisibility(View.VISIBLE);
        }
    }
    // even click add location trong vận chuyển
    // chua fix lỗi != null
    public void onclickAddLocation(View view){
        switch (view.getId()) {
            case R.id.btXacNhanLocation:

                checkBegin();
                checkEnd();
                if (edtLocationEnd.getText().toString().equals("")||edtLocationBegin.getText().toString().equals("")){
                    Toast.makeText(this, "Thông tin chưa đầy đủ", Toast.LENGTH_SHORT).show();
                }else {

                    range();
                    countsearch = 1;
                    if (edtLocationBegin.getText().toString().equals("Vị trí hiện tại")){

                    }

                    if (edtLocationBegin.getText().equals("")){
                        Toast.makeText(this, "Chưa nhập địa chỉ bắt đầu !", Toast.LENGTH_SHORT).show();
                    }else if (edtLocationBegin.getText().equals("")){
                        Toast.makeText(this, "Chưa nhập địa chỉ kết thúc !", Toast.LENGTH_SHORT).show();
                    }else {
                        txtDetailBeginSearch.setText(edtLocationBegin.getText().toString());
                        txtDetailEndSearch.setText(edtLocationEnd.getText().toString());
                        LinearAdd.setVisibility(View.GONE);
                        LinearDetailLocation.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.btTestLocationEnd:
                checkEnd();
//                }

                break;
            case R.id.btTestLocationBegin:
                checkBegin();

                break;
            case R.id.btMyLocationBegin:
                mapTest = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapTestlocation);
                mapTest.getMapAsync(MainActivity.this);
                if (status==2){
                    Toast.makeText(this, "Bạn đã chọn vị trí của bạn là vị trí kết thúc", Toast.LENGTH_SHORT).show();
                }else if (status == 0 ){
                    status=1;
                    btMyLocationBegin.setText("Hủy vị trí hiện tại");
                    edtLocationBegin.setText("Vị trí hiện tại");
                    LinearMapTestLocation.setVisibility(View.VISIBLE);


                    client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                             onMyLocationClick(location);
                            final Timer TT=new Timer();
                            TT.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {countime++;
                                            if (countime==2 ){
                                                LatLng e = new LatLng(dMyLatitude,dMyLongitude);
                                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(e,15));
                                                countime=0;
                                                TT.cancel();
                                            }
                                        }
                                    });
                                }
                            }, 1000, 1000);
                        }
                    });

                }else if (status == 1){
                    status = 0;
                    dLatBegin = 0;
                    dLoBegin= 0;
                    edtLocationBegin.setText(null);
                    btMyLocationBegin.setText("Vị trí hiện tại");
                }

                break;
            case R.id.btMyLocationEnd:
                mapTest = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapTestlocation);
                mapTest.getMapAsync(MainActivity.this);
                if (status==1){
                    Toast.makeText(this, "Bạn đã chọn vị trí của bạn là vị trí bắt đầu", Toast.LENGTH_SHORT).show();
                }else if (status == 0 ){
                    status = 2;
                    LinearMapTestLocation.setVisibility(View.VISIBLE);
                    client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            onMyLocationClick(location);
                            T=new Timer();
                            T.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {countime++;
                                            if (countime==2 ){
                                                LatLng e = new LatLng(dMyLatitude,dMyLongitude );
                                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(e,15));
                                                countime=0;
                                                T.cancel();
                                            }
                                        }
                                    });
                                }
                            }, 1000, 1000);
                        }
                    });
                    edtLocationEnd.setText("Vị trí hiện tại");
                    btMyLocationEnd.setText("Hủy vị trí hiện tại");
                }else if (status==2){
                    dLatEnd = 0;
                    dLoEnd = 0;
                    status = 0;
                    edtLocationEnd.setText(null);
                    btMyLocationEnd.setText("Vị trí hiện tại");
                }
                break;
            case R.id.btCancelTransport:
               /// code xác thực
                updateTransport(sID,keyhistory+"","Đã hủy vận chuyển");
                updateTransport( txtIDBusDetaildialog.getText().toString(),keyhistory+"","Đã hủy vận chuyển");
                statusTranBus=false;
                dstatus = false;
                mdatafirebasex= FirebaseDatabase.getInstance().getReference().child("Bus").child(txtIDBusDetaildialog.getText().toString());
                mdatafirebasex.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            Bus a = dataSnapshot.getValue(Bus.class);
                            Bus bus = new Bus(a.Phuong,a.Quan,a.Ten,a.Tinh,a.diem
                                    ,a.iduser,a.loaiXe,a.soxe,"1",a.trongtai,a.viTrix,a.viTriy );
                            mdatafiresetprofile.child("Bus/"+a.iduser).setValue(bus);
                            mdatafirebasex.removeEventListener(this);
                        }catch (Exception e){
                            Toast.makeText(MainActivity.this, e+"", Toast.LENGTH_SHORT).show();
                            mdatafirebasex.removeEventListener(this);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(this, "Đã hủy", Toast.LENGTH_SHORT).show();
                LinearTransport.setVisibility(View.GONE);
                LinearEND.setVisibility(View.VISIBLE);
                 final Timer Te=new Timer();
               countimes = 0;
                Te.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {countimes++;
                                 if (countimes==5){
                                    LinearEND.setVisibility(View.GONE);
                                    LinearHome156.setVisibility(View.VISIBLE);
                                    Te.cancel();
                                }
                            }
                        });
                    }
                }, 1000, 1000);
                break;
            case R.id.btEditLocationSearch:
               /// code xác thực
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                LinearDetailLocation.setVisibility(View.GONE);
                LinearAdd.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void toollickaddfragment (View view){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragmentx = null;

        switch (view.getId()){
            case R.id.imgH:
                //add fragment home
                LinearHome156.setVisibility(View.GONE);
                LinearTransport.setVisibility(View.GONE);
                FramContent.setVisibility(View.VISIBLE);
                fragmentx = new FragmentHome();
                checkphoneuser();
                imgHome.setBackgroundColor(Color.parseColor("#8AA0EC47"));
                imgUser.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                imgSearch.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                break;

            case R.id.imgU:
                fragmentx = new FragmentUser();
                LinearHome156.setVisibility(View.GONE);
                LinearTransport.setVisibility(View.GONE);
                FramContent.setVisibility(View.VISIBLE);
                //add fragment FragmentUser
                checkphoneuser();
                imgUser.setBackgroundColor(Color.parseColor("#8AA0EC47"));
                imgSearch.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                imgHome.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                break;
        }
        fragmentTransaction.replace(R.id.FramContent,fragmentx);
        //add Fragment
        fragmentTransaction.commit();

    }

    private void checkphoneuser(){
        if (sPhone.equals(""))
            dialogsetphone.show();
    }

    //// load adater  spinner
    private void loadSpinner (){
        ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(this,R.array.ListThanhPho,android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTinhSearch.setAdapter(spAdapter);
        //
        ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(this,R.array.ListLoaixe,android.R.layout.simple_spinner_item);
        spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaixeSearch.setAdapter(spLoaixeAdapter);
        spLoaixeSearchAuto.setAdapter(spLoaixeAdapter);
        //
        ArrayAdapter<CharSequence> spLoaixeAdapterAuto = ArrayAdapter.createFromResource(this,R.array.ListLoaixeAuto,android.R.layout.simple_spinner_item);
        spLoaixeAdapterAuto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaixeSearchAuto.setAdapter(spLoaixeAdapterAuto);
        //
        ArrayAdapter<CharSequence> spTrongTaiAdapter = ArrayAdapter.createFromResource(this,R.array.ChonTrongTai,android.R.layout.simple_spinner_item);
        spTrongTaiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sptrongtaiSearch.setAdapter(spLoaixeAdapter);
        sptrongtaiSearchAuto.setAdapter(spLoaixeAdapter);
        //

        spLoaixeSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListAll,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearch.setAdapter(spLoaixeAdapter);
                }else if (position == 1){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListTrongTaiXeTai,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearch.setAdapter(spLoaixeAdapter);
                }else if (position == 2){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListTrongTaiXeBanTai,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearch.setAdapter(spLoaixeAdapter);
                }else if (position == 3){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListTrongTaiXe3Gac,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearch.setAdapter(spLoaixeAdapter);
                }else if (position == 4){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListTrongTaiXeMay,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearch.setAdapter(spLoaixeAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // load trong tải Auto

        spLoaixeSearchAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListTrongTaiXeTaiAuto,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearchAuto.setAdapter(spLoaixeAdapter);
                }else if (position == 1){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListTrongTaiXeBanTai,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearchAuto.setAdapter(spLoaixeAdapter);
                }else if (position == 2){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListTrongTaiXe3Gac,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearchAuto.setAdapter(spLoaixeAdapter);
                }else if (position == 3){
                    ArrayAdapter<CharSequence> spLoaixeAdapter = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListTrongTaiXeMay,android.R.layout.simple_spinner_item);
                    spLoaixeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptrongtaiSearchAuto.setAdapter(spLoaixeAdapter);
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
                    ArrayAdapter<CharSequence> spAdapterQuan = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.ListAll,android.R.layout.simple_spinner_item);
                    spAdapterQuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spquanSearch.setAdapter(spAdapterQuan);
                } else if (position == 1){
                    ArrayAdapter<CharSequence> spAdapterQuan = ArrayAdapter.createFromResource(MainActivity.this,R.array.ListQuanHCM,android.R.layout.simple_spinner_item);
                    spAdapterQuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spquanSearch.setAdapter(spAdapterQuan);
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
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.ListAll,android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position== 1){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(MainActivity.this,R.array.ListPhuongQuan1HCM,android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 2 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(MainActivity.this,R.array.ListPhuongQuan2HCM,android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 3 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListPhuongQuan3HCM,
                            android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 4 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListPhuongQuan4HCM,
                            android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 5 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListPhuongQuan5HCM,
                            android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 6 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(MainActivity.this
                            ,R.array.ListPhuongQuan6HCM,
                            android.R.layout.simple_spinner_item);
                    spAdapterQuan1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPhuongSearch.setAdapter(spAdapterQuan1);
                }else if (position == 7 ){
                    ArrayAdapter<CharSequence> spAdapterQuan1 = ArrayAdapter.createFromResource(MainActivity.this
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

    @Override
    public void onLocationChanged(Location location) {
        lastlocation = location;
        if (lastlocation != null)
            currenMarker.remove();
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Here !!");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currenMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(100));

        if (clientgoogle != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(clientgoogle, (com.google.android.gms.location.LocationListener) MainActivity.this);

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.setDraggable(true);
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        /// lấy vi tri
        end_x = marker.getPosition().latitude;
        end_y = marker.getPosition().longitude;
    }

    @Override
    public boolean onMyLocationButtonClick() {
    // vị trí hiện tại
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        buildGoogle();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
    }


    protected synchronized void buildGoogle(){
        clientgoogle= new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .addApi(LocationServices.API)
                .build();
        clientgoogle.connect();
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        dMyLatitude = location.getLatitude();
        dMyLongitude = location.getLongitude();
    }
 
}
