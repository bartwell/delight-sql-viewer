package ru.bartwell.delightsqlviewer.feature.viewer

import ru.bartwell.delightsqlviewer.core.data.Column

internal fun List<Column>.removeBuiltIn() = filter { !it.isRowId }
