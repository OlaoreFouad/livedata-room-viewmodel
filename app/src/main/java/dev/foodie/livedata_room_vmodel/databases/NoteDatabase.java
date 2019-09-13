package dev.foodie.livedata_room_vmodel.databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import dev.foodie.livedata_room_vmodel.dao.NoteDao;
import dev.foodie.livedata_room_vmodel.models.Note;
import dev.foodie.livedata_room_vmodel.tasks.Transactions;

@Database(entities = {
        Note.class
}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao getDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "notes_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(dbCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Transactions.PopulateDbAsyncTask(instance).execute();
        }
    };
}
