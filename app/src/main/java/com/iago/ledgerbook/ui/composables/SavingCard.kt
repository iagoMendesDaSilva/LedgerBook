package com.iago.ledgerbook.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iago.ledgerbook.data.Saving
import com.iago.ledgerbook.ui.previews.PreviewDataSaving
import com.iago.ledgerbook.ui.theme.LedgerBookTheme

@Composable
fun SavingCard(
    saving: Saving,
) {
    val formattedValue = String.format("%.2f", saving.value)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(.7f),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = saving.color.copy(alpha = 0.15f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Text(
                text = "R$ $formattedValue",
                style = MaterialTheme.typography.displaySmall,
                color = saving.color,
                modifier = Modifier.align(Alignment.Center)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(saving.color),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = saving.icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = saving.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SavingCardPreview() {
    LedgerBookTheme {
        Surface {
            SavingCard(
                PreviewDataSaving.saving
            )
        }
    }
}