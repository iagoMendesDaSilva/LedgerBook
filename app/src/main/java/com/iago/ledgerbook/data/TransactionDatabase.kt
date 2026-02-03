package com.iago.ledgerbook.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iago.ledgerbook.utils.Converters

@Database(
    entities = [Transaction::class, Saving::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun savingDao(): SavingDao
}