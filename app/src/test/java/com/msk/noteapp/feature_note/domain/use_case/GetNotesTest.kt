package com.msk.noteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.msk.noteapp.feature_note.data.repository.FakeNoteRepository
import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.util.NoteOrder
import com.msk.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest{
    private lateinit var  getNotes:GetNotes
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp(){
        fakeNoteRepository=FakeNoteRepository()
        getNotes= GetNotes(fakeNoteRepository)
        val notesToInsert= mutableListOf<note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(note(
                title = c.toString(),
                content = c.toString(),
                timeStamp = index,
                color = index
            ))
        }
        notesToInsert.shuffle()
        runBlocking {
        notesToInsert.forEach{
            fakeNoteRepository.insertNote(it)
        }}
    }

    @Test
    fun `Order notes by title ascending,correct order`(){
        runBlocking {
            val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
            for (i in 0..notes.size - 2){
                assertThat(notes.get(i).title).isLessThan(notes.get(i+1).title)
            }
        }
    }
    @Test
    fun `Order notes by title descending,correct order`(){
        runBlocking {
            val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()
            for (i in 0..notes.size - 2){
                assertThat(notes.get(i).title).isGreaterThan(notes.get(i+1).title)
            }
        }
    }
    @Test
    fun `Order notes by date ascending,correct order`(){
        runBlocking {
            val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
            for (i in 0..notes.size - 2){
                assertThat(notes.get(i).timeStamp).isLessThan(notes.get(i+1).timeStamp)
            }
        }
    }
    @Test
    fun `Order notes by date descending,correct order`(){
        runBlocking {
            val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()
            for (i in 0..notes.size - 2){
                assertThat(notes.get(i).timeStamp).isGreaterThan(notes.get(i+1).timeStamp)
            }
        }
    }
    @Test
    fun `Order notes by content ascending,correct order`(){
        runBlocking {
            val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
            for (i in 0..notes.size - 2){
                assertThat(notes.get(i).content).isLessThan(notes.get(i+1).content)
            }
        }
    }
    @Test
    fun `Order notes by content descending,correct order`(){
        runBlocking {
            val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()
            for (i in 0..notes.size - 2){
                assertThat(notes.get(i).content).isGreaterThan(notes.get(i+1).content)
            }
        }
    }
    @Test
    fun `Order notes by color ascending,correct order`(){
        runBlocking {
            val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
            for (i in 0..notes.size - 2){
                assertThat(notes.get(i).color).isLessThan(notes.get(i+1).color)
            }
        }
    }
    @Test
    fun `Order notes by color descending,correct order`(){
        runBlocking {
            val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()
            for (i in 0..notes.size - 2){
                assertThat(notes.get(i).color).isGreaterThan(notes.get(i+1).color)
            }
        }
    }

}