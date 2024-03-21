package com.antont.notesjava.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antont.notesjava.callback.OnNoteClickedListener;
import com.antont.notesjava.databinding.RecyclerViewNoteBinding;
import com.antont.notesjava.db.entity.Note;

import java.util.List;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NoteHolder> {

    private final List<Note> allNotes;
    private final OnNoteClickedListener listener;

    public NotesRecyclerViewAdapter(List<Note> allNotes, OnNoteClickedListener listener) {
        this.allNotes = allNotes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewNoteBinding binding = RecyclerViewNoteBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bind(allNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }


}