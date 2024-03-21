package com.antont.notesjava.callback;

import com.antont.notesjava.db.entity.Note;

public interface OnNoteDialogAction {
    void save(Note note);
    void delete(Integer id);
}