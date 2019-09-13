package dev.foodie.livedata_room_vmodel.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import dev.foodie.livedata_room_vmodel.dao.NoteDao;
import dev.foodie.livedata_room_vmodel.databases.NoteDatabase;
import dev.foodie.livedata_room_vmodel.models.Note;
import dev.foodie.livedata_room_vmodel.tasks.Transactions;

public class NotesRepository {

    private NoteDatabase db;
    private NoteDao dao;
    private LiveData<List<Note>> notes;

    public NotesRepository(Application application) {
        this.db = NoteDatabase.getInstance(application.getApplicationContext());
        this.dao = this.db.getDao();
        this.notes = this.dao.getNotes();
    }

    public void addNote(Note note) {
        new Transactions.AddNoteAsyncTask(this.dao).execute(note);
    }

    public void updateNote(Note note) {
        new Transactions.UpdateNoteAsyncTask(this.dao).execute(note);
    }

    public void deleteNote(Note note) {
        new Transactions.DeleteNoteAsyncTask(this.dao).execute(note);
    }

    public void deleteAllNotes() {
        new Transactions.DeleteAllNotesAsyncTask(this.dao).execute();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

}

