package com.example.nummus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nummus.databinding.FragmentHomeBinding;
import com.example.nummus.ui.home.HomeFragment;

public class Earnings extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String type, category, source, fixed, primarykeyvalue;

    Spinner fixedinstallment, Category, Type, Source;
    EditText Reason;
    Button Submit;
    private FragmentHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#E19F9F"));
        actionBar.setBackgroundDrawable(colorDrawable);
//        Type = findViewById(R.id.typeEarning);
//        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.Earningtype, android.R.layout.simple_spinner_item);
//        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Type.setAdapter(adapterType);
//        Type.setOnItemSelectedListener(this);

        Category = findViewById(R.id.categoryEarning);
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.CategoryEarnings, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category.setAdapter(adapterCategory);
        Category.setOnItemSelectedListener(this);

//        fixedinstallment = findViewById(R.id.fixedpopupEarning);
//        ArrayAdapter<CharSequence> adapterfixedinstallment = ArrayAdapter.createFromResource(this, R.array.fixedinstallment, android.R.layout.simple_spinner_item);
//        adapterfixedinstallment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        fixedinstallment.setAdapter(adapterfixedinstallment);
//        fixedinstallment.setOnItemSelectedListener(this);
//        fixedinstallment.setEnabled(false);


        Source = findViewById(R.id.sourceEarning);
        ArrayAdapter<CharSequence> adapterSource = ArrayAdapter.createFromResource(this, R.array.SourceEarnings, android.R.layout.simple_spinner_item);
        adapterSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Source.setAdapter(adapterSource);
        Source.setOnItemSelectedListener(this);

        Reason = findViewById(R.id.ReasonEarning);
        Submit = findViewById(R.id.btnsubmitEarning);
        DBHelper DB = new DBHelper(this);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ReasonTxt = Reason.getText().toString();
                String dotTXT = getIntent().getExtras().getString("dotTXT");
                String time = getIntent().getExtras().getString("time");
                String amountTXT = getIntent().getExtras().getString("amountTXT");
                String currency = getIntent().getExtras().getString("currency");
                String valuecurrency = getIntent().getExtras().getString("valuecurrency");
                String paymentmethodTXT = getIntent().getExtras().getString("paymentmethodTXT");
                String noteTXT = getIntent().getExtras().getString("noteTXT");
                String Cat = getIntent().getExtras().getString("Cat");

                Boolean checkinsertpagedata = DB.insertuserdata(dotTXT, time, amountTXT, currency,valuecurrency, paymentmethodTXT, noteTXT, Cat);
                if (checkinsertpagedata == true){
                    datainsert();
                }
                //int key = Integer.parseInt(primarykeyvalue);
                type = "one time payment";
                Boolean checkinsertdata = DB.insertEarningsdata(primarykeyvalue,type, category, source, ReasonTxt);
                if (checkinsertdata == true && checkinsertpagedata == true) {
                    Toast.makeText(Earnings.this, "New Transaction Added", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(Earnings.this, "New Transaction Not Added", Toast.LENGTH_SHORT).show();

                Intent intent  = new Intent(getApplicationContext(), Navigationactivity.class);
                startActivity(intent);

            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if(adapterView.getId() == R.id.typeEarning){
//            type = adapterView.getItemAtPosition(i).toString();
//            if(type.equals("Fixed Earning")){
//                fixedinstallment.setEnabled(true);
//            }else{
//
//                fixedinstallment.setEnabled(false);
//            }
//
//        }
//        if(adapterView.getId() == R.id.fixedpopupEarning){
//            fixed = adapterView.getItemAtPosition(i).toString();
//
//        }
        if(adapterView.getId() == R.id.categoryEarning){
            category = adapterView.getItemAtPosition(i).toString();
        }

        if(adapterView.getId() == R.id.sourceEarning){
            source = adapterView.getItemAtPosition(i).toString();
        }

    }

    public void datainsert(){
        DBHelper DB = new DBHelper(this);

        Cursor cursor = DB.getprimarykey();
        if(cursor.getCount()==0)
        {

        }
        else
        {
            while(cursor.moveToNext())
            {
                primarykeyvalue = cursor.getString(0);

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}