package dev.foodie.livedata_room_vmodel.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dev.foodie.livedata_room_vmodel.models.Note;
import dev.foodie.livedata_room_vmodel.repos.NotesRepository;

public class NoteViewModel extends AndroidViewModel {

    private NotesRepository notesRepository;
    private LiveData<List<Note>> notes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        this.notesRepository = new NotesRepository(application);
        this.notes = this.notesRepository.getNotes();
    }

    public void addNote(Note note) {
        this.notesRepository.addNote(note);
    }

    public void updateNote(Note note) {
        this.notesRepository.updateNote(note);
    }

    public void deleteNote(Note note) {
        this.notesRepository.deleteNote(note);
    }

    public void deleteAllNotes() {
        this.notesRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return this.notes;
    }


}
