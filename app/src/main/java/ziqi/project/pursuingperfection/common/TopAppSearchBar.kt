package ziqi.project.pursuingperfection.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.viewModel.SearchResultViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppSearchBar(
    route: String,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel()
) {
    var query by remember {
        mutableStateOf("")
    }
    val active = query.isNotEmpty()
    val testContent = listOf("A", "B", "C")

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
            onQueryChange = { query = it },
            onSearch = { query = "" },
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
            testContent.forEach {
                TopAppSearchResultCard(input = it)
            }
        }
        if (!active) {
            IconButton(modifier = Modifier.offset(y = 12.dp), onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppSearchResultCard(input: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.clip(
                    CircleShape
                )
            )
            Column() {
                Text(text = "This is title", style = MaterialTheme.typography.labelMedium)
                Text(text = "This is content", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}