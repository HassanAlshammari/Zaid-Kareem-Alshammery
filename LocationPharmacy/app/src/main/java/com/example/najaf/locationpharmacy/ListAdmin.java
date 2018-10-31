package com.example.najaf.locationpharmacy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class ListAdmin extends AppCompatActivity {
    private  ListView listViewlocation=null;
    private static ArrayList<Locations> locationsArrayList=null;
    private   Locations locations=null;
    private FirebaseDatabase firebaseDatabase=null;
    private DatabaseReference databaseReference=null;
    private LocationAdapter locationAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_admin);
            ShowDataLocation();

        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private   void  ShowDataLocation() {
        listViewlocation=(ListView)findViewById(R.id.listViewlocation);
        locations=new Locations();
        locationsArrayList=new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("MyDataLocation");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                locationsArrayList.clear();
                for(DataSnapshot dss:dataSnapshot.getChildren())
                {
                    try
                    {
                        locations=dss.getValue(Locations.class);
                        locationsArrayList.add(locations);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
                try
                {
                    locationAdapter=new LocationAdapter(getApplicationContext(),R.layout.location_items,locationsArrayList);
                    listViewlocation.setAdapter(locationAdapter);
                    locationAdapter.notifyDataSetChanged();
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    public void ReShowData()
    {
        startActivity(new Intent(getApplicationContext(),ListAdmin.class));
    }
}
