package com.example.najaf.locationpharmacy;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ApplicationAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_about);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"ae-almothnna-bold.ttf");

        TextView textversion=(TextView)findViewById(R.id.textversion);
        TextView textpharmacy=(TextView)findViewById(R.id.textpharmacy);
        textversion.setTypeface(typeface);
        textpharmacy.setTypeface(typeface);


    }
}
