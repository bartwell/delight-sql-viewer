package ru.bartwell.delightsqlviewer

import android.content.Context
import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

public interface AndroidEnvironmentProvider<T> : EnvironmentProvider<T> {
    public fun getContext(): Context
}
