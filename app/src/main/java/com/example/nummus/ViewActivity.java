package com.example.nummus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class ViewActivity extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener mDateSetListenerview;
    EditText mDisplayDateview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        EditText doT;
        Button view;
        DBHelper DB = new DBHelper(this);
        view = findViewById(R.id.btnView1);
        mDisplayDateview = (EditText) findViewById(R.id.txtview);

        mDisplayDateview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ViewActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerview,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerview = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


                String dateview = month + "/" + day + "/" + year;
                mDisplayDateview.setText(dateview);
            }
        };

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doTTxt = mDisplayDateview.getText().toString();
                Cursor res = DB.getdata(doTTxt);
                if(res.getCount()==0){
                    Toast.makeText(ViewActivity.this, "No Transaction Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("doT : "+res.getString(0)+"\n");
                    buffer.append("Time : "+res.getString(1)+"\n");
                    buffer.append("amount : "+res.getString(2)+"\n");
                    buffer.append("reference : "+res.getString(3)+"\n");
                    buffer.append("paymentMethod : "+res.getString(4)+"\n");
                    buffer.append("note : "+res.getString(5)+"\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });
    }
}