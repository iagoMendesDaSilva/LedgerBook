package com.iago.ledgerbook.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.iago.ledgerbook.R
import com.iago.ledgerbook.ui.theme.*

enum class TransactionCategory(
    @StringRes val title: Int,
    val icon: ImageVector,
    val color: Color
) {

    HEALTH(
        R.string.emergency,
        Icons.Default.LocalHospital,
        Red
    ),

    BILLS(
        R.string.bill,
        Icons.Default.Home,
        Blue
    ),

    TRANSPORT(
        R.string.transport,
        Icons.Default.DirectionsCar,
        Yellow
    ),

    SELFCARE(
        R.string.selfcare,
        Icons.Default.Favorite,
        Pink
    ),

    FOOD(
        R.string.food,
        Icons.Default.Restaurant,
        Orange
    ),

    SHOPPING(
        R.string.shopping,
        Icons.Default.ShoppingBag,
        Purple
    ),

    PET(
        R.string.pet,
        Icons.Default.Pets,
        Brown
    ),

    EDUCATION(
        R.string.education,
        Icons.Default.School,
        LightBlue
    ),

    LEISURE(
        R.string.leisure,
        Icons.Default.Movie,
        Cyan
    ),

    GIFT(
        R.string.gift,
        Icons.Default.CardGiftcard,
        DeepPurple
    ),

    SALARY(
        R.string.salary,
        Icons.Default.AttachMoney,
        Green
    ),

    BONUS(
        R.string.bonus,
        Icons.Default.Star,
        LightGreen
    ),

    REIMBURSEMENT(
        R.string.reimbursement,
        Icons.Default.SyncAlt,
        Lime
    )
}
