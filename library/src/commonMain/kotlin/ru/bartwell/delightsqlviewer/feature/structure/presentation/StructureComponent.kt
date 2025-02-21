package ru.bartwell.delightsqlviewer.feature.structure.presentation

import com.arkivanov.decompose.value.Value
import ru.bartwell.delightsqlviewer.core.component.Component

internal interface StructureComponent : Component {
    val model: Value<StructureState>

    fun onBackPressed()
}
