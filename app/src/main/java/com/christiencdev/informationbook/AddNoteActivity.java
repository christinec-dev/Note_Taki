package com.christiencdev.informationbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Note");
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.editNoteTitle);
        description = findViewById(R.id.editNoteDescription);
        save = findViewById(R.id.buttonSubmit);
        cancel = findViewById(R.id.buttonCancel);

        //will close new note menu
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Note Cancelled",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //will save new note
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    public void saveNote(){
        //get text entered
        String noteTitle = title.getText().toString();
        String noteDescription = description.getText().toString();

        //send text to db
        Intent i = new Intent();
        i.putExtra("noteTitle", noteTitle);
        i.putExtra("noteDescription", noteDescription);
        setResult(RESULT_OK, i);
        finish();
    }
}