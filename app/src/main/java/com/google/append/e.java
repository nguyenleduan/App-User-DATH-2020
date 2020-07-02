package com.google.append;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class e extends AppCompatActivity  implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener,
        LocationListener,GoogleMap.OnMarkerDragListener
        ,GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationClickListener , GoogleMap.OnMyLocationButtonClickListener {

    DatabaseReference mdatafiresetprofile,mdatafiregetprofile;
    int countime = 1;
    private static GoogleMap mMap;
    TextView t,rr;
    private FusedLocationProviderClient client;
    double x , y ,end_x,end_y ;
    Timer T;
    LatLng latLngxx;
    Button bt,bx;
    String sid = "123";
    private GoogleApiClient clientgoogle;
    private LocationRequest locationRequest;
    private Location lastlocation , los;
    private Marker currenMarker;
    public  static  final int RE_LOCATION_CODE = 99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e);
        t = findViewById(R.id.t);
        bt = findViewById(R.id.bt);
        bx = findViewById(R.id.bx);

        mdatafiresetprofile= FirebaseDatabase.getInstance().getReference();
        T=new Timer();
        client = LocationServices.getFusedLocationProviderClient(this);
//         getProfile("11s");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code google
                end_x= 10.8158499; // atm AG
                end_y= 106.7124634 ;
                x = 110.8061908 ; //cầy bến ngoc
                y =  106.7110137;
                mMap.clear();
                float resul[] = new float[10];
                Location.distanceBetween(x,y,end_x,end_y,resul);
                bt.setText(resul[0]+"");
            }
        });

        getBusLocation();
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            checklocation();
        }
        /// gole maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public void  getBusLocation( ){
        mdatafiregetprofile= FirebaseDatabase.getInstance().getReference().child("Bus").child("id1531212");
        mdatafiregetprofile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double a  , b;
                Bus bussss = dataSnapshot.getValue(Bus.class);
                a = Double.parseDouble(bussss.viTrix);
                b = Double.parseDouble(bussss.viTriy);
                Toast.makeText(e.this, a+" "+b, Toast.LENGTH_SHORT).show();
                mMap.clear();
                LatLng sydney = new LatLng(a,b);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
////////
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                  buildGoogle();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        }
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        T.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {countime++;
                    client.getLastLocation().addOnSuccessListener(e.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                           onMyLocationClick(location);
                        }
                    });
                }
            });
        }
    }, 1000, 4000);

    }

    protected synchronized void buildGoogle(){
        clientgoogle= new GoogleApiClient.Builder(e.this)
                .addConnectionCallbacks(e.this)
                .addOnConnectionFailedListener(e.this)
                .addApi(LocationServices.API)
                .build();

        clientgoogle.connect();
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


    // không cần !!!
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch  (requestCode){
//            case RE_LOCATION_CODE:
//                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                    if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//                        if (clientgoogle == null){
//                            buildGoogle();
//
//                        }
//                        mMap.setMyLocationEnabled(true);
//
//                    }
//                }else {
//                    Toast.makeText(this, "Permission 153", Toast.LENGTH_SHORT).show();
//                }
//                return;
//
//        }
    }
    // khong cần !!!
    public boolean checklocation(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
//
//                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},RE_LOCATION_CODE);
//                }
//            else
//                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},RE_LOCATION_CODE);
//            return false;
//
//        }else
            return true;
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
            LocationServices.FusedLocationApi.removeLocationUpdates(clientgoogle, (com.google.android.gms.location.LocationListener) e.this);

        }

    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
           // LocationServices.FusedLocationApi.requestLocationUpdates(clientgoogle, locationRequest, (com.google.android.gms.location.LocationListener) this);
        }
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
        end_x = marker.getPosition().latitude;
        end_y = marker.getPosition().longitude;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, location.getLatitude()+"", Toast.LENGTH_SHORT).show();
        final Bus profileUser = new Bus("test","test","test","test","test","id1531212","test",
                "test","test","test",location.getLatitude()+"",location.getLongitude()+"");
        mdatafiresetprofile.child("Bus/"+"id1531212").setValue(profileUser);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this,  " easddd", Toast.LENGTH_SHORT).show();

        return false;
    }


    /////

//        //
//        private void res() {
//            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
//        }
//
//        T.scheduleAtFixedRate(new TimerTask() {
//        @Override
//        public void run() {
//            runOnUiThread(new Runnable()
//            {
//                @Override
//                public void run()
//                {countime++;
//                    client.getLastLocation().addOnSuccessListener(e.this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            if (location != null) {
//                                mMap.clear();
//                                res();
//                                Toast.makeText(e.this, "true", Toast.LENGTH_SHORT).show();
//                                x = location.getLatitude() ;
//                                y = location.getLongitude() ;
//                                t.append("----------\nCount Time: "+countime+"\nX:"+x+"\nY:"+y+"\n");
//                                LatLng sydney = new LatLng(  x,y);
//                                mMap.addMarker(new MarkerOptions().position(sydney).title("re" + x));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//                                addbus("asd","tres","tres","tres","tres","11s","11s","tres","tres","tres"
//                                        ,x+"",y+"");
//                            }
//                        }
//                    });
//                }
//            });
//        }
//    }, 1000, 5000);

}
