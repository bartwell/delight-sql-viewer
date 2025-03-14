package ru.bartwell.delightsqlviewer.feature.update.presentation

import com.arkivanov.decompose.value.Value
import ru.bartwell.delightsqlviewer.core.component.Component

internal interface UpdateComponent : Component {
    val model: Value<UpdateState>

    fun onBackPressed()
    fun onValueChange(text: String)
    fun onNullCheckboxClick()
    fun onSaveClick()
}
