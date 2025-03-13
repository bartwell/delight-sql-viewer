package ru.bartwell.delightsqlviewer

import android.content.Context
import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

internal interface AndroidEnvironmentProvider : EnvironmentProvider<Any> {
    @Suppress("unused")
    fun getContext(): Context
}
