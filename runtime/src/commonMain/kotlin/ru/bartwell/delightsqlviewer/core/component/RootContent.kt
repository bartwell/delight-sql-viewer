package ru.bartwell.delightsqlviewer.core.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.bartwell.delightsqlviewer.feature.insert.presentation.InsertContent
import ru.bartwell.delightsqlviewer.feature.structure.presentation.StructureContent
import ru.bartwell.delightsqlviewer.feature.table.presentation.TablesListContent
import ru.bartwell.delightsqlviewer.feature.update.presentation.UpdateContent
import ru.bartwell.delightsqlviewer.feature.viewer.presentation.ViewerContent

@Composable
internal fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(slide()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Viewer -> ViewerContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )

            is RootComponent.Child.TablesList -> TablesListContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )

            is RootComponent.Child.Update -> UpdateContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )

            is RootComponent.Child.Insert -> InsertContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )

            is RootComponent.Child.Structure -> StructureContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
