package dev.foodie.livedata_room_vmodel.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.foodie.livedata_room_vmodel.models.Note;

@Dao
public interface NoteDao {

    @Insert
    void addNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM notes_table ORDER BY priority DESC")
    LiveData<List<Note>> getNotes();

    @Query("DELETE FROM notes_table")
    void deleteNotes();

}
