package com.example.najaf.locationpharmacy;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity  extends FragmentActivity implements OnMapReadyCallback,LocationListener {
    final static int PERMISSION_ALL = 1;
    final static String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private GoogleMap mMap;
    MarkerOptions mo;
    Marker marker;
    LocationManager locationManager;
    DatabaseManagment databaseManagment;
    private ArrayList<Locations> locationslist;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private   Locations locations;
    private TextView textViewhave;
    private  Button buttonok;
    private  TextView textViewok;
    private  Typeface typeface;
    private View viewok;
    private ImageView imageViewok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        try
        {

            Bundle b=getIntent().getExtras();
            if(b.getString("value")=="true")
            {
                ShowValue();
            }
            else if(b.getString("value")=="false")
            {
                HidenValue();
            }
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            mo = new MarkerOptions().position(new LatLng(32.243792, 44.765805)).title("My Current Location");
            if (Build.VERSION.SDK_INT >= 23 && !isPermissionGranted()) {
                requestPermissions(PERMISSIONS, PERMISSION_ALL);
            } else requestLocation();
            if (!isLocationEnabled()) showAlert(1);
            databaseManagment=new DatabaseManagment();
            imageViewok =(ImageView)findViewById(R.id.imageok);
            viewok =(View)findViewById(R.id.viewok);
            textViewhave=(TextView)findViewById(R.id.textViewhave);
            buttonok = (Button) findViewById(R.id.ok);
            textViewok= (TextView) findViewById(R.id.textViewok);
            typeface = Typeface.createFromAsset(getAssets(),"ae-almothnna-bold.ttf");
            buttonok.setTypeface(typeface);
            textViewok.setTypeface(typeface);
            textViewhave.setTypeface(typeface);

        }
        catch (Exception ex)
        {}


    }

    private void showAlert(final int status) {
        String message, title, btnText;
        if (status == 1) {
            message = "Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app";
            title = "Enable Location";
            btnText = "Location Settings";
        } else {
            message = "Please allow this app to access location!";
            title = "Permission access";
            btnText = "Grant";
        }

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle(title)

                .setMessage(message).setPositiveButton(btnText, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                if (status == 1) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                } else requestPermissions(PERMISSIONS, PERMISSION_ALL);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                finish();
            }
        });
        dialog.show();
    }


    private void requestLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 10000, 10, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isPermissionGranted() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v("mylog", "Permission is granted");
            return true;
        } else {
            Log.v("mylog", "Permission not granted");
            return false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
        marker.setPosition(myCoordinates);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoordinates, 13));
        mMap.setBuildingsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
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
    public void onMapReady(GoogleMap googleMap) {
        try
        {
            mMap = googleMap;
            marker =  mMap.addMarker(mo);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            mMap.addMarker(new MarkerOptions().position(new LatLng(32.004561, 44.349051)).title("صيدلية المسلة ").snippet("النجف الاشرف")).showInfoWindow();
            mMap.addMarker(new MarkerOptions().position(new LatLng( 32.002000, 44.350155)).title("صيدلية الحرة").snippet("النجف الاشرف")).showInfoWindow();
            mMap.addMarker(new MarkerOptions().position(new LatLng( 32.001844, 44.350254)).title("صيدلية الكرنك").snippet("النجف الاشرف")).showInfoWindow();
            mMap.addMarker(new MarkerOptions().position(new LatLng(32.001098, 44.350594 )).title("صيدلية الشهداء").snippet("النجف الاشرف")).showInfoWindow();
            mMap.addMarker(new MarkerOptions().position(new LatLng(32.000728, 44.350751 )).title("صيدلية بغداد العلمية").snippet("النجف الاشرف")).showInfoWindow();
            mMap.addMarker(new MarkerOptions().position(new LatLng( 32.007084, 44.343280)).title("صيدلية الاندلس").snippet("النجف الاشرف")).showInfoWindow();
            mMap.addMarker(new MarkerOptions().position(new LatLng(32.016741, 44.369996 )).title("صيدلية باب الريان").snippet("النجف الاشرف")).showInfoWindow();
            mMap.addMarker(new MarkerOptions().position(new LatLng( 32.036488, 44.336269)).title("صيدلية الذهب").snippet("النجف الاشرف")).showInfoWindow();
            mMap.addMarker(new MarkerOptions().position(new LatLng( 32.024807, 44.390550)).title("صيدلية حسين الشاعر").snippet("النجف الاشرف")).showInfoWindow();
            mMap.addMarker(new MarkerOptions().position(new LatLng( 32.022992, 44.387763)).title("صيدلية هلا").snippet("النجف الاشراف")).showInfoWindow();
            mMap.setBuildingsEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            try
            {

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        Intent intent = new Intent(getApplicationContext(), AddPharmacy.class);
                        Bundle b = new Bundle();
                        b.putString("latitude", String.valueOf(latLng.latitude));
                        b.putString("longitude", String.valueOf(latLng.longitude));
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });


            }
            catch (Exception ex)
            {

            }
            try {
                locations=new Locations();
                locationslist=new ArrayList<>();
                firebaseDatabase=FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference("MyDataLocation");
                databaseReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dss:dataSnapshot.getChildren())
                        {
                            locations=dss.getValue(Locations.class);
                            locationslist.add(locations);

                        }
                        for (Locations i : locationslist) {
                            if(i.getState().contains("true"))
                            {
                                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(i.getLat()), Double.parseDouble(i.getLog()))).title(String.valueOf(i.getLocationName())).snippet(String.valueOf(i.getLocationAddress()+" "+i.getLocationDescription()))).showInfoWindow();
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

            }
            catch (Exception ex)
            {

            }

        }
        catch (Exception ex)
        {

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void ok(View view) {
        HidenValue();
//        ImageView imageViewok =(ImageView)findViewById(R.id.imageok);
//        View viewok =(View)findViewById(R.id.viewok);
//        Button buttonok =(Button)findViewById(R.id.ok);
//        TextView textViewok= (TextView) findViewById(R.id.textViewok);
//        TextView textViewhave=(TextView)findViewById(R.id.textViewhave);
//
//        imageViewok.setVisibility(View.INVISIBLE);
//        viewok.setVisibility(View.INVISIBLE);
//        buttonok.setVisibility(View.INVISIBLE);
//        textViewok.setVisibility(View.INVISIBLE);
//        textViewhave.setVisibility(View.INVISIBLE);

        //        android:visibility="invisible"

    }
    public  void  HidenValue()
    {
        imageViewok.setVisibility(View.INVISIBLE);
        viewok.setVisibility(View.INVISIBLE);
        buttonok.setVisibility(View.INVISIBLE);
        textViewok.setVisibility(View.INVISIBLE);
        textViewhave.setVisibility(View.INVISIBLE);
    }
    public void ShowValue() {

        imageViewok.setVisibility(View.VISIBLE);
        viewok.setVisibility(View.VISIBLE);
        buttonok.setVisibility(View.VISIBLE);
        textViewok.setVisibility(View.VISIBLE);
        textViewhave.setVisibility(View.VISIBLE);

        //        android:visibility="invisible"

    }
}
