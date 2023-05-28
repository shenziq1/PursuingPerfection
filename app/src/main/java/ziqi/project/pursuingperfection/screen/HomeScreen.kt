package ziqi.project.pursuingperfection.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.data.LocalCategoryDataProvider
import ziqi.project.pursuingperfection.viewModel.TaskListViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskListViewModel = hiltViewModel(),
    checked: Boolean = false
) {
    var categoryName by remember {
        mutableStateOf("All")
    }
    val taskOverViewListState = rememberLazyListState()
    val tasks =
        if (checked) viewModel.checkedTasks.collectAsStateWithLifecycle()
        else viewModel.plannedTasks.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp)
            ) {
                items(items = LocalCategoryDataProvider.allCategories) { categoryUiState ->
                    CategoryCard(categoryUiState = categoryUiState, onClick = {
                        categoryName = it
                        viewModel.updateTaskList(checked, categoryName)
                    })
                }
            }
            AnimatedContent(
                targetState = categoryName,
                transitionSpec = {
                    slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentScope.SlideDirection.Down
                    ).with(
                        slideOutOfContainer(
                            animationSpec = tween(300, easing = EaseOut),
                            towards = AnimatedContentScope.SlideDirection.Up
                        )
                    )
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = taskOverViewListState
                ) {
                    items(
                        items = tasks.value
                    ) {
                        TaskOverviewCard(it, {})
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
            }

        }
        FloatingActionButton(modifier = Modifier.align(Alignment.BottomCenter), onClick = {
            viewModel.insertTask()
        }) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    categoryUiState: CategoryUiState,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.size(80.dp), onClick = { onClick(categoryUiState.name) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.weight(0.7f),
                painter = painterResource(id = categoryUiState.picture),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .weight(0.3f)
                    .padding(horizontal = 8.dp),
                text = categoryUiState.name
            )
        }
    }
}

@Composable
fun TaskOverviewCard(
    taskUiState: TaskUiState,
    onCheck: (TaskUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val checkedIcon = Icons.Default.CheckCircle
    val uncheckedIcon = Icons.Default.Check
    var checked by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val constraintHeight = 65.dp
    val expandedModifier = if (expanded) Modifier else Modifier.height(constraintHeight)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Profile Image"
                    )
                    Column {
                        Text(
                            modifier = Modifier,
                            text = taskUiState.category,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            modifier = Modifier,
                            text = taskUiState.timeCreated.toString(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                IconButton(modifier = Modifier, onClick = {
                    checked = !checked
                    if (checked) onCheck(taskUiState)
                }) {
                    if (checked) Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = checkedIcon,
                        contentDescription = "Checked"
                    )
                    else Icon(imageVector = uncheckedIcon, contentDescription = "Unchecked")
                }
            }
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 12.dp),
                text = taskUiState.title,
                style = MaterialTheme.typography.titleLarge
            )

            Column(modifier = expandedModifier) {
                taskUiState.content.forEach {
                    if (it.second) Text(
                        modifier = Modifier,
                        text = it.first,
                        textDecoration = TextDecoration.LineThrough,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    else Text(
                        modifier = Modifier,
                        text = it.first,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = "Priority: ${taskUiState.priority}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = "•".repeat(taskUiState.lifeSpent),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = "∘".repeat(taskUiState.lifeSpan - taskUiState.lifeSpent),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

            }

            IconButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { expanded = !expanded }) {
                if (expanded) Icon(
                    modifier = Modifier.rotate(180f),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand"
                )
                else Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Undo expand"
                )
            }

        }
    }
}