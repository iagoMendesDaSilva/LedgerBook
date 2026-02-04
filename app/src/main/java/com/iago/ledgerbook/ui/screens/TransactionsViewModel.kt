package com.iago.ledgerbook.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.ledgerbook.data.Transaction
import com.iago.ledgerbook.data.TransactionDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionDao: TransactionDao,
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions = _transactions.asStateFlow()

    fun fetchTransactions() {
        viewModelScope.launch {
            val transactionList = transactionDao.getTransactions()
            _transactions.value = transactionList
        }
    }

    private fun launchDb(block: suspend () -> Unit) {
        viewModelScope.launch {
            block()
            fetchTransactions()
        }
    }

    fun addTransaction(transaction: Transaction) =
        launchDb { transactionDao.insertTransaction(transaction) }

    fun updateTransaction(transaction: Transaction) =
        launchDb { transactionDao.updateTransaction(transaction) }

    fun deleteTransaction(transaction: Transaction) =
        launchDb { transactionDao.deleteTransaction(transaction) }
}