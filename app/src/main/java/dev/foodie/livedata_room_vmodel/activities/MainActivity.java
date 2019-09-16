package dev.foodie.livedata_room_vmodel.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import dev.foodie.livedata_room_vmodel.R;
import dev.foodie.livedata_room_vmodel.adapters.NoteAdapter;
import dev.foodie.livedata_room_vmodel.models.Note;
import dev.foodie.livedata_room_vmodel.viewmodels.NoteViewModel;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;
    private NoteViewModel noteViewModel;

    private RecyclerView noteRecyclerView;
    private NoteAdapter noteAdapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteRecyclerView = findViewById(R.id.notesRecycler);
        noteRecyclerView.setHasFixedSize(true);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteAdapter = new NoteAdapter();
        noteRecyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        this.noteViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.submitList(notes);
            }
        });

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.deleteNote(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        itemTouchHelper.attachToRecyclerView(noteRecyclerView);

        noteAdapter.setmOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(Note note) {
                Intent editNoteIntent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                editNoteIntent.putExtra("note", note);
                editNoteIntent.putExtra("editMode", true);

                startActivityForResult(editNoteIntent, EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note: {
                Intent addNoteIntent = new Intent(this, AddEditNoteActivity.class);
                startActivityForResult(addNoteIntent, REQUEST_CODE);
                break;
            }
            case R.id.delete_all_notes: {
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted!", Toast.LENGTH_SHORT).show();
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.d("DEBUG", "onActivityResult: data is not null");
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                Log.d("DEBUG", "onActivityResult: request gotten");
                Bundle extras = data.getExtras();
                Note note = (Note) extras.getSerializable("note");

                noteViewModel.addNote(note);

            } else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
                Note note = (Note) data.getSerializableExtra("note");
                if (note != null) {
                    noteViewModel.updateNote(note);
                    Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "No note saved!", Toast.LENGTH_SHORT).show();
        }
    }
}

/*
 * TODO: read up on entity documentation!
 * TODO: read on possibilities that can be achieved in using the Room helper!
 * */