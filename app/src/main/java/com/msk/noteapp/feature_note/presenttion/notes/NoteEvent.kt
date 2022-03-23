package com.msk.noteapp.feature_note.presenttion.notes

import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.util.NoteOrder

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder):NoteEvent()
    data class DeleteNote(val note: note):NoteEvent()
    object RestoreNote:NoteEvent()
    object ToggleOrderSection:NoteEvent()


}