package ziqi.project.pursuingperfection.common

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.common.topBar.TopAppSearchBar
import ziqi.project.pursuingperfection.data.Destination
import ziqi.project.pursuingperfection.data.Destinations
import ziqi.project.pursuingperfection.data.Home
import ziqi.project.pursuingperfection.ui.theme.AppTheme

@Composable
fun BottomNavigationBar(
    navigateTo: (String) -> Unit,
    currentDestination: Destination,
    modifier: Modifier = Modifier,
    destinations: List<Destination> = Destinations,
) {
    NavigationBar(
        modifier = modifier,
        //contentColor = MaterialTheme.colorScheme.surfaceTint,
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
    ) {
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

@Preview
@Composable
fun AppTopBarPreview() {
    AppTheme() {
        TopAppSearchBar(route = Home.route, onResultClick = {})
    }
}