package com.example.najaf.locationpharmacy;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PasswordAdmin extends AppCompatActivity {
    EditText AdminName;
    EditText AdminPassword;
    Button buttonshow;
    private ArrayList<AdminLogin> adminLoginsList;
    private AdminLogin adminLogin=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_admin);
        AdminName=(EditText)findViewById(R.id.txtAdminName);
        AdminPassword=(EditText)findViewById(R.id.txtpasswordAdmin);
        buttonshow=(Button)findViewById(R.id.buttonShow);
        TextView textViewtitle=findViewById(R.id.textViewtitle11);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"ae-almothnna-bold.ttf");
        AdminName.setTypeface(typeface);
        textViewtitle.setTypeface(typeface);
        AdminPassword.setTypeface(typeface);
        buttonshow.setTypeface(typeface);
    }


    public void ClickShowSettings(View view) {
        try
        {
            buttonshow.setEnabled(false);
            adminLoginsList=new ArrayList<>();
            adminLogin=new AdminLogin();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("AdminLogin");
            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    adminLoginsList.clear();
                    for(DataSnapshot dss:dataSnapshot.getChildren())
                    {
                        try
                        {
                            adminLogin=dss.getValue(AdminLogin.class);
                            adminLoginsList.add(adminLogin);
                        }
                        catch (Exception ex)
                        {
                            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                            buttonshow.setEnabled(true);
                        }

                    }
                    String AdminNameValue=AdminName.getText().toString();
                    String AdminPasswordValue=AdminPassword.getText().toString();
                    if(AdminNameValue.isEmpty() || AdminPasswordValue.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"لايمكن ترك اي شيء فارغ",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        for(AdminLogin adminLogin:adminLoginsList)
                        {
                            if(adminLogin.getAdminName().equals(AdminNameValue) && adminLogin.getPassword().equals(AdminPasswordValue))
                            {
                                startActivity(new Intent(getApplicationContext(),ListAdmin.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"ادخالك خاطئ",Toast.LENGTH_LONG).show();
                                buttonshow.setEnabled(true);
                            }
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
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
