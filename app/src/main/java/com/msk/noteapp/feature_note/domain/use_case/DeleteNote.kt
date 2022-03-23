package com.msk.noteapp.feature_note.domain.use_case

import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.repository.NoteRepository

class DeleteNote(private val repository: NoteRepository){
    suspend operator fun invoke(note: note){
        repository.deletenote(note)
    }
}