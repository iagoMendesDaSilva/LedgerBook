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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.iago.ledgerbook.data.SummaryData
import com.iago.ledgerbook.data.Transaction
import com.iago.ledgerbook.data.TransactionCategory
import com.iago.ledgerbook.data.TransactionType
import com.iago.ledgerbook.ui.composables.BottomSheetAction
import com.iago.ledgerbook.ui.composables.SavingCard
import com.iago.ledgerbook.ui.composables.SummaryDisplay
import com.iago.ledgerbook.ui.composables.TransactionBottomSheetContent
import com.iago.ledgerbook.ui.composables.TransactionCard
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
    val currentScreen = remember { mutableStateOf(TransactionType.EXPENSE) }

    LaunchedEffect(Unit) {
        viewModel.fetchTransactions()
    }

    TransactionsScreenUI(
        transactions,
        currentScreen.value,
        addTransaction = { viewModel.addTransaction(it) },
        updateTransaction = { viewModel.updateTransaction(it) },
        deleteTransaction = { viewModel.deleteTransaction(it) },
        onChangeTransactionType = { transactionType -> currentScreen.value = transactionType }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreenUI(
    transactions: List<Transaction>,
    currentScreen: TransactionType,
    addTransaction: (Transaction) -> Unit,
    updateTransaction: (Transaction) -> Unit,
    deleteTransaction: (Transaction) -> Unit,
    onChangeTransactionType: (TransactionType) -> Unit
) {
    val filteredTransactions = transactions.filter { it.type == currentScreen }
    val summaryData = getSummaryData(transactions)
    var showBottomSheet by remember { mutableStateOf(BottomSheetAction.CLOSE) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var editItem = remember { mutableStateOf<Transaction?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { showBottomSheet = BottomSheetAction.CREATE }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                            TransactionCard(transaction) {
                                showBottomSheet = BottomSheetAction.EDIT
                                editItem.value = transaction
                            }
                        }
                    }

                    TransactionType.SAVING -> {
                        items(filteredTransactions) { saving ->
                            SavingCard(saving) {
                                showBottomSheet = BottomSheetAction.EDIT
                                editItem.value = saving
                            }
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet != BottomSheetAction.CLOSE) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = BottomSheetAction.CLOSE },
            sheetState = sheetState
        ) {
            TransactionBottomSheetContent(
                currentScreen,
                isEditing = showBottomSheet == BottomSheetAction.EDIT,
                amount = editItem.value?.value,
                description = editItem.value?.title ?: "",
                selectedCategory = editItem.value?.category,
                onSubmit = { value: Double, category: TransactionCategory, title: String -> }
            )
        }
    }
}

fun getSummaryData(transactions: List<Transaction>): SummaryData {
    var totalIncome = 0.0
    var totalExpense = 0.0
    var totalSaving = 0.0

    transactions.forEach { transaction ->
        when (transaction.type) {
            TransactionType.INCOME -> totalIncome += transaction.value
            TransactionType.EXPENSE -> totalExpense += transaction.value
            TransactionType.SAVING -> totalSaving += transaction.value
        }
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
                TransactionType.SAVING,
                {},
                {},
                {},
            ) {}
        }
    }
}