package com.example.najaf.locationpharmacy;
import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class DatabaseManagment {
    private DatabaseReference MyDataLocation;
    private   Locations locations;
    private  DatabaseReference RefDelete;
    private ArrayList<Locations> locationslist;
    private  FirebaseDatabase firebaseDatabase;
    private  DatabaseReference databaseReference;

    public  void  SaveData( String LocationName, String LocationAddress, String LocationDescription, final String Log, String Lat, String State)
    {
        MyDataLocation=FirebaseDatabase .getInstance().getReference("MyDataLocation");
        String ID=MyDataLocation.push().getKey();
        locations=new Locations(ID,LocationName,LocationAddress,LocationDescription,Log,Lat,State);
        MyDataLocation.child(ID).setValue(locations);
    }
    public List<Locations> GetData()
    {
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return locationslist;
    }
    public  void  DeleteLocation(Locations locations)
    {
        try
        {
            firebaseDatabase=FirebaseDatabase.getInstance();
            databaseReference=firebaseDatabase.getReference("MyDataLocation");
            databaseReference.child(locations.getID()).removeValue();
        }
        catch (Exception ex)
        {}
    }
    public  void  UpdateLocation(String ID,String LocationName,String LocationAddress,String LocationDescription,String Log,String Lat,String State)
    {
        try
        {
            firebaseDatabase=FirebaseDatabase.getInstance();
            databaseReference=firebaseDatabase.getReference("MyDataLocation");
            Locations locations1=new Locations(ID,LocationName,LocationAddress,LocationDescription,Log,Lat,State);
            databaseReference.child(locations1.getID()).setValue(locations1);
        }
        catch (Exception ex)
        {}
    }
}
