package com.msk.noteapp.feature_note.presenttion

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.msk.noteapp.Di.AppModulle
import com.msk.noteapp.MainActivity
import com.msk.noteapp.feature_note.presenttion.Util.Screen
import com.msk.noteapp.feature_note.presenttion.add_edit_note.AddEditNoteSecreen
import com.msk.noteapp.feature_note.presenttion.notes.components.NotesScreen
import com.msk.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModulle::class)
class NotesEndToEndTest {

    @get:Rule(order=0)
    val hiltRule= HiltAndroidRule(this)

    @get:Rule(order=1)
    val composeRule= createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.setContent {
            NoteAppTheme {
                val navController= rememberNavController()
                NavHost(navController=navController, startDestination = Screen.NoteScreen.route){
                    composable(route= Screen.NoteScreen.route){
                        NotesScreen(navController)
                    }
                    composable(
                        Screen.AddEditNoteScreen.route+"?noteid={noteId}&noteColor={noteColor}",
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

    @Test
    fun saveNote_editAfterwards(){
        //creating a note
        composeRule.onNodeWithContentDescription("add note").performClick()
        composeRule.onNodeWithTag("TitleTextField").performTextInput("title")
        composeRule.onNodeWithTag("ContentTextField").performTextInput("content")
        composeRule.onNodeWithContentDescription("Save note").performClick()
        //check note
        composeRule.onNodeWithText("title").performClick()
        composeRule.onNodeWithTag("TitleTextField").assertTextEquals("title")
        composeRule.onNodeWithTag("ContentTextField").assertTextEquals("content")
        //update note
        composeRule.onNodeWithTag("TitleTextField").performTextInput("changed")
        composeRule.onNodeWithTag("ContentTextField").performTextInput("changed")
        composeRule.onNodeWithContentDescription("Save note").performClick()

        composeRule.onNodeWithText("titlechanged").assertIsDisplayed()
        composeRule.onNodeWithText("contentchanged").assertIsDisplayed()
    }
    @Test
    fun SaveNotes_OrderByTitleDescending(){
        for (i in 0..2){
            //creating a note
            composeRule.onNodeWithContentDescription("add note").performClick()
            composeRule.onNodeWithTag("TitleTextField").performTextInput(i.toString())
            composeRule.onNodeWithTag("ContentTextField").performTextInput(i.toString())
            composeRule.onNodeWithContentDescription("Save note").performClick()
        }
        for (i in 0..2){
            composeRule.onNodeWithText(i.toString()).assertIsDisplayed()

        }

        composeRule.onNodeWithContentDescription("sort").performClick()
        composeRule.onNodeWithContentDescription("title").performClick()
        composeRule.onNodeWithContentDescription("Descending").performClick()

        for (i in 2 downTo 0){

                composeRule.onAllNodesWithTag("note item").get(2-i).assertTextContains(i.toString())


        }




    }

}