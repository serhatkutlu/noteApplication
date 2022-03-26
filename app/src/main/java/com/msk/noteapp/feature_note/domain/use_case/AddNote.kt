package com.msk.noteapp.feature_note.domain.use_case

import android.util.Log
import com.msk.noteapp.feature_note.domain.model.InvalidNoteExeption
import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidNoteExeption::class)
    suspend operator fun invoke(note:note){
        if (note.title.isEmpty()){
        throw InvalidNoteExeption("The Title of the note can't be empty")

        }
        if (note.content.isEmpty()){
        throw InvalidNoteExeption("The content of the note can't be empty")

        }
        repository.insertNote(note)
    }
}