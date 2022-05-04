package com.example.nummus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class DeleteActivity extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener mDateSetListenerdelete;
    EditText mDisplayDatedelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        EditText doT;
        Button delete;
        DBHelper DB = new DBHelper(this);
        delete = findViewById(R.id.btndel);

        mDisplayDatedelete = (EditText) findViewById(R.id.txtdel);

        mDisplayDatedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DeleteActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerdelete,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerdelete = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


                String dateview = month + "/" + day + "/" + year;
                mDisplayDatedelete.setText(dateview);
            }
        };

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dotTXT = mDisplayDatedelete.getText().toString();
                Boolean checkudeletedata = DB.deletedata(dotTXT);
                if(checkudeletedata==true)
                    Toast.makeText(DeleteActivity.this, "Transaction Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(DeleteActivity.this, "Transaction Doesn't Exist", Toast.LENGTH_SHORT).show();
            }        });
    }
}