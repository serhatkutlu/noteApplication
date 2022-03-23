package com.msk.noteapp.feature_note.data.repository

import com.msk.noteapp.feature_note.data.data_source.NoteDao
import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao:NoteDao
):NoteRepository {


    override suspend fun insertNote(note: note) {
        dao.insertNote(note)
    }
    override fun getNotes(): Flow<List<note>> =dao.getNotes()

    override suspend fun getNoteByid(id: Int): note? =dao.getNoteByid(id)

    override suspend fun deletenote(note: note) {
        dao.deleteNote(note)
    }

}