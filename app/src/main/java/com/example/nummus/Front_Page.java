package com.example.nummus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Front_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button, button1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        button = (Button) findViewById(R.id.addTransaction);
        button1 = (Button) findViewById(R.id.btnView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMainActivity();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenViewActivity();
            }
        });

    }
    public void OpenMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void OpenViewActivity(){
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }


}