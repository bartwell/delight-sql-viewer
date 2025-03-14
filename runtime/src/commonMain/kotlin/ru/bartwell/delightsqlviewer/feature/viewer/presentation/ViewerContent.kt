package ru.bartwell.delightsqlviewer.feature.viewer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.bartwell.delightsqlviewer.core.data.Column
import ru.bartwell.delightsqlviewer.core.extension.orNull
import ru.bartwell.delightsqlviewer.core.presentation.ErrorAlert
import ru.bartwell.delightsqlviewer.core.presentation.ErrorBox

private val MAX_CELL_WIDTH = 200.dp
private val CELL_PADDING = 8.dp
private val FIELD_TYPE_STYLE: TextStyle
    @Composable get() = MaterialTheme.typography.bodySmall

@Composable
internal fun ViewerContent(
    component: ViewerComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.model.subscribeAsState()
    Column(modifier = modifier) {
        Toolbar(
            state = state,
            onBackPressed = component::onBackPressed,
            onCancelDelete = component::onCancelDeleteClick,
            onConfirmDelete = component::onConfirmDeleteClick,
            onStructureClick = component::onStructureClick,
            onInsertClick = component::onInsertClick,
            onDeleteClick = component::onDeleteClick,
        )
        ErrorBox(
            modifier = Modifier.fillMaxSize(),
            error = state.loadError,
        ) {
            Table(
                viewerState = state,
                onCellClick = component::onCellClick,
                onRowSelected = component::onRowSelected,
            )
        }
    }
    state.deleteError?.let { error ->
        ErrorAlert(message = error, onDismiss = component::onAlertDismiss)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    state: ViewerState,
    onBackPressed: () -> Unit,
    onCancelDelete: () -> Unit,
    onConfirmDelete: () -> Unit,
    onStructureClick: () -> Unit,
    onInsertClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = if (state.isDeleteMode) "Delete" else state.table) },
        navigationIcon = {
            if (state.isDeleteMode) {
                IconButton(onClick = onCancelDelete) {
                    Icon(imageVector = Icons.Outlined.Close, contentDescription = "Cancel")
                }
            } else {
                IconButton(onClick = onBackPressed) {
                    Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            if (state.isDeleteMode) {
                IconButton(onClick = onConfirmDelete) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = "Delete selected")
                }
            } else {
                IconButton(onClick = { isMenuExpanded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Structure") },
                        onClick = {
                            isMenuExpanded = false
                            onStructureClick()
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Insert row") },
                        onClick = {
                            isMenuExpanded = false
                            onInsertClick()
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            isMenuExpanded = false
                            onDeleteClick()
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun Table(
    viewerState: ViewerState,
    onCellClick: (column: Column, rowId: Long) -> Unit,
    onRowSelected: (rowId: Long, isSelected: Boolean) -> Unit
) {
    val horizontalScrollState = rememberScrollState()
    val cellsWidths = calculateCellsWidths(viewerState)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(horizontalScrollState)
            .padding(16.dp),
    ) {
        item {
            Row(Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
                if (viewerState.isDeleteMode) {
                    TableCell(
                        title = "",
                        subtitle = "",
                        width = 48.dp,
                        textColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                for ((columnIndex, column) in viewerState.visibleColumns.withIndex()) {
                    TableCell(
                        title = column.name,
                        subtitle = column.type.name.lowercase(),
                        width = cellsWidths[columnIndex],
                        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        }
        items(viewerState.rows.size) { index ->
            val row = viewerState.rows[index]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            ) {
                if (viewerState.isDeleteMode) {
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .fillMaxHeight()
                            .border(1.dp, MaterialTheme.colorScheme.onBackground)
                            .padding(horizontal = 4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        val isChecked = viewerState.selectedRows.contains(row.id)
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked -> onRowSelected(row.id, checked) },
                        )
                    }
                }
                for (cell in row.data.withIndex()) {
                    TableCell(
                        title = cell.value.orNull(),
                        onClick = { onCellClick(viewerState.visibleColumns[cell.index], row.id) },
                        width = cellsWidths[cell.index],
                        textColor = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Composable
private fun TableCell(
    title: String,
    width: Dp,
    textColor: Color,
    onClick: (() -> Unit)? = null,
    subtitle: String? = null
) {
    Box(
        modifier = Modifier
            .width(width)
            .fillMaxHeight()
            .border(1.dp, MaterialTheme.colorScheme.onBackground)
            .clickable(enabled = onClick != null, onClick = onClick ?: {}),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(CELL_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            subtitle?.let {
                Text(
                    text = subtitle,
                    color = textColor,
                    style = FIELD_TYPE_STYLE,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun calculateCellsWidths(viewerState: ViewerState): MutableList<Dp> {
    val result = mutableListOf<Dp>()
    for (column in viewerState.columns) {
        val fieldNameWidth = column.name.calculateTextWidth()
        val fieldTypeWidth = column.type
            .name
            .lowercase()
            .calculateTextWidth(FIELD_TYPE_STYLE)
        result.add(max(fieldNameWidth, fieldTypeWidth))
    }
    for (row in viewerState.rows) {
        for (cell in row.data.withIndex()) {
            val cellWidth = cell.value.orNull().calculateTextWidth()
            result[cell.index] = max(cellWidth, result[cell.index])
        }
    }
    return result
}

@Composable
private fun String.calculateTextWidth(style: TextStyle = MaterialTheme.typography.bodyMedium): Dp {
    val textMeasurer = rememberTextMeasurer()
    val result = textMeasurer.measure(
        text = AnnotatedString(text = this),
        style = style,
    )
    val calculatedWidth = with(LocalDensity.current) { result.size.width.toDp() }
    val widthWithPaddings = calculatedWidth + CELL_PADDING * 2
    return min(widthWithPaddings, MAX_CELL_WIDTH)
}
