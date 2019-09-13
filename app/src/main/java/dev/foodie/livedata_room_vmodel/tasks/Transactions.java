package dev.foodie.livedata_room_vmodel.tasks;

import android.os.AsyncTask;

import dev.foodie.livedata_room_vmodel.dao.NoteDao;
import dev.foodie.livedata_room_vmodel.databases.NoteDatabase;
import dev.foodie.livedata_room_vmodel.models.Note;

public class Transactions {

    public static class AddNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao dao;

        public AddNoteAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.dao.addNote(notes[0]);
            return null;
        }
    }

    public static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao dao;

        public UpdateNoteAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.dao.updateNote(notes[0]);
            return null;
        }
    }

    public static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao dao;

        public DeleteNoteAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.dao.deleteNote(notes[0]);
            return null;
        }
    }

    public static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao dao;

        public DeleteAllNotesAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.dao.deleteNotes();
            return null;
        }
    }

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao dao;

        public PopulateDbAsyncTask(NoteDatabase db) {
            this.dao = db.getDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.dao.addNote(new Note("Title 1", "Description 1", 1));
            this.dao.addNote(new Note("Title 2", "Description 2", 2));
            this.dao.addNote(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }

}
