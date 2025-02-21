package ru.bartwell.delightsqlviewer.core.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.bartwell.delightsqlviewer.feature.insert.presentation.InsertComponent
import ru.bartwell.delightsqlviewer.feature.structure.presentation.StructureComponent
import ru.bartwell.delightsqlviewer.feature.table.presentation.TablesListComponent
import ru.bartwell.delightsqlviewer.feature.update.presentation.UpdateComponent
import ru.bartwell.delightsqlviewer.feature.viewer.presentation.ViewerComponent

internal interface RootComponent {
    val stack: Value<ChildStack<*, Child<*>>>

    sealed class Child<T : Component>(val component: T) {
        class TablesList(component: TablesListComponent) : Child<TablesListComponent>(component)
        class Viewer(component: ViewerComponent) : Child<ViewerComponent>(component)
        class Update(component: UpdateComponent) : Child<UpdateComponent>(component)
        class Insert(component: InsertComponent) : Child<InsertComponent>(component)
        class Structure(component: StructureComponent) : Child<StructureComponent>(component)
    }
}
