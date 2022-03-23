package com.msk.noteapp.feature_note.presenttion.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.noteapp.feature_note.domain.model.InvalidNoteExeption
import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _noteTitle= mutableStateOf(NoteTextFieldState(hint = "Enter title..."))
    val noteTitle:State<NoteTextFieldState> =_noteTitle

    private val _noteContent= mutableStateOf(NoteTextFieldState(hint = "Enter Content"))
    val noteContent:State<NoteTextFieldState> =_noteContent

    private val _noteColor= mutableStateOf(note.noteColors.random().toArgb())
    val noteColor:State<Int> =_noteColor

    private val _eventFlow= MutableSharedFlow<UiEvent>()
    val eventFlow:SharedFlow<UiEvent> =_eventFlow

    private var currentnoteid:Int?=null

    init {
        savedStateHandle.get<Int>("noteId")?.let {
            if(it!=-1){
                viewModelScope.launch {
                    noteUseCases.getNote(it)?.also { note->
                        currentnoteid=note.id
                        _noteTitle.value=noteTitle.value.copy(
                            text = note.title,
                            ishintVisible = false
                        )
                        _noteContent.value=noteContent.value.copy(
                            text = note.content,
                            ishintVisible = false
                        )
                        _noteColor.value=note.color

                    }
                }
            }
        }
    }
    fun OnEvent(event:AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitle->{
                _noteTitle.value=noteTitle.value.copy(text = event.value)
            }
            is AddEditNoteEvent.ChangeTitleFocus->{
                _noteTitle.value=noteTitle.value.copy(ishintVisible = !event.focus.isFocused&&noteTitle.value.text.isBlank())
            }
            is AddEditNoteEvent.EnteredContent->{
                _noteContent.value=noteContent.value.copy(text = event.value)
            }
            is AddEditNoteEvent.ChangeContentFocus->{
                _noteContent.value=noteContent.value.copy(ishintVisible = !event.focus.isFocused&&noteTitle.value.text.isBlank())
            }
            is AddEditNoteEvent.ChangeColor->{
                _noteColor.value=event.color
            }
            is AddEditNoteEvent.SaveNote->{
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timeStamp = System.currentTimeMillis().toInt(),
                                color=noteColor.value,
                                id = currentnoteid
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e:InvalidNoteExeption){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackbar(val message:String):UiEvent()
        object SaveNote:UiEvent()
    }
}