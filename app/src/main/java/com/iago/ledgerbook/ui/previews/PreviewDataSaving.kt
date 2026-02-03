package com.iago.ledgerbook.ui.previews

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import com.iago.ledgerbook.data.Saving
import com.iago.ledgerbook.ui.theme.Blue


object PreviewDataSaving {
    val saving = Saving(
        id = 1,
        icon = Icons.Default.AirplanemodeActive,
        color = Blue,
        title = "Viagem",
        value = 250.0
    )

    val savingList = List(5) { index ->
        saving.copy(id = index,)
    }
}