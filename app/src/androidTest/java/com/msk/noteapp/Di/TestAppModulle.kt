package com.msk.noteapp.Di

import android.app.Application
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Room
import com.msk.noteapp.feature_note.data.data_source.noteDatabase
import com.msk.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.msk.noteapp.feature_note.domain.repository.NoteRepository
import com.msk.noteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModulle {

    @Provides
    @Singleton
    fun provideDatabase(app:Application):noteDatabase{
        return Room.inMemoryDatabaseBuilder(
            app,noteDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: noteDatabase):NoteRepository{
        return NoteRepositoryImpl(database.dao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository):NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotes(repository), deleteNote = DeleteNote(repository),
            addNote = AddNote(repository), getNote = GetNote(repository)
        )
    }
}