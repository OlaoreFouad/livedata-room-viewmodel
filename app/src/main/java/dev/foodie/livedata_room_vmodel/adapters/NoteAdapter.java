package dev.foodie.livedata_room_vmodel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.foodie.livedata_room_vmodel.R;
import dev.foodie.livedata_room_vmodel.models.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> notes = new ArrayList<>();

    public NoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = this.notes.get(position);
        holder.populate(note);
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, priority;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.noteTitle);
            description = itemView.findViewById(R.id.noteDescription);
            priority = itemView.findViewById(R.id.notePriority);

        }

        void populate(Note note) {
            this.title.setText(note.getTitle());
            this.description.setText(note.getDescription());
            this.priority.setText(String.valueOf(note.getPriority()));
        }
    }

}
