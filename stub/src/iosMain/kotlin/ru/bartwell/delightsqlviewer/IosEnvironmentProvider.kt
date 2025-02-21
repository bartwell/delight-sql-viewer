package ru.bartwell.delightsqlviewer

public class IosEnvironmentProvider(private val sqlDriver: Any) : EnvironmentProvider {
    public override fun getSqlDriver(): Any = sqlDriver
}
