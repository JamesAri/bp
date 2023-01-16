package cz.zcu.students.lostandfound.lost_items.presentation.settings

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.list.ListDialog
import com.maxkeppeler.sheets.list.models.ListOption
import com.maxkeppeler.sheets.list.models.ListSelection
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun SettingsScreen() {
    val themeDialogState = rememberSheetState()

    Column(modifier = Modifier.fillMaxSize()) {
        SettingsItem(
            title = "Theme",
            currentValue = "Dark",
            onClick = {
                themeDialogState.show()
            }
        )
        Divider()
    }

    ThemeDialog(
        dialogState = themeDialogState,
        closeDialog = { themeDialogState.hide() }
    )
}

@Composable
fun ThemeDialog(
    dialogState: SheetState,
    closeDialog: () -> Unit,
) {
    var isDarkTheme by remember { mutableStateOf(true) }

    ListDialog(
        state = dialogState,
        header = Header.Default(title = "Theme"),
        selection = ListSelection.Single(
            showRadioButtons = true,
            options = listOf(
                ListOption(
                    titleText = "Dark",
                    selected = isDarkTheme,
                ),
                ListOption(
                    titleText = "Light",
                    selected = !isDarkTheme
                ),
            ),
            positiveButton = null,
        ) { index, option ->
            isDarkTheme = option.titleText == "Dark"
        }
    )
}

@Composable
fun SettingsItem(
    title: String,
    currentValue: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = currentValue,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}