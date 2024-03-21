package com.antont.notesjava;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.antont.notesjava.callback.OnNoteDialogAction;
import com.antont.notesjava.databinding.NoteDialogBinding;
import com.antont.notesjava.db.entity.Note;
public class NoteDialog extends DialogFragment {
    private static final String TAG = "example_dialog";
    private final String title;
    private final Note note;
    private final Boolean isDeleteButtonVisible;
    private final OnNoteDialogAction listener;
    private NoteDialogBinding binding;
    public NoteDialog(String title, Note note, Boolean isDeleteButtonVisible, OnNoteDialogAction listener) {
        this.title = title;
        this.note = note;
        this.isDeleteButtonVisible = isDeleteButtonVisible;
        this.listener = listener;
    }

    public static NoteDialog displayCreate(FragmentManager fragmentManager, OnNoteDialogAction listener)  {
        NoteDialog exampleDialog = new NoteDialog("Create Note", null, false, listener);
        exampleDialog.show(fragmentManager, TAG);
        return exampleDialog;
    }

    public static NoteDialog displayEdit(Note note, FragmentManager fragmentManager, OnNoteDialogAction listener) {
        NoteDialog exampleDialog = new NoteDialog("Edit Note", note, true, listener);
        exampleDialog.show(fragmentManager, TAG);
        return exampleDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_Notes_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
                window.setWindowAnimations(R.style.Theme_Notes_Slide);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = NoteDialogBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbar.setNavigationOnClickListener(v -> dismiss());
        binding.toolbar.setTitle(title);
        binding.toolbar.inflateMenu(R.menu.note_dialog);
        binding.toolbar.getMenu().findItem(R.id.action_delete).setVisible(isDeleteButtonVisible);
        binding.toolbar.setOnMenuItemClickListener(v -> {
            if(v.getItemId() == R.id.action_delete){
                if (note != null){
                    listener.delete(note.getUid());
                } else {
                    Toast.makeText(view.getContext(), "Error during saving note", Toast.LENGTH_SHORT).show();
                }
            } else if (v.getItemId() == R.id.action_save) {
                Integer id = 0;
                if(note != null){
                    id =  note.getUid();
                }
                String title = binding.content.noteTitleEditText.getText().toString();
                String content = binding.content.noteContentEditText.getText().toString();
                listener.save(new Note(id, title, content));
            }
            dismiss();
            return true;
        });

        if(note != null){
            binding.content.noteTitleEditText.setText(note.getTitle());
            binding.content.noteContentEditText.setText(note.getContent());
        }

    }
}
