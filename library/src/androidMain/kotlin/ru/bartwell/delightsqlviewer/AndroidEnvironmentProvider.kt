package ru.bartwell.delightsqlviewer

import android.content.Context

public interface AndroidEnvironmentProvider : EnvironmentProvider {
    public fun getContext(): Context
}
