package com.iago.ledgerbook.di

import android.content.Context
import androidx.room.Room
import com.iago.ledgerbook.data.AppDatabase
import com.iago.ledgerbook.data.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "ledgerbook_db"
            ).fallbackToDestructiveMigration(false)
            .build()

    @Singleton
    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao =
        appDatabase.transactionDao()
}

