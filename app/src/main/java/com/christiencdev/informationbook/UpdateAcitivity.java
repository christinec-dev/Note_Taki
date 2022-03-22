package com.christiencdev.informationbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateAcitivity extends AppCompatActivity {
    EditText title;
    EditText description;
    Button save, cancel;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Edit Note");
        setContentView(R.layout.activity_update_acitivity);

        title = findViewById(R.id.editNoteTitleUpdate);
        description = findViewById(R.id.editNoteDescriptionUpdate);
        save = findViewById(R.id.buttonSubmitUpdate);
        cancel = findViewById(R.id.buttonCancelUpdate);

        getData();

        //will close new note menu
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Note Updated",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //will save new note
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNote();
            }
        });
    }

    private void updateNote() {
        String titleLast = title.getText().toString();
        String descriptionLast = description.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("titleLast", titleLast);
        intent.putExtra("descriptionLast", descriptionLast);

        //the id will never be -1
        if (noteId != -1){
            intent.putExtra("noteId", noteId);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void getData(){
        Intent i = getIntent();
        noteId = i.getIntExtra("id", -1);
        String noteTitle = i.getStringExtra("title");
        String noteDescription = i.getStringExtra("description");

        title.setText(noteTitle);
        description.setText(noteDescription);
    }
}