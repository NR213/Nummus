package com.example.nummus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registrationactivity extends AppCompatActivity {
    EditText username, password, repassword;
    Button signup;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationactivity);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#E19F9F"));
        actionBar.setBackgroundDrawable(colorDrawable);
        username = (EditText) findViewById(R.id.txtreguser);
        password = (EditText) findViewById(R.id.txtregpass1);
        repassword = (EditText) findViewById(R.id.txtregpass2);
        signup = (Button) findViewById(R.id.btnsignup);

        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(user.isEmpty()||pass.isEmpty()||repass.isEmpty())
                    Toast.makeText(registrationactivity.this, "Please enter Username and Password", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        if (pass.length() > 5){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false){
                            if(Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                                Boolean insert = DB.insertData(user, pass);

                            if(insert==true){
                                Toast.makeText(registrationactivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),loginactivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(registrationactivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                                Toast.makeText(registrationactivity.this, "Invalid Email address", Toast.LENGTH_SHORT).show();
                        }
                        }
                        else{
                            Toast.makeText(registrationactivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                        }else{
                            Toast.makeText(registrationactivity.this, "Password should be longer than 6 characters", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(registrationactivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });

    }
}

