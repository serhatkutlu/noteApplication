package com.msk.noteapp.feature_note.presenttion.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.msk.noteapp.feature_note.domain.util.NoteOrder
import com.msk.noteapp.feature_note.domain.util.OrderType


@Composable
fun OrderSection(
    modifier: Modifier =Modifier,
    noteOrder: NoteOrder=NoteOrder.Date(OrderType.Descending),
    onorderChange:(NoteOrder)->Unit
){
    Column(modifier){
        Row ( Modifier.fillMaxWidth().testTag("")){
            defaulthRadioButton(
                "title",
                selected = noteOrder is NoteOrder.Title,
                onSelected = {onorderChange(NoteOrder.Title(noteOrder.orderType))}
            )
            Spacer(Modifier.width(5.dp))
            defaulthRadioButton(
                "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelected = {onorderChange(NoteOrder.Date(noteOrder.orderType))}
            )
            Spacer(Modifier.width(5.dp))

            defaulthRadioButton(
                "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelected = {onorderChange(NoteOrder.Color(noteOrder.orderType))}
            )}
            Spacer(Modifier.height(16.dp))
            Row (Modifier.fillMaxWidth()){
                defaulthRadioButton(
                    "Ascending",
                    selected = noteOrder.orderType is OrderType.Ascending,
                    onSelected = {onorderChange(noteOrder.copy(OrderType.Ascending))}
                )
                Spacer(Modifier.width(5.dp))

                defaulthRadioButton(
                    "Descending",
                    selected = noteOrder.orderType is OrderType.Descending,
                    onSelected = {onorderChange(noteOrder.copy(OrderType.Descending))}
                )
            }

    }

}