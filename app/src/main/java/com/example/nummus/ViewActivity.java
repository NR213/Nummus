package com.example.nummus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        EditText doT;
        Button view;
        DBHelper DB = new DBHelper(this);
        view = findViewById(R.id.btnView1);
        doT = findViewById(R.id.doT1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doTTxt = doT.getText().toString();
                Cursor res = DB.getdata(doTTxt);
                if(res.getCount()==0){
                    Toast.makeText(ViewActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("doT : "+res.getString(0)+"\n");
                    buffer.append("amount : "+res.getString(1)+"\n");
                    buffer.append("reference : "+res.getString(2)+"\n");
                    buffer.append("paymentMethod : "+res.getString(3)+"\n");
                    buffer.append("note : "+res.getString(4)+"\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });
    }
}