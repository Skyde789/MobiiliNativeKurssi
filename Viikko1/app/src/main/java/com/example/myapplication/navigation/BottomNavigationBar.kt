import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavController,
    onAddClick: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Filled.List, contentDescription = "Tasks") },
            label = { Text("Tasks") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onAddClick() },
            icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
            label = { Text("Add") }
        )
        NavigationBarItem(
            selected = currentRoute == "calendar",
            onClick = { navController.navigate("calendar") },
            icon = { Icon(Icons.Filled.CalendarToday, contentDescription = "Calendar") },
            label = { Text("Calendar") }
        )
    }
}

