package com.example.nummus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class Front_Page extends AppCompatActivity  {
     DrawerLayout draw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button, button1, buttonDelete;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);


        button = (Button) findViewById(R.id.addTransaction);
        button1 = (Button) findViewById(R.id.btnView);
        buttonDelete = (Button) findViewById(R.id.btndelete1);
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
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDeleteActivity();
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
    public void OpenDeleteActivity(){
        Intent intent = new Intent(this, DeleteActivity.class);
        startActivity(intent);
    }



}