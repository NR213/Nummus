package com.example.nummus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nummus.ui.home.HomeFragment;

import java.lang.ref.Reference;

public class CostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String type, category, source, fixed;
    Spinner fixedinstallment, Category, Type, Source;
    EditText Reason;
    Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#E19F9F"));
        actionBar.setBackgroundDrawable(colorDrawable);
        Type = findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Type.setAdapter(adapterType);
        Type.setOnItemSelectedListener(this);

        Category = findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category.setAdapter(adapterCategory);
        Category.setOnItemSelectedListener(this);

        fixedinstallment = findViewById(R.id.fixedpopup);
        ArrayAdapter<CharSequence> adapterfixedinstallment = ArrayAdapter.createFromResource(this, R.array.fixedinstallment, android.R.layout.simple_spinner_item);
        adapterfixedinstallment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fixedinstallment.setAdapter(adapterfixedinstallment);
        fixedinstallment.setOnItemSelectedListener(this);
        fixedinstallment.setEnabled(false);

        Source = findViewById(R.id.source);
        ArrayAdapter<CharSequence> adapterSource = ArrayAdapter.createFromResource(this, R.array.consumer, android.R.layout.simple_spinner_item);
        adapterSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Source.setAdapter(adapterSource);
        Source.setOnItemSelectedListener(this);

        Reason = findViewById(R.id.Reason);
        Submit = findViewById(R.id.btnsubmit);
        DBHelper DB = new DBHelper(this);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ReasonTxt = Reason.getText().toString();

                Boolean checkinsertdata = DB.insertCostdata(type, category, source, ReasonTxt);
                if (checkinsertdata == true)
                    Toast.makeText(CostActivity.this, "New Transaction Added", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(CostActivity.this, "New Transaction Not Added", Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.type){
            type = adapterView.getItemAtPosition(i).toString();
            if(type.equals("Fixed installment")){
                fixedinstallment.setEnabled(true);
            }else{

                fixedinstallment.setEnabled(false);
            }
        }
        if(adapterView.getId() == R.id.category){
            category = adapterView.getItemAtPosition(i).toString();
        }
        if(adapterView.getId() == R.id.fixedpopup){
            fixed = adapterView.getItemAtPosition(i).toString();

        }
        if(adapterView.getId() == R.id.source){
            source = adapterView.getItemAtPosition(i).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}