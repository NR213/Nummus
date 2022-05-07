package com.example.nummus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

public class Front_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
     DrawerLayout draw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button, button1, buttonDelete;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        draw = findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, draw, toolbar, R.string.navigation_draweropen, R.string.navigation_drawerclose);
        draw.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView NV = findViewById(R.id.nav_view);
        NV.setNavigationItemSelectedListener(this);

//        button = (Button) findViewById(R.id.addTransaction);
//        button1 = (Button) findViewById(R.id.btnView);
//        buttonDelete = (Button) findViewById(R.id.btndelete1);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OpenMainActivity();
//            }
//        });
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OpenViewActivity();
//            }
//        });
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OpenDeleteActivity();
//            }
//        });

    }
    @Override
    public void onBackPressed(){
        if(draw.isDrawerOpen(GravityCompat.START)){
            draw.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();
        }
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addtransaction:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.viewtransaction:
                Intent intentview = new Intent(this, ViewActivity.class);
                startActivity(intentview);
                break;
            case R.id.deletetransaction:
                Intent intentdel = new Intent(this, DeleteActivity.class);
                startActivity(intentdel);
                break;
        }
        draw.closeDrawer(GravityCompat.START);
        return true;
    }
}