package com.example.xoxoxo;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Setting extends AppCompatActivity {
    ImageView btn_music,btn_sound;
    String music,sound;
    MediaPlayer mp;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        btn_music=findViewById(R.id.music_btn);
        btn_sound=findViewById(R.id.sound_btn);
        music="music";
        sound="sound";
        btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(music==""){
                   btn_music.setImageResource(R.drawable.toggle);
               }else {
                   btn_music.setImageResource(R.drawable.togglebutton);
               }

            }
        });
        btn_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound==""){
                    btn_music.setImageResource(R.drawable.toggle);
                }else {
                    btn_music.setImageResource(R.drawable.togglebutton);
                }
            }
        });

    }
}