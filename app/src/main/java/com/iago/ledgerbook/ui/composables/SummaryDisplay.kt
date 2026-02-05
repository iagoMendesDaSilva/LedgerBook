package com.iago.ledgerbook.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iago.ledgerbook.R
import com.iago.ledgerbook.data.SummaryData
import com.iago.ledgerbook.data.TransactionType
import com.iago.ledgerbook.ui.theme.DarkBackground
import com.iago.ledgerbook.ui.theme.DarkTurquoise
import com.iago.ledgerbook.ui.theme.LedgerBookTheme
import com.iago.ledgerbook.ui.theme.Red
import java.util.Locale

@Composable
fun SummaryDisplay(
    summaryData: SummaryData,
    transactionType: TransactionType,
    onChangeTransactionType: (TransactionType) -> Unit
) {
    val remaining = summaryData.incomes - (summaryData.expenses + summaryData.savings)
    val formattedRemaining = String.format( Locale("pt", "BR"), "%,.2f", remaining)
    val (mainValue, decimals) = formattedRemaining.split(",")

    Card(
        shape = MaterialTheme.shapes.extraLarge,
        contentColor = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .drawBehind {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                DarkTurquoise,
                                DarkBackground
                            ),
                            center = Offset(
                                x = size.width,
                                y = 0f
                            ),
                            radius = size.maxDimension
                        )
                    )
                }
                .padding(24.dp)
        ) {
            Column {

                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.remaining),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "R$$mainValue",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White
                        )

                        Text(
                            text = ",$decimals",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp,)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Divider(
                    color = Color.White.copy(alpha = 0.1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SummaryItem(
                        summaryData,
                        transactionType,
                        TransactionType.INCOME
                    ) {
                        onChangeTransactionType(TransactionType.INCOME)
                    }
                    SummaryItem(
                        summaryData,
                        transactionType,
                        TransactionType.EXPENSE
                    ) {
                        onChangeTransactionType(TransactionType.EXPENSE)
                    }
                    SummaryItem(
                        summaryData,
                        transactionType,
                        TransactionType.SAVING
                    ) {
                        onChangeTransactionType(TransactionType.SAVING)
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryItem(
    summaryData: SummaryData,
    currentTransactionType: TransactionType,
    transactionType: TransactionType,
    onPress: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onPress() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(
                when (transactionType) {
                    TransactionType.EXPENSE -> R.string.expense
                    TransactionType.INCOME -> R.string.income
                    TransactionType.SAVING -> R.string.saving
                }
            ),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "R$${
                String.format(
                    Locale("pt", "BR"), "%,.2f", when (transactionType) {
                        TransactionType.EXPENSE -> summaryData.expenses
                        TransactionType.INCOME -> summaryData.incomes
                        TransactionType.SAVING -> summaryData.savings
                    }
                )
            }",
            style = MaterialTheme.typography.titleMedium,
            color = if (currentTransactionType == transactionType) {
                if (transactionType == TransactionType.EXPENSE) Color.Red
                else MaterialTheme.colorScheme.primary
            } else Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryDisplayPreview() {
    LedgerBookTheme {
        Surface {
            SummaryDisplay(
                SummaryData(
                    incomes = 4000.0,
                    expenses = 1200.99,
                    savings = 500.55,
                ),
                TransactionType.SAVING
            ) {}
        }
    }
}
