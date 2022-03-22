package com.christiencdev.informationbook;

//Room DB will generate code in bg
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Note Data Access Object =  an object or an interface that provides access to an underlying database
@Dao
public interface NoteDao {
    //Data Access Object Queries
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    //SQLite query to list all notes
    @Query("SELECT * FROM note_table ORDER BY id ASC")
    LiveData<List<Note>> getAllNotes();

}
