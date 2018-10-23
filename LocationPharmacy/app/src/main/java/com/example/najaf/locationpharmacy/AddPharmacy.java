package com.example.najaf.locationpharmacy;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddPharmacy extends AppCompatActivity {
    EditText txtplacename,txtplacecity,txtdescription;
    String longitude;
    String latitude;
    public  static  Typeface typeface;
    DatabaseManagment databaseManagment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pharmacy);
        Bundle b=getIntent().getExtras();
        this.latitude=b.getString("latitude");
        this.longitude=b.getString("longitude");
        databaseManagment=new DatabaseManagment();
        txtplacename=(EditText)findViewById(R.id.txtplacename);
        txtplacecity=(EditText)findViewById(R.id.txtaddress);
        txtdescription=(EditText)findViewById(R.id.txtdescription);

        Button btnadd =(Button)findViewById(R.id.btnadd);
        TextView textViewtitle= (TextView) findViewById(R.id.textViewtitle);
        typeface = Typeface.createFromAsset(getAssets(),"ae-almothnna-bold.ttf");

        btnadd.setTypeface(typeface);
        textViewtitle.setTypeface(typeface);
        txtplacename.setTypeface(typeface);
        txtplacecity.setTypeface(typeface);
        txtdescription.setTypeface(typeface);

    }
    public  Typeface getTypeface()
    {
        return  Typeface.createFromAsset(getAssets(),"ae-almothnna-bold.ttf");
    }
    public void ClickAdd(View view) {
        if(txtplacename.getText().toString().isEmpty() || txtplacecity.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"لايمكن ترك اسم الصيدلية او المكان فارغ",Toast.LENGTH_LONG).show();
        }
        else
        {
            try
            {
                databaseManagment.SaveData(txtplacename.getText().toString()
                        ,txtplacecity.getText().toString()
                        ,txtdescription.getText().toString()
                        ,longitude
                        ,latitude
                        ,"false");
                Toast.makeText(getApplicationContext(),"انتضر لحين موافقة المسؤول حتى يتم اضافة الصيدلية الجديدة",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                Bundle b = new Bundle();
                b.putString("value", "false");
                intent.putExtras(b);
                startActivity(intent);
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }
}
