package com.christiencdev.informationbook;

//enables the Room Database
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Adds db table - can have multiple
@Entity(tableName = "note_table")

//will contain our notes
public class Note {
    //db columns
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public String description;

    //Constructor
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //Getter
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    //Setter

    public void setId(int id) {
        this.id = id;
    }
}
