package com.antont.notesjava.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.antont.notesjava.callback.OnNoteClickedListener;
import com.antont.notesjava.databinding.RecyclerViewNoteBinding;
import com.antont.notesjava.db.entity.Note;

public class NoteHolder extends RecyclerView.ViewHolder {
    private final RecyclerViewNoteBinding binding;
    private final OnNoteClickedListener listener;

    public NoteHolder(RecyclerViewNoteBinding binding, OnNoteClickedListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }



    void bind(Note note ) {
        binding.getRoot().setOnClickListener(v -> listener.onNoteClicked(note));
        binding.title.setText(note.getTitle());
        binding.content.setText(note.getContent());
    }
}