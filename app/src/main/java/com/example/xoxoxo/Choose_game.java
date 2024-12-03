package com.example.xoxoxo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Choose_game extends AppCompatActivity {


    private Button next;
    private ImageView backBtn;
    private RadioButton friend,robot;
    private RadioGroup radioGroup;
    private  String choice;
    RadioButton rb_friend,rb_robot;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

        next=findViewById(R.id.go_to_activity);
        rb_friend=findViewById(R.id.radiofriend);
        rb_robot=findViewById(R.id.radioRobot);

        choice = "friend";
        rb_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             choice="friend";
            }
        });
        rb_robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice="robot";
            }
        });

      next.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (choice == "friend"){
                  Intent intent =new Intent(Choose_game.this,Addplayers.class);
                  startActivity(intent);
              }else {
                  Intent intent =new Intent(Choose_game.this,Ai_get_side.class);
                  startActivity(intent);
              }
          }
      });

    }
}