package com.myplanner.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.myplanner.app.ui.screens.*
import com.myplanner.app.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home     : Screen("home",     "Beranda",  Icons.Rounded.Home)
    object Tasks    : Screen("tasks",    "Tugas",    Icons.Rounded.Assignment)
    object Planner  : Screen("planner",  "Planner",  Icons.Rounded.CalendarMonth)
    object Schedule : Screen("schedule", "Jadwal",   Icons.Rounded.School)
    object Settings : Screen("settings", "Setelan",  Icons.Rounded.Settings)
}

val bottomNavItems = listOf(Screen.Home, Screen.Tasks, Screen.Planner, Screen.Schedule, Screen.Settings)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyPlannerTheme {
                MyPlannerRoot()
            }
        }
    }
}

@Composable
fun MyPlannerRoot() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DeepSpace,
        bottomBar = {
            MyPlannerBottomNav(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToTasks    = { navController.navigate(Screen.Tasks.route) },
                    onNavigateToPlanner  = { navController.navigate(Screen.Planner.route) },
                    onNavigateToSchedule = { navController.navigate(Screen.Schedule.route) }
                )
            }
            composable(Screen.Tasks.route)    { TasksScreen() }
            composable(Screen.Planner.route)  { PlannerScreen() }
            composable(Screen.Schedule.route) { ScheduleScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}

@Composable
fun MyPlannerBottomNav(navController: androidx.navigation.NavController) {
    val navBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStack?.destination

    NavigationBar(
        containerColor = Color(0xFF0D1117),
        tonalElevation = 0.dp,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        bottomNavItems.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                icon = {
                    Icon(screen.icon, contentDescription = screen.label,
                        modifier = Modifier.size(22.dp))
                },
                label = { Text(screen.label, fontSize = 10.sp) },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NeonBlue,
                    selectedTextColor = NeonBlue,
                    indicatorColor = NeonBlue.copy(alpha = 0.15f),
                    unselectedIconColor = Color(0xFF64748B),
                    unselectedTextColor = Color(0xFF64748B)
                )
            )
        }
    }
}
