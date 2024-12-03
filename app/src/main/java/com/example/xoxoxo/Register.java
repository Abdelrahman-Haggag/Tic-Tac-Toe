package com.example.xoxoxo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {
    EditText regEmail,regPassword,conPassword;
    Button sign_up,gotoLogin;
    private XoDataBase db;
    private String Email,Password,regPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        db=new XoDataBase(this,XoDataBase.TABLE_PERSON,null,XoDataBase.DATABASE_VERSION);

        regEmail=findViewById(R.id.reg_email_et);
        regPassword=findViewById(R.id.reg_password_et);
        conPassword=findViewById(R.id.reg_confirm_password_et);
        sign_up=findViewById(R.id.reg_btn);
        gotoLogin=findViewById(R.id.goto_login_btn);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=regEmail.getText().toString();
                Password=regPassword.getText().toString();
                regPass=conPassword.getText().toString();
                if (Email.isEmpty()) {
                    Toast.makeText(Register.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Password.isEmpty()) {
                    Toast.makeText(Register.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(Password.equals(regPass))) {
                    Toast.makeText(Register.this, "Please enter same password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (db.insertUser(new User(Email, Password))) {
                    Intent intent=new Intent(Register.this,Login.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Register.this, "This email already has an account.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

    }
}