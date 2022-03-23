package com.msk.noteapp.feature_note.domain.use_case

import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.repository.NoteRepository

class GetNote(private val repository: NoteRepository) {

    suspend operator fun invoke(id:Int):note?{
        return repository.getNoteByid(id)

    }
}