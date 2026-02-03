package com.iago.ledgerbook.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.iago.ledgerbook.data.Saving
import com.iago.ledgerbook.data.SummaryData
import com.iago.ledgerbook.data.Transaction
import com.iago.ledgerbook.data.TransactionType
import com.iago.ledgerbook.ui.composables.SavingCard
import com.iago.ledgerbook.ui.composables.SummaryDisplay
import com.iago.ledgerbook.ui.composables.TransactionCard
import com.iago.ledgerbook.ui.previews.PreviewDataSaving
import com.iago.ledgerbook.ui.previews.PreviewDataTransaction
import com.iago.ledgerbook.ui.theme.LedgerBookTheme
import com.iago.ledgerbook.utils.DevicePreviews

@Composable
fun TransactionsScreen() {
    val context = LocalContext.current
    val activity = context as Activity
    ViewCompat.getWindowInsetsController(activity.window.decorView)?.apply {
        hide(WindowInsetsCompat.Type.systemBars())
        systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    val viewModel = hiltViewModel<TransactionsViewModel>()
    val transactions by viewModel.transactions.collectAsState()
    val savings by viewModel.savings.collectAsState()
    val currentScreen = remember { mutableStateOf(TransactionType.EXPENSE) }

    LaunchedEffect(Unit) {
        viewModel.fetchTransactions()
        viewModel.fetchSavings()
    }

    TransactionsScreenUI(
        transactions,
        savings,
        currentScreen.value,
        addTransaction = { viewModel.addTransaction(it) },
        updateTransaction = { viewModel.updateTransaction(it) },
        deleteTransaction = { viewModel.deleteTransaction(it) },
        onChangeTransactionType = { transactionType -> currentScreen.value = transactionType }
    )
}

@Composable
fun TransactionsScreenUI(
    transactions: List<Transaction>,
    savings: List<Saving>,
    currentScreen: TransactionType,
    addTransaction: (Transaction) -> Unit,
    updateTransaction: (Transaction) -> Unit,
    deleteTransaction: (Transaction) -> Unit,
    onChangeTransactionType: (TransactionType) -> Unit
) {
    val filteredTransactions = transactions.filter { it.type == currentScreen }
    val summaryData = getSummaryData(transactions, savings)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        SummaryDisplay(summaryData, currentScreen) { transactionType ->
            onChangeTransactionType(transactionType)
        }
        Spacer(Modifier.height(15.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(if (currentScreen == TransactionType.SAVING) 2 else 1),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            when (currentScreen) {
                TransactionType.INCOME,
                TransactionType.EXPENSE -> {
                    items(filteredTransactions) { transaction ->
                        TransactionCard(transaction)
                    }
                }

                TransactionType.SAVING -> {
                    items(savings) { saving ->
                        SavingCard(saving)
                    }
                }
            }
        }

    }
}

fun getSummaryData(transactions: List<Transaction>, savings: List<Saving>): SummaryData {
    var totalIncome = 0.0
    var totalExpense = 0.0
    var totalSaving = 0.0

    transactions.forEach { transaction ->
        when (transaction.type) {
            TransactionType.INCOME -> totalIncome += transaction.value
            TransactionType.EXPENSE -> totalExpense += transaction.value
            TransactionType.SAVING -> {}
        }
    }

    savings.forEach { saving ->
        totalSaving += saving.value
    }

    return SummaryData(
        incomes = totalIncome,
        expenses = totalExpense,
        savings = totalSaving
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@DevicePreviews
@Composable
fun PotsPreview() {
    LedgerBookTheme {
        Scaffold {
            TransactionsScreenUI(
                PreviewDataTransaction.transactionList,
                PreviewDataSaving.savingList,
                TransactionType.SAVING,
                {},
                {},
                {},
            ) {}
        }
    }
}