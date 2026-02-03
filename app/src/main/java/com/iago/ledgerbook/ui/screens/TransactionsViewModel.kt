package com.iago.ledgerbook.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.ledgerbook.data.Saving
import com.iago.ledgerbook.data.SavingDao
import com.iago.ledgerbook.data.Transaction
import com.iago.ledgerbook.data.TransactionDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionDao: TransactionDao,
    private val savingDao: SavingDao,
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions = _transactions.asStateFlow()

    private val _savings = MutableStateFlow<List<Saving>>(emptyList())
    val savings = _savings.asStateFlow()


    fun fetchTransactions() {
        viewModelScope.launch {
            val transactionList = transactionDao.getTransactions()
            _transactions.value = transactionList
        }
    }

    fun fetchSavings() {
        viewModelScope.launch {
            val savingList = savingDao.getSavings()
            _savings.value = savingList
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

    fun addSaving(saving: Saving) =
        launchDb { savingDao.insertSaving(saving) }

    fun updateSaving(saving: Saving) =
        launchDb { savingDao.updateSaving(saving) }

    fun deleteSaving(saving: Saving) =
        launchDb { savingDao.deleteSaving(saving) }
}