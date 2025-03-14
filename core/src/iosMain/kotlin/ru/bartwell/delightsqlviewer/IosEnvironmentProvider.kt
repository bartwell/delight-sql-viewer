package ru.bartwell.delightsqlviewer

import ru.bartwell.delightsqlviewer.core.EnvironmentProvider

public abstract class IosEnvironmentProvider<T>(protected val driver: T) : EnvironmentProvider<T> {
    final override fun getDriver(): T = driver
}
