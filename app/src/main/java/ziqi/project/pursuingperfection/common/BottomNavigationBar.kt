package ziqi.project.pursuingperfection.common

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ziqi.project.pursuingperfection.data.Destination
import ziqi.project.pursuingperfection.data.Destinations

@Composable
fun BottomNavigationBar(
    navigateTo: (String) -> Unit,
    currentDestination: Destination,
    modifier: Modifier = Modifier,
    destinations: List<Destination> = Destinations,
) {
    NavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination.route == destination.route,
                onClick = {
                    navigateTo(destination.route)
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.route
                    )
                },
                label = {
                    Text(
                        text = destination.route,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        }
    }
}