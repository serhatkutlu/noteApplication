package com.msk.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msk.noteapp.ui.theme.*
import java.lang.Exception

@Entity
data class note(
val title:String,
val content:String,
val timeStamp:Int,
val color:Int,
@PrimaryKey(autoGenerate = true)
val id:Int?=null
){
    companion object{
        val noteColors= listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteExeption(message:String):Exception(message)