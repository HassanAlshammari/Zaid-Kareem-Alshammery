package com.example.najaf.locationpharmacy;

import android.content.Context;
import android.graphics.Typeface;
import android.service.autofill.Dataset;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class LocationAdapter extends BaseAdapter {
    private Context context;
    private View row;
    private   Locations locationsproperty;
    private  int layout;
    private ArrayList<Locations> locations;
    private  FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private  Locations locations1;
    public LocationAdapter(Context context, int layout, ArrayList<Locations> locations) {
        this.context = context;
        this.layout = layout;
        this.locations = locations;
    }


    @Override
    public int getCount() {
        return this.locations.size();
    }

    @Override
    public Object getItem(int position) {
        return this.locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private  class  ViewHolder
    {
        TextView LocationName;
        TextView LocationAddress;
        TextView LocationDescription;
        Button buttonDelete;
        Button buttonOK;
        Button buttonvisible;

    }
    ViewHolder finalHolder1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row=convertView;
        ViewHolder holder =new ViewHolder();
        if(row==null)
        {
            try
            {
                LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row=layoutInflater.inflate(layout,null);
                holder.LocationName=(TextView)row.findViewById(R.id.textplacename);
                holder.LocationAddress=(TextView)row.findViewById(R.id.txtaddress);
                holder.LocationDescription=(TextView)row.findViewById(R.id.txtdescription);
                holder.buttonDelete=(Button)row.findViewById(R.id.btndelete);
                holder.buttonOK=(Button)row.findViewById(R.id.btnok);
                holder.buttonvisible=(Button)row.findViewById(R.id.buttonvisible);
                row.setTag(holder);
            }
            catch (Exception ex)
            {}

        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }
        try
        {
            locations1 =this.locations.get(position);
            holder.LocationName.setText(locations1.getLocationName());
            holder.LocationAddress.setText(locations1.getLocationAddress());
            holder.LocationDescription.setText(locations1.getLocationDescription());
            if(locations1.getState().trim()=="true")
            {
                finalHolder1.buttonOK.setEnabled(false);
                finalHolder1.buttonvisible.setEnabled(true);
            }
            else if(locations1.getState().trim()=="false")
            {
                finalHolder1.buttonOK.setEnabled(true);
                finalHolder1.buttonvisible.setEnabled(false);
            }


        }
        catch (Exception ex)
        { }


        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
                    Query query=ref.child("MyDataLocation").orderByChild("id").equalTo(locations1.getID());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                            {
                                dataSnapshot1.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("", "onCancelled", databaseError.toException());
                        }
                    });
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        final ViewHolder finalHolder = holder;
        holder.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("MyDataLocation");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                String uid=data.child("id").getValue().toString();
                                if(uid.equals((locations1.getID())))
                                {
                                    String keyid=data.getKey();
                                    ref.child(keyid).child("locationName").setValue(locations1.getLocationName());
                                    ref.child(keyid).child("locationAddress").setValue(locations1.getLocationAddress());
                                    ref.child(keyid).child("locationDescription").setValue(locations1.getLocationDescription());
                                    ref.child(keyid).child("log").setValue(locations1.getLog());
                                    ref.child(keyid).child("lat").setValue(locations1.getLat());
                                    ref.child(keyid).child("state").setValue("true");
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("", "onCancelled", databaseError.toException());
                        }
                    });
                    finalHolder.buttonOK.setEnabled(false);
                    finalHolder.buttonvisible.setEnabled(true);
                }
                catch (Exception ex)
                {
                    Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.buttonvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("MyDataLocation");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                String uid=data.child("id").getValue().toString();
                                if(uid.equals((locations1.getID())))
                                {
                                    String keyid=data.getKey();
                                    ref.child(keyid).child("locationName").setValue(locations1.getLocationName());
                                    ref.child(keyid).child("locationAddress").setValue(locations1.getLocationAddress());
                                    ref.child(keyid).child("locationDescription").setValue(locations1.getLocationDescription());
                                    ref.child(keyid).child("log").setValue(locations1.getLog());
                                    ref.child(keyid).child("lat").setValue(locations1.getLat());
                                    ref.child(keyid).child("state").setValue("false");
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("", "onCancelled", databaseError.toException());
                        }
                    });
                    finalHolder.buttonOK.setEnabled(true);
                    finalHolder.buttonvisible.setEnabled(false);
                }
                catch (Exception ex)
                {
                    Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        return row;
    }
}
