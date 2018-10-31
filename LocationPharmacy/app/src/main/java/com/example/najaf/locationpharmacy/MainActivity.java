package com.example.najaf.locationpharmacy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference MyDataAdmin;
    private   AdminLogin adminLogin;
    private  FirebaseDatabase firebaseDatabase;
    private  DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            SaveData("","");
            final Intent intent=new Intent(this,HomeLayout.class);
            Thread timer =new Thread(){
                public void run(){
                    try {
                        sleep(2000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    finally {
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    public  void  SaveData( String AdminName, String Password)
    {
        MyDataAdmin=FirebaseDatabase.getInstance().getReference("AdminLogin");
        String ID=MyDataAdmin.push().getKey();
        adminLogin=new AdminLogin(ID,AdminName,Password);
        MyDataAdmin.child(ID).setValue(adminLogin);
        DeleteLocation(adminLogin);
    }
    public  void  DeleteLocation(AdminLogin adminLogin)
    {
        try
        {
            firebaseDatabase=FirebaseDatabase.getInstance();
            databaseReference=firebaseDatabase.getReference("AdminLogin");
            databaseReference.child(adminLogin.getID()).removeValue();
        }
        catch (Exception ex)
        {}
    }
}
