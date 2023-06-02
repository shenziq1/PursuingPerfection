package ziqi.project.pursuingperfection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ziqi.project.pursuingperfection.common.BottomNavigationBar
import ziqi.project.pursuingperfection.common.TopAppSearchBar
import ziqi.project.pursuingperfection.data.Destinations
import ziqi.project.pursuingperfection.data.Done
import ziqi.project.pursuingperfection.data.Home
import ziqi.project.pursuingperfection.data.Settings
import ziqi.project.pursuingperfection.data.navigateSingleTopTo
import ziqi.project.pursuingperfection.screen.DoneScreen
import ziqi.project.pursuingperfection.screen.HomeScreen
import ziqi.project.pursuingperfection.screen.MainScreen
import ziqi.project.pursuingperfection.screen.SettingsScreen
import ziqi.project.pursuingperfection.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}