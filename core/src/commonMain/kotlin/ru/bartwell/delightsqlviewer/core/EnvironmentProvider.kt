package ru.bartwell.delightsqlviewer.core

public interface EnvironmentProvider<T> {
    public fun getDriver(): T
    public fun getWrapper(): DatabaseWrapper
}
