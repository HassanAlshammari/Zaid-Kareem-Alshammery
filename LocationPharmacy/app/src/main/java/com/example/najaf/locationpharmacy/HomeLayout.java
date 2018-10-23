package com.example.najaf.locationpharmacy;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);

        Writingbtn(R.id.btnshowpharmacy);
        Writingbtn(R.id.btnManage);
        Writingbtn(R.id.btnabout);


    }


    public void Writingbtn(int x){
        Button button = (Button) findViewById(x);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"ae-almothnna-bold.ttf");
        button.setTypeface(typeface);
    }

    public void ClickShow(View view) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        Bundle b = new Bundle();
        b.putString("value", "true");
        intent.putExtras(b);
        startActivity(intent);
    }

    public void ManagClick(View view) {
        startActivity(new Intent(getApplicationContext(),PasswordAdmin.class));
    }

    public void ClickAbout(View view) {
        startActivity(new Intent(getApplicationContext(),About.class));
    }
}
