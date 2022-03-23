package com.msk.noteapp.feature_note.domain.use_case

import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.repository.NoteRepository
import com.msk.noteapp.feature_note.domain.util.NoteOrder
import com.msk.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(
          noteorder:NoteOrder=NoteOrder.Date(OrderType.Descending)
    ):Flow<List<note>>{
        return repository.getNotes().map {notes->
            when(noteorder.orderType){
                is OrderType.Ascending->{
                    when(noteorder){
                    is NoteOrder.Title->{notes.sortedBy {it.title.lowercase() }}
                    is NoteOrder.Date->{notes.sortedBy { it.timeStamp }}
                    is NoteOrder.Color->{notes.sortedBy { it.color }}
                }}
                is OrderType.Descending-> {
                    when(noteorder){
                    is NoteOrder.Title->{notes.sortedByDescending {it.title.lowercase() }}
                    is NoteOrder.Date->{notes.sortedByDescending { it.timeStamp }}
                    is NoteOrder.Color->{notes.sortedByDescending { it.color }}

                }}
            }
        }

    }

}