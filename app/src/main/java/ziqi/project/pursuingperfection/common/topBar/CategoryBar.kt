package ziqi.project.pursuingperfection.common.topBar

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.common.card.CategoryCard
import ziqi.project.pursuingperfection.common.card.CategoryCardVariant
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.viewModel.newViewModel.NewCategoryViewModel

@Composable
fun CategoryBar(
    onNewClick: (String) -> Unit,
    onEditClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    setVisible: (Boolean) -> Unit,
    viewModel: NewCategoryViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
    var categoryName by rememberSaveable {
        mutableStateOf("All")
    }
    //val taskOverViewListState = rememberLazyListState()
    val categories = viewModel.listUiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val allCategory = CategoryUiState(category = "All")
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp)
    ) {
        item {
            CategoryCard(
                categoryUiState = allCategory,
                selected = categoryName == "All",
                onClick = {
                    Log.d("categoryName", categoryName)
                    categoryName = it
                    onCategoryClick(it)
                    coroutineScope.launch {
                        setVisible(false)
                        delay(300)
                        //taskOverViewListState.scrollToItem(0)
                        //taskOverViewListState.animateScrollToItem(0)
                        setVisible(true)
                    }
                }
            )
        }
        items(
            items = categories.value,
            key = { it.id }
        ) { categoryUiState ->
            CategoryCard(
                categoryUiState = categoryUiState,
                selected = categoryUiState.category == categoryName,
                onClick = {
                    categoryName = it
                    onCategoryClick(it)
                    //viewModel.saveCurrentCategory(categoryName)
                    coroutineScope.launch {
                        setVisible(false)
                        delay(300)
                        //taskOverViewListState.scrollToItem(0)
                        //taskOverViewListState.animateScrollToItem(0)
                        setVisible(true)
                    }
                }
            )
        }
        item {
            CategoryCardVariant(onClick = { onNewClick("new") }, imageVector = Icons.Default.Add)
        }
        item {
            CategoryCardVariant(
                onClick = { onEditClick(categoryName) },
                imageVector = Icons.Default.Edit
            )
        }
        item {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}