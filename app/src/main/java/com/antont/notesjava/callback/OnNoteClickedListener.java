package com.antont.notesjava.callback;

import com.antont.notesjava.db.entity.Note;

public interface OnNoteClickedListener {
    void onNoteClicked(Note note);
}
