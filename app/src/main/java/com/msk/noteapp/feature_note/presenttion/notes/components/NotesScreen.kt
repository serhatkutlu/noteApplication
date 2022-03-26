package com.msk.noteapp.feature_note.presenttion.notes.components

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.presenttion.Util.Screen
import com.msk.noteapp.feature_note.presenttion.notes.NoteEvent
import com.msk.noteapp.feature_note.presenttion.notes.NotesViewModel
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel= hiltViewModel()
) {
    val state=viewModel.state.value
    val scaffoldState= rememberScaffoldState()
    val scope= rememberCoroutineScope()


    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                      navController.navigate(Screen.AddEditNoteScreen.route)
            }, backgroundColor = MaterialTheme.colors.primary
        )
        {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add note")
        }
    }, scaffoldState = scaffoldState
    ){
        Column(modifier = Modifier.fillMaxSize().padding(16.dp))
        {
         Row(Modifier.fillMaxWidth(),
             horizontalArrangement = Arrangement.SpaceBetween,
         verticalAlignment = Alignment.CenterVertically)
         {
            Text(
                text = "Your Note", style = MaterialTheme.typography.h4
            )
             IconButton(onClick = {viewModel.onEvent(NoteEvent.ToggleOrderSection)}){
                 Icon(imageVector = Icons.Default.Sort, contentDescription = "sort")
             }
         }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut()+ slideOutVertically())
            {
                OrderSection(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp).testTag("order section test"),
                    state.noteOrder,
                    onorderChange = {
                        viewModel.onEvent(NoteEvent.Order(it))
                    }        )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) {note->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                       navController.navigate(route = Screen.AddEditNoteScreen.route+"?noteid=${note.id}&noteColor=${note.color}")
                            }.testTag("note item"),
                        onDeleteClick = {
                            viewModel.onEvent(NoteEvent.DeleteNote(note))
                            scope.launch {
                                val result=scaffoldState.snackbarHostState.showSnackbar(
                                    message = "note deleted",
                                    actionLabel = "Undo"
                                )
                                if (result==SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(NoteEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}