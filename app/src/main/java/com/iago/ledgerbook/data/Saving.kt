package com.iago.ledgerbook.data

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "savings")
data class Saving(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val icon: ImageVector,
    val color: Color,
    val title: String,
    val value: Double,
) {
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}