package com.msk.noteapp.feature_note.presenttion.notes.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.msk.noteapp.Di.AppModulle
import com.msk.noteapp.MainActivity
import com.msk.noteapp.feature_note.presenttion.Util.Screen
import com.msk.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModulle::class)
class NotesScreenKtTest{


    @get:Rule(order=0)
    val hiltRule=HiltAndroidRule(this)

    @get:Rule(order=1)
    val composeRule= createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.setContent {
            val navController= rememberNavController()
            NoteAppTheme {
                NavHost(navController=navController, startDestination = Screen.NoteScreen.route){
                    composable(route=Screen.NoteScreen.route){
                        NotesScreen(navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible(){
        composeRule.onNodeWithTag("order section test").assertDoesNotExist()
        composeRule.onNodeWithContentDescription("sort").performClick()
       // composeRule.onNodeWithTag("order section test").assertIsDisplayed()
        composeRule.onNodeWithTag("order section test").assertExists()

    }

}