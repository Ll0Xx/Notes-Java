package com.antont.notesjava.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.antont.notesjava.NoteDialog;
import com.antont.notesjava.adapter.NotesRecyclerViewAdapter;
import com.antont.notesjava.callback.OnNoteClickedListener;
import com.antont.notesjava.callback.OnNoteDialogAction;
import com.antont.notesjava.databinding.ActivityMainBinding;
import com.antont.notesjava.db.DatabaseQueryClass;
import com.antont.notesjava.db.entity.Note;
import com.antont.notesjava.viewmodel.MainViewModel;
import com.antont.notesjava.viewmodel.MainViewModelFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private final OnNoteClickedListener noteListener =  new OnNoteClickedListener() {
        @Override
        public void onNoteClicked(Note note) {
            NoteDialog.displayEdit(note, getSupportFragmentManager(), noteDialogCallback);
        }
    };

    private final OnNoteDialogAction noteDialogCallback = new OnNoteDialogAction() {
        @Override
        public void save(Note note) {
            mainViewModel.insert(note);
        }

        @Override
        public void delete(Integer id) {
            mainViewModel.delete(id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        mainViewModel = new MainViewModelFactory(new DatabaseQueryClass(this)).create(MainViewModel.class);
        mainViewModel.getAllNotes().observe(this, notes -> {
            binding.content.notesRecyclerView.setLayoutManager( new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
            NotesRecyclerViewAdapter adapter = new NotesRecyclerViewAdapter(notes, noteListener);
            binding.content.notesRecyclerView.setAdapter(adapter);
        });

        binding.fab.setOnClickListener(v -> NoteDialog.displayCreate(getSupportFragmentManager(), noteDialogCallback));
    }
}