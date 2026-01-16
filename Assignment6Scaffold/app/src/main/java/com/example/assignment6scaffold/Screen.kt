package com.example.assignment6scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPinCircle
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val route: String, val name: String, val icon: ImageVector) {
    data object Home: Screen(route = "home_screen", name = "Home", icon = Icons.Default.Home)
    data object Friend1: Screen(route = "friend1_screen", name = "Friend1", icon =Icons.Default.Person)
    data object Friend2: Screen(route = "friend2_screen", name = "Friend2", icon = Icons.Default.PersonPinCircle)
}