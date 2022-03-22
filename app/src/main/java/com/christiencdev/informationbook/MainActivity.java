package com.christiencdev.informationbook;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    ActivityResultLauncher<Intent> activityResultLauncherForAddNote;
    ActivityResultLauncher<Intent> activityResultLauncherForUpdateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //registers activity
        registerActivityForAddNote();
        registerActivityForUpdateNote();

        ///implements RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update Recycler View
                adapter.setNotes(notes);
            }
        });

        //delete notes
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNotes(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(),"Note Deleted",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //update notes
        adapter.SetOnNoteClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent i = new Intent(MainActivity.this, UpdateAcitivity.class);
                i.putExtra("id", note.getId());
                i.putExtra("title", note.getTitle());
                i.putExtra("description", note.getDescription());

                activityResultLauncherForUpdateNote.launch(i);
            }
        });
    }

    //get new note entered & sends to db
    public void registerActivityForAddNote() {
        activityResultLauncherForAddNote =  registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                int resultCode = result.getResultCode();
                Intent data = result.getData();

                if (resultCode == RESULT_OK && data != null){
                    String title = data.getStringExtra("noteTitle");
                    String description = data.getStringExtra("noteDescription");

                    Note note = new Note(title, description);
                    noteViewModel.insert(note);
                }
            }
        });
    }

    //update note & send to db
    public void registerActivityForUpdateNote() {
        activityResultLauncherForUpdateNote =  registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                int resultCode = result.getResultCode();
                Intent data = result.getData();

                if (resultCode == RESULT_OK && data != null){
                    String title = data.getStringExtra("titleLast");
                    String description = data.getStringExtra("descriptionLast");
                    int id = data.getIntExtra("noteId", -1);

                    Note note = new Note(title, description);
                    note.setId(id);
                    noteViewModel.update(note);
                }
            }
        });
    }

    //gets menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;
    }

    //let's menu button work by opening new note menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.top_menu:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                activityResultLauncherForAddNote.launch(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}