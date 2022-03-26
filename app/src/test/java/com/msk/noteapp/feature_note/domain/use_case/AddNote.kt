package com.msk.noteapp.feature_note.domain.use_case

import com.msk.noteapp.feature_note.data.repository.FakeNoteRepository
import com.msk.noteapp.feature_note.domain.model.InvalidNoteExeption
import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteTest {


    private lateinit var  addNote: AddNote
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp(){
        fakeNoteRepository= FakeNoteRepository()
        addNote= AddNote(fakeNoteRepository)

    }
    @Test(expected = InvalidNoteExeption::class)
    fun `given empty title,throw exeption`(){
        runBlocking {
        addNote(note("","content",5,5))
    }}
    @Test(expected = InvalidNoteExeption::class)
    fun `given empty content,throw exeption`(){
        runBlocking {
        addNote(note("title","",5,5))
    }}
}