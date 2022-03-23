package com.msk.noteapp.feature_note.presenttion.notes

import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.util.NoteOrder
import com.msk.noteapp.feature_note.domain.util.OrderType

data class NotesState(
    val notes:List<note> = listOf(),
    val noteOrder: NoteOrder=NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible:Boolean=false
)
