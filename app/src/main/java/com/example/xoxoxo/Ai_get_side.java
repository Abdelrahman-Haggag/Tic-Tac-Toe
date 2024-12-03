package com.example.xoxoxo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Ai_get_side extends AppCompatActivity {

    private String playerName;
    private EditText playerNameTxt;
    private Button playerButton;
    private ImageView backBtn;
    private RadioButton X,O;
    private RadioGroup radioGroup;
    private  String choice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_get_side);

        playerNameTxt = findViewById(R.id.et_player_name);
        playerButton = findViewById(R.id.ai_player_name_btn);
        backBtn = findViewById(R.id.back_to_choosegame);
        radioGroup = findViewById(R.id.radioGroup);

        choice = "X";
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioX) {
                choice = "X";
            } else if (checkedId == R.id.radioO) {
                choice = "O";
            }
        });

        playerButton.setOnClickListener(v -> {
            playerName = playerNameTxt.getText().toString();
            if (playerName.isEmpty()) {
                Toast.makeText(getBaseContext(), "Enter Name", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(Ai_get_side.this, With_AI.class);
                intent.putExtra("p1", playerName);
                intent.putExtra("playerChoice", choice);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Ai_get_side.this,Choose_game.class);
                startActivity(intent);
            }
        }); // Use onBackPressed to handle back navigation
    }

    @Override
    public void onBackPressed() {
        // Navigate back to Choose_game without causing background music to restart
        super.onBackPressed();
        Intent intent = new Intent(Ai_get_side.this, Choose_game.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish(); // Close the current activity
    }
}