package com.example.nummus;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText amount, reference, doT, paymentMethod, note;
    Button insert, update, delete, view;
    String paymentmethodTXT;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amount);
        reference = findViewById(R.id.reference);
        doT = findViewById(R.id.doT);
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
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountTXT = amount.getText().toString();
                String referenceTXT = reference.getText().toString();
                String dotTXT = doT.getText().toString();
                //String paymentmethodTXT = paymentMethod.getText().toString();

                String noteTXT = note.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(dotTXT, amountTXT, referenceTXT, paymentmethodTXT, noteTXT);
                if(checkinsertdata==true)
                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountTXT = amount.getText().toString();
                String referenceTXT = reference.getText().toString();
                String dotTXT = doT.getText().toString();
                //String paymentmethodTXT = paymentMethod.getText().toString();
                String noteTXT = note.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(dotTXT, amountTXT, referenceTXT, paymentmethodTXT, noteTXT);
                if(checkupdatedata==true)
                    Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
            }        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dotTXT = doT.getText().toString();
                Boolean checkudeletedata = DB.deletedata(dotTXT);
                if(checkudeletedata==true)
                    Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
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