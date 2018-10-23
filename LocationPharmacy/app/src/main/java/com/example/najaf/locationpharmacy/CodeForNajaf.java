package com.example.najaf.locationpharmacy;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CodeForNajaf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_for_najaf);


        Typeface typeface = Typeface.createFromAsset(getAssets(),"ae-almothnna-bold.ttf");
        TextView textcodeforiraq=(TextView)findViewById(R.id.textcodeforiraq);
        textcodeforiraq.setTypeface(typeface);


    }
}
