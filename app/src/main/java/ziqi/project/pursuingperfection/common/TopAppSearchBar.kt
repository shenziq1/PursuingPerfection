package ziqi.project.pursuingperfection.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ziqi.project.pursuingperfection.data.Home
import ziqi.project.pursuingperfection.uiState.SearchResultUiState
import ziqi.project.pursuingperfection.viewModel.SearchResultViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppSearchBar(
    route: String,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel()
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var active by rememberSaveable {
        mutableStateOf(false)
    }
    val searchResult =
        if (route == Home.route) viewModel.homePageSearchResult.collectAsStateWithLifecycle()
        else viewModel.donePageSearchResult.collectAsStateWithLifecycle()

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        SearchBar(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 8.dp),
            active = active,
            onActiveChange = { active = it },
            query = query,
            onQueryChange = {
                query = it
                viewModel.updateSearchResult(route, query)
            },
            onSearch = {
                query = ""
                active = false
            },
            leadingIcon = {
                if (active) IconButton(onClick = {
                    query = ""
                    active = false
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
                else Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            placeholder = {
                Text(text = "Search")
            }
        ) {
            searchResult.value.forEach {
                TopAppSearchResultCard(query = query, searchResultUiState = it)
            }
        }
//        if (!active) {
//            IconButton(modifier = Modifier.offset(y = 12.dp), onClick = { }) {
//                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
//            }
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppSearchResultCard(query: String, searchResultUiState: SearchResultUiState) {
    val matchedQuery = query.toRegex(RegexOption.IGNORE_CASE).find(searchResultUiState.content)?.value
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = searchResultUiState.profilePhoto),
                contentDescription = null,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant,
                        MaterialTheme.shapes.small
                    ),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column() {
                Text(
                    text = searchResultUiState.title,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = if (matchedQuery == null) AnnotatedString(searchResultUiState.content)
                    else buildAnnotatedString {
                        append(searchResultUiState.content.substringBefore(matchedQuery))
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                background = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            append(matchedQuery)
                        }
                        append(searchResultUiState.content.substringAfter(matchedQuery, ""))
                    },
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }
}