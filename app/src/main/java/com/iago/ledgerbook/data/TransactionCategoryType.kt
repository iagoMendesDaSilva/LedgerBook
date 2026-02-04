package com.iago.ledgerbook.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BedroomBaby
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Elderly
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.iago.ledgerbook.R
import com.iago.ledgerbook.ui.theme.Blue
import com.iago.ledgerbook.ui.theme.BlueGrey
import com.iago.ledgerbook.ui.theme.Brown
import com.iago.ledgerbook.ui.theme.Cyan
import com.iago.ledgerbook.ui.theme.DarkOrange
import com.iago.ledgerbook.ui.theme.DeepPurple
import com.iago.ledgerbook.ui.theme.Green
import com.iago.ledgerbook.ui.theme.Grey
import com.iago.ledgerbook.ui.theme.LightBlue
import com.iago.ledgerbook.ui.theme.LightGreen
import com.iago.ledgerbook.ui.theme.LightPink
import com.iago.ledgerbook.ui.theme.LightPurple
import com.iago.ledgerbook.ui.theme.LightYellow
import com.iago.ledgerbook.ui.theme.Lime
import com.iago.ledgerbook.ui.theme.Orange
import com.iago.ledgerbook.ui.theme.Pink
import com.iago.ledgerbook.ui.theme.Purple
import com.iago.ledgerbook.ui.theme.Red
import com.iago.ledgerbook.ui.theme.Yellow

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
        Icons.Default.ReceiptLong,
        Cyan
    ),

    TRANSPORT(
        R.string.transport,
        Icons.Default.DirectionsCar,
        Orange
    ),

    SELFCARE(
        R.string.selfcare,
        Icons.Default.Spa,
        DeepPurple
    ),

    FOOD(
        R.string.food,
        Icons.Default.Restaurant,
        DarkOrange
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
        LightPurple
    ),

    GIFT(
        R.string.gift,
        Icons.Default.CardGiftcard,
        LightPink
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

    TRIP(
        R.string.trip,
        Icons.Default.FlightTakeoff,
        LightYellow
    ),

    HOUSE(
        R.string.house,
        Icons.Default.Home,
        Blue
    ),

    RETIREMENT(
        R.string.retirement,
        Icons.Default.Elderly,
        BlueGrey
    ),

    PARTY(
        R.string.party,
        Icons.Default.Cake,
        Yellow
    ),

    CHILDREN(
        R.string.children,
        Icons.Default.BedroomBaby,
        LightPurple
    ),

    WEDDING(
        R.string.wedding,
        Icons.Default.Favorite,
        Pink
    ),
}
