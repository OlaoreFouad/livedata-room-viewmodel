package dev.foodie.livedata_room_vmodel.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import dev.foodie.livedata_room_vmodel.R;
import dev.foodie.livedata_room_vmodel.models.Note;

public class AddNoteActivity extends AppCompatActivity {

    private EditText title, description;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.addNoteTitle);
        description = findViewById(R.id.addNoteDescription);
        numberPicker = findViewById(R.id.addNoteNumberPicker);

        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);

        setTitle("Add New Note");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save_note: {
                saveNote();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void saveNote() {
        String titleText = title.getText().toString();
        String descriptionText = description.getText().toString();
        int priority = numberPicker.getValue();

        if (
                TextUtils.isEmpty(titleText) || TextUtils.isEmpty(descriptionText)
        ) {
            Toast.makeText(this, "Empty fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note(titleText, descriptionText, priority);
        Intent intent = getIntent();

        intent.putExtra("note", note);
        setResult(RESULT_OK, intent);
        finish();
    }
}
