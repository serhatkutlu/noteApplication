package com.msk.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.msk.noteapp.feature_note.presenttion.Util.Screen
import com.msk.noteapp.feature_note.presenttion.add_edit_note.AddEditNoteSecreen
import com.msk.noteapp.feature_note.presenttion.notes.components.NotesScreen
import com.msk.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background)
                {
                    val navController= rememberNavController()
                    NavHost(navController=navController, startDestination = Screen.NoteScreen.route){
                        composable(route=Screen.NoteScreen.route){
                            NotesScreen(navController)
                        }
                        composable(Screen.AddEditNoteScreen.route+"?noteid={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ){
                                type= NavType.IntType
                                defaultValue=-1
                            },
                            navArgument(
                                name = "noteColor"
                            ){
                                type= NavType.IntType
                                defaultValue=-1
                            }
                        )
                        ){
                            val color=it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteSecreen(
                                navController=navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}

