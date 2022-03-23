package com.msk.noteapp.feature_note.presenttion.Util

sealed class Screen(val route:String){
    object NoteScreen:Screen("note_screen")
    object AddEditNoteScreen:Screen("Add_edit_note_screen")
}
