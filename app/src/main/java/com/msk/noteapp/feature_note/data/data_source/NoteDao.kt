package com.msk.noteapp.feature_note.data.data_source

import androidx.room.*
import com.msk.noteapp.feature_note.domain.model.note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: note)

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<note>>

    @Query("SELECT * FROM note WHERE id= :id")
    suspend fun getNoteByid(id:Int):note

    @Delete
    suspend fun deleteNote(note:note)
}