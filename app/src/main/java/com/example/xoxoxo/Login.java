package com.example.xoxoxo;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText email, password;
    private Button login, gotoReg;
     XoDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.login_email_et);
        password = findViewById(R.id.login_password_et);
        login = findViewById(R.id.login_btn);
        gotoReg = findViewById(R.id.goto_signUP_btn);
        db=new XoDataBase(this,XoDataBase.TABLE_PERSON,null,XoDataBase.DATABASE_VERSION);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                if (Email.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Password.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (db.checkUser(Email,Password)) {
                    Intent intent=new Intent(Login.this,Choose_game.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Login.this, "your data not found", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        gotoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(Login.this, Register.class);
                startActivity(in);
            }
        });
    }
}