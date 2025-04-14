package ru.bartwell.delightsqlviewer.sample.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.bartwell.delightsqlviewer.DelightSqlViewer
import ru.bartwell.delightsqlviewer.core.data.Theme

@Composable
fun App(
    databaseInitializer: DatabaseInitializer,
) {
    var selectedDatabase by remember { mutableStateOf(Database.SqlDelight) }
    var selectedTheme by remember { mutableStateOf(AppTheme.Auto) }
    MaterialTheme(
        colorScheme = selectedTheme.getColorScheme(),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                        Database.entries.forEach { entry ->
                            RadioButtonRow(
                                title = entry.name,
                                isSelected = entry == selectedDatabase,
                                onClick = { selectedDatabase = entry }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    ThemeList(selected = selectedTheme, onSelect = { selectedTheme = it })
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            when (selectedDatabase) {
                                Database.SqlDelight -> databaseInitializer.initSqlDelight()
                                Database.Room -> databaseInitializer.initRoom()
                            }
                            DelightSqlViewer.launch(theme = selectedTheme.toLibraryTheme())
                        },
                        content = {
                            Text(text = "Launch viewer")
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RadioButtonRow(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.width(16.dp))
        RadioButton(selected = isSelected, onClick = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeList(
    selected: AppTheme,
    onSelect: (AppTheme) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val themeOptions = AppTheme.entries.map { it.name }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selected.name,
            onValueChange = { },
            label = { Text("Theme") },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            themeOptions.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onSelect(AppTheme.valueOf(selectionOption))
                        expanded = false
                    }
                )
            }
        }
    }
}

private enum class Database {
    SqlDelight, Room
}

private enum class AppTheme {
    Auto, Dark, Light, Custom;

    @Composable
    fun getColorScheme(): ColorScheme = when (this) {
        Auto -> if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
        Dark -> darkColorScheme()
        Light -> lightColorScheme()
        Custom -> appCustomColorScheme()
    }
}

private fun AppTheme.toLibraryTheme(): Theme = when (this) {
    AppTheme.Auto -> Theme.Auto
    AppTheme.Dark -> Theme.Dark
    AppTheme.Light -> Theme.Light
    AppTheme.Custom -> Theme.Custom(appCustomColorScheme())
}
