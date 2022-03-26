package com.msk.noteapp.feature_note.data.repository

import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*

class FakeNoteRepository:NoteRepository{

    private val notes= mutableListOf<note>()
    override suspend fun insertNote(note: note) {
        notes.add(note)
    }

    override fun getNotes(): Flow<List<note>> = flow { emit(notes) }

    override suspend fun getNoteByid(id: Int): note? {
        return notes.find { it.id==id }
    }

    override suspend fun deletenote(note: note) {
        notes.remove(note)
    }

}