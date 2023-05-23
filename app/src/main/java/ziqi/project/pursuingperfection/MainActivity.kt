package ziqi.project.pursuingperfection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppSearchBar() },
        bottomBar = { BottomNavigationBar() },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier,
                shape = MaterialTheme.shapes.medium,
                onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        HomeScreen(modifier = Modifier.padding(it))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppSearchBar(modifier: Modifier = Modifier) {
    var query by remember {
        mutableStateOf("")
    }
    val active = query.isNotEmpty()

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        //verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 8.dp),
            active = active,
            onActiveChange = {},
            query = query,
            onQueryChange = {query = it},
            onSearch = {query = ""},
            leadingIcon = {
                if (active) IconButton(onClick = { query = "" }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
                else Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            placeholder = {
                Text(text = "Search")
            }
        ) {
        }
        if (active){
            IconButton(modifier = Modifier.offset(y = 12.dp), onClick = { query = "" }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
        else {
            IconButton(modifier = Modifier.offset(y = 12.dp), onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }

    }

}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<Pair<ImageVector, String>> = listOf(
        Icons.Default.Home to "Home",
        Icons.Default.Settings to "Settings"
    )
) {
    var selectedIndex by remember { mutableStateOf(0) }
    NavigationBar(modifier = modifier) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedIndex,
                onClick = { selectedIndex = index },
                icon = { Icon(imageVector = item.first, contentDescription = item.second) },
                label = { Text(text = item.second, style = MaterialTheme.typography.labelMedium) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme() {
        MainScreen()
    }
}