package com.msk.noteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msk.noteapp.feature_note.domain.model.note


@Database(
    entities = [note::class],
    version = 1
)
abstract class noteDatabase:RoomDatabase() {
    abstract val dao:NoteDao
}