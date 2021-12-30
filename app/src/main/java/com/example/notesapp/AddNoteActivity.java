package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNoteActivity extends AppCompatActivity {

    EditText mtitle,mcontent,user;
    Button addNoteBtn;
    FirebaseDatabase mDb;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        mtitle = findViewById(R.id.noteTitle);
        mcontent = findViewById(R.id.noteContent);
        user = findViewById(R.id.email);
        addNoteBtn = findViewById(R.id.addNoteBtn);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mtitle.getText().toString();
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String content = mcontent.getText().toString();
                long createTime = System.currentTimeMillis();
                mDb = FirebaseDatabase.getInstance();
                reference = mDb.getReference(id);

                Notes notes = new Notes(title,content,createTime);
                reference.child(title).setValue(notes);
                Toast.makeText(AddNoteActivity.this, "The Note has been added successfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddNoteActivity.this,MainActivity.class));
            }
        });
    }
}