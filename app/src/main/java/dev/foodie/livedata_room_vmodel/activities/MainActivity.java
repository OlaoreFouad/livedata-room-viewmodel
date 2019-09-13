package dev.foodie.livedata_room_vmodel.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
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
    private NoteViewModel noteViewModel;
    private LiveData<List<Note>> notes;

    private RecyclerView noteRecyclerView;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteRecyclerView = findViewById(R.id.notesRecycler);
        noteRecyclerView.setHasFixedSize(true);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteAdapter = new NoteAdapter(this);
        noteRecyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        this.noteViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
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
        switch(item.getItemId()) {
            case R.id.add_note: {
                Intent addNoteIntent = new Intent(this, AddNoteActivity.class);
                startActivityForResult(addNoteIntent, REQUEST_CODE);
                break;
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