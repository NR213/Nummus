package com.example.nummus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginactivity extends AppCompatActivity {
    EditText email, password, username;
    Button btnlogin, btnregister;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        //username = (EditText) findViewById(R.id.txtusername);
        email = (EditText) findViewById(R.id.txtuser);
        password = (EditText) findViewById(R.id.txtpass);
        btnlogin = (Button) findViewById(R.id.btnsignin);
        btnregister = (Button) findViewById(R.id.btnregister);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#E19F9F"));
        actionBar.setBackgroundDrawable(colorDrawable);
        DB = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = email.getText().toString();
                String pass = password.getText().toString();

                if(user.isEmpty()||pass.isEmpty())
                    Toast.makeText(loginactivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        Toast.makeText(loginactivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), Navigationactivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(loginactivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), registrationactivity.class);
                startActivity(intent);
            }
        });
    }

    public String getemail(){
       return email.getText().toString();
    }
}