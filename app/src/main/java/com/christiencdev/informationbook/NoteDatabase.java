package com.christiencdev.informationbook;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Database for storing notes, linked to DAO
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase  extends RoomDatabase {

    //note will be accessed from anywhere (static)
    private static NoteDatabase instance;

    //abstract because DAO has no method nor argument, the db will handle it
    public abstract  NoteDao noteDao();

    //initialize db via blank db
    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database").addCallback(roomCallback).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    //pumps data into db
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            NoteDao noteDao = instance.noteDao();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    noteDao.insert(new Note("Title 1", "Description 1"));
                }
            });
        }
    };
}
