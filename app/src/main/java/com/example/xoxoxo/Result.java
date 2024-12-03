package com.example.xoxoxo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class Result extends Dialog {

    String message;
    MainActivity mainActivity;

    public Result(@NonNull Context context, String message, MainActivity mainActivity) {
        super(context);

        this.message = message;
        this.mainActivity = mainActivity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView messageText = findViewById(R.id.messageText);
        Button btnStartAgain = findViewById(R.id.btnStartAgain);


        messageText.setText(message);
        btnStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.restartMatch();
                dismiss();
            }
        });

    }
}