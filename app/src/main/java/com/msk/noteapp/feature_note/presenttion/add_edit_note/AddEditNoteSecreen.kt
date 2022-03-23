package com.msk.noteapp.feature_note.presenttion.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.msk.noteapp.feature_note.domain.model.note
import com.msk.noteapp.feature_note.presenttion.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteSecreen(
    navController: NavController,
    noteColor:Int,
    viewModel: AddEditNoteViewModel= hiltViewModel()
) {

    val titleState=viewModel.noteTitle.value
    val contentState=viewModel.noteContent.value

    val scaffoldState= rememberScaffoldState()
    val noteBackgroundAnimatable= remember {
        Animatable(
            Color(if (noteColor!=-1) noteColor else viewModel.noteColor.value)
        )
    }
    val scope= rememberCoroutineScope()

    LaunchedEffect(true){
        viewModel.eventFlow.collectLatest {
            when(it){
                is AddEditNoteViewModel.UiEvent.ShowSnackbar->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message
                    )
                }
                is AddEditNoteViewModel.UiEvent.SaveNote->{
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold (floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.OnEvent(AddEditNoteEvent.SaveNote)
        }
        , backgroundColor = MaterialTheme.colors.primary){
            Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
        }
    }, scaffoldState = scaffoldState
    ){
        Column(modifier = Modifier.fillMaxSize().background(noteBackgroundAnimatable.value).padding(16.dp))
        {
            Row (modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween)
            {
                note.noteColors.forEach{
                    Box(
                        modifier = Modifier.size(50.dp).shadow(15.dp,CircleShape).clip(CircleShape)
                            .background(it).border(width = 3.dp,
                                color = if (viewModel.noteColor.value==it.toArgb()){
                                Color.Black }else Color.Transparent, shape = CircleShape)
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(targetValue = it, animationSpec = tween(durationMillis = 500))
                                }
                                viewModel.OnEvent(AddEditNoteEvent.ChangeColor(it.toArgb()))
                            }

                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChangeListener = {
                    viewModel.OnEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.OnEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.ishintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChangeListener = {
                    viewModel.OnEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.OnEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.ishintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()

            )

        }
    }
}