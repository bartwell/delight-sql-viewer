package ru.bartwell.delightsqlviewer.sample.shared

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.bartwell.delightsqlviewer.DelightSqlViewer

@Composable
fun App(
    databaseInitializer: DatabaseInitializer,
) {
    var selectedDatabase by remember { mutableStateOf(Databases.SqlDelight) }
    MaterialTheme {
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
                        Databases.entries.forEach { entry ->
                            RadioButtonRow(
                                title = entry.name,
                                isSelected = entry == selectedDatabase,
                                onClick = { selectedDatabase = entry }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            when (selectedDatabase) {
                                Databases.SqlDelight -> databaseInitializer.initSqlDelight()
                                Databases.Room -> databaseInitializer.initRoom()
                            }
                            DelightSqlViewer.launch()
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

private enum class Databases {
    SqlDelight, Room
}
