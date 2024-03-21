package com.antont.notesjava.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.antont.notesjava.db.DatabaseQueryClass;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final DatabaseQueryClass repository;

    public MainViewModelFactory(DatabaseQueryClass repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
