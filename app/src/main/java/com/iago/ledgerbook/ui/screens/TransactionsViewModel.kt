package com.iago.ledgerbook.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.ledgerbook.data.Transaction
import com.iago.ledgerbook.data.TransactionDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionDao: TransactionDao,
) : ViewModel() {

    val transactions = transactionDao.getTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTransaction(transaction: Transaction) =
        viewModelScope.launch {
            transactionDao.insertTransaction(transaction)
        }

    fun updateTransaction(transaction: Transaction) =
        viewModelScope.launch {
            transactionDao.updateTransaction(transaction)
        }

    fun deleteTransaction(transaction: Transaction) =
        viewModelScope.launch {
            transactionDao.deleteTransaction(transaction)
        }
}