package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewNote extends AppCompatActivity {

    TextView title,content;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        title = findViewById(R.id.viewTitle);
        content = findViewById(R.id.viewContent);
        backBtn = findViewById(R.id.editNoteBtn);

        String mTitle = getIntent().getStringExtra("title");
        String mContent = getIntent().getStringExtra("content");

        title.setText(mTitle);
        content.setText(mContent);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}