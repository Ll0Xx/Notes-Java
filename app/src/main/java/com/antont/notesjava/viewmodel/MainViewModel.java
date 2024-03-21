package com.antont.notesjava.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.antont.notesjava.db.DatabaseQueryClass;
import com.antont.notesjava.db.entity.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainViewModel extends ViewModel {

    private final DatabaseQueryClass repository;
    private List<Note> noteList = new ArrayList<>();
    private final MutableLiveData<List<Note>> allNotes = new MutableLiveData<>();

    public MainViewModel(DatabaseQueryClass repository) {
        this.repository = repository;
        loadNotes();
    }

    public MutableLiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    void loadNotes(){
        noteList = repository.getAllNotes();
        allNotes.setValue(noteList);
    }

    public void insert(Note note) {
        int newId = repository.insert(note);
        note.setUid(newId);
        noteList.add(note);
        allNotes.setValue(noteList);
    }

    public void delete(Integer noteId) {
        repository.delete(noteId);
        noteList.removeIf(note -> Objects.equals(note.getUid(), noteId));
        allNotes.setValue(noteList);
    }
}

