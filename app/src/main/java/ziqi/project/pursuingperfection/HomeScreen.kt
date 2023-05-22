package ziqi.project.pursuingperfection

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    items: List<String> = listOf("a", "B", "C", "D", "E", "F", "G")
) {
    Column(modifier = modifier) {
        LazyRow(
            //modifier = modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp)
        ) {
            items(items = items) {
                Card(
                    modifier = Modifier
                        .widthIn(min = 80.dp)
                        .heightIn(min = 80.dp)
                ) {
                    Text(modifier = Modifier.padding(8.dp), text = it)
                }
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = List(5) { TaskUiState() }) {
                TaskOverviewCard()
            }
            item { Spacer(modifier = Modifier.height(0.dp)) }
        }
    }
}

@Composable
fun TaskOverviewCard(taskUiState: TaskUiState = TaskUiState()) {
    val checkedIcon = Icons.Default.CheckCircle
    val uncheckedIcon = Icons.Default.Check
    var checked by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val constraintHeight = 65.dp
    val expandedModifier = if (expanded) Modifier else Modifier.height(constraintHeight)

    Card(
        modifier = Modifier
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
                    Column() {
                        Text(
                            modifier = Modifier,
                            text = taskUiState.userName,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            modifier = Modifier,
                            text = taskUiState.timeCreated.toString(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                IconButton(modifier = Modifier, onClick = { checked = !checked }) {
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