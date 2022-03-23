package com.msk.noteapp.feature_note.domain.repository

import com.msk.noteapp.feature_note.domain.model.note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insertNote(note: note)
    fun getNotes(): Flow<List<note>>
    suspend fun getNoteByid(id:Int):note?
    suspend fun deletenote(note: note)

}