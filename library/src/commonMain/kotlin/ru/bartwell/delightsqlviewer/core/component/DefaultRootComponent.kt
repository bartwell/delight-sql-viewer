package ru.bartwell.delightsqlviewer.core.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.bartwell.delightsqlviewer.core.data.Column
import ru.bartwell.delightsqlviewer.feature.insert.presentation.DefaultInsertComponent
import ru.bartwell.delightsqlviewer.feature.structure.presentation.DefaultStructureComponent
import ru.bartwell.delightsqlviewer.feature.table.presentation.DefaultTablesListComponent
import ru.bartwell.delightsqlviewer.feature.update.presentation.DefaultUpdateComponent
import ru.bartwell.delightsqlviewer.feature.viewer.presentation.DefaultViewerComponent

internal class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val nav = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child<*>>> = childStack(
        source = nav,
        serializer = Config.serializer(),
        initialConfiguration = Config.TablesList,
        handleBackButton = true,
        childFactory = ::child,
    )

    init {
        stack.subscribe { childStack ->
            val component = childStack.active.instance.component
            if (component is Resumable) {
                component.onResume()
            }
        }
    }

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child<*> = when (config) {
        Config.TablesList -> RootComponent.Child.TablesList(
            DefaultTablesListComponent(
                componentContext = componentContext,
                listItemClicked = { table ->
                    nav.pushNew(Config.Viewer(table))
                }
            )
        )

        is Config.Viewer -> {
            RootComponent.Child.Viewer(
                DefaultViewerComponent(
                    componentContext = componentContext,
                    table = config.table,
                    onFinished = {
                        nav.pop()
                    },
                    structureClick = { table -> nav.pushNew(Config.Structure(table)) },
                    insertClick = { table, columns -> nav.pushNew(Config.Insert(table, columns)) },
                    cellClick = { table, column, rowId ->
                        nav.pushNew(Config.Update(table, column, rowId))
                    }
                )
            )
        }

        is Config.Update -> RootComponent.Child.Update(
            DefaultUpdateComponent(
                componentContext = componentContext,
                table = config.table,
                column = config.column,
                rowId = config.rowId,
                onFinished = {
                    nav.pop()
                },
            )
        )

        is Config.Insert -> RootComponent.Child.Insert(
            DefaultInsertComponent(
                componentContext = componentContext,
                table = config.table,
                columns = config.columns,
                onFinished = {
                    nav.pop()
                },
            )
        )

        is Config.Structure -> RootComponent.Child.Structure(
            DefaultStructureComponent(
                componentContext = componentContext,
                table = config.table,
                onFinished = {
                    nav.pop()
                },
            )
        )
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object TablesList : Config

        @Serializable
        data class Viewer(val table: String) : Config

        @Serializable
        data class Update(
            val table: String,
            val column: Column,
            val rowId: Long,
        ) : Config

        @Serializable
        data class Insert(val table: String, val columns: List<Column>) : Config

        @Serializable
        data class Structure(val table: String) : Config
    }
}
