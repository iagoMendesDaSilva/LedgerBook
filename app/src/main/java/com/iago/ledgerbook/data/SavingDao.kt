package com.iago.ledgerbook.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface SavingDao {

    @Query("SELECT * FROM savings WHERE id=:id")
    suspend fun getSaving(id: Int): Saving

    @Query("SELECT * FROM savings")
    suspend fun getSavings(): List<Saving>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaving(saving: Saving)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSaving(saving: Saving)

    @Delete
    suspend fun deleteSaving(saving: Saving)

}