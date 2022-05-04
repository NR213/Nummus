package com.example.nummus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        EditText doT;
        Button delete;
        DBHelper DB = new DBHelper(this);
        delete = findViewById(R.id.btndel);
        doT = findViewById(R.id.txtdel);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dotTXT = doT.getText().toString();
                Boolean checkudeletedata = DB.deletedata(dotTXT);
                if(checkudeletedata==true)
                    Toast.makeText(DeleteActivity.this, "Transaction Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(DeleteActivity.this, "Transaction Doesn't Exist", Toast.LENGTH_SHORT).show();
            }        });
    }
}