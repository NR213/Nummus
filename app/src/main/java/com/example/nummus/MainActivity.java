package com.example.nummus;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText amount, reference, doT, paymentMethod, note;
    Button insert, update, delete, view;
    String paymentmethodTXT;
    DBHelper DB;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    EditText mDisplayDate;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        amount = findViewById(R.id.amount);
        reference = findViewById(R.id.reference);

        //paymentMethod = findViewById(R.id.paymentMethod);
        Spinner paymentMethod = findViewById(R.id.paymentMethod);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.paymentMethodlist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethod.setAdapter(adapter);
        paymentMethod.setOnItemSelectedListener(this);
        note = findViewById(R.id.note);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.addTransaction);
        view = findViewById(R.id.btnView);
        DB = new DBHelper(this);



        mDisplayDate = (EditText) findViewById(R.id.doTmain);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            MainActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mDateSetListener,
                            year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });

            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;


                    date = month + "/" + day + "/" + year;
                    mDisplayDate.setText(date);
                }
            };

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountTXT = amount.getText().toString();
                String referenceTXT = reference.getText().toString();

                String dotTXT = mDisplayDate.getText().toString();
                //String paymentmethodTXT = paymentMethod.getText().toString();

                String noteTXT = note.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(dotTXT, amountTXT, referenceTXT, paymentmethodTXT, noteTXT);
                if(checkinsertdata==true)
                    Toast.makeText(MainActivity.this, "New Transaction Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Transaction Not Inserted", Toast.LENGTH_SHORT).show();
            }        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountTXT = amount.getText().toString();
                String referenceTXT = reference.getText().toString();
                String dotTXT = doT.getText().toString();
                //String paymentmethodTXT = paymentMethod.getText().toString();
                String noteTXT = note.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(date, amountTXT, referenceTXT, paymentmethodTXT, noteTXT);
                if(checkupdatedata==true)
                    Toast.makeText(MainActivity.this, "Transaction Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Transaction Not Updated", Toast.LENGTH_SHORT).show();
            }        });



    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        paymentmethodTXT = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
