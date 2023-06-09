package ziqi.project.pursuingperfection.common

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.uiState.TaskUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskOverviewCard(
    currentChecked: Boolean,
    taskUiState: TaskUiState,
    onCheck: (TaskUiState) -> Unit,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val checkedIcon = Icons.Default.CheckCircle
    val uncheckedIcon = Icons.Default.Check
    var checked by remember { mutableStateOf(currentChecked) }
    var expanded by remember { mutableStateOf(false) }
    val constraintHeight = 100.dp
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
            ),
        onClick = {onClick(taskUiState.id)}
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmallAvatar()
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
                    onCheck(taskUiState)
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
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                text = taskUiState.title,
                style = MaterialTheme.typography.titleLarge
            )

            Column(modifier = expandedModifier) {
                taskUiState.contents.forEach {
                    if (it.checked) Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    baselineShift = BaselineShift(-0.1f)
                                )
                            ) {
                                append(" \u2022 ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    textDecoration = TextDecoration.LineThrough
                                )
                            ) {
                                append(it.content)
                            }
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                    else Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    baselineShift = BaselineShift(-0.1f)
                                )
                            ) {
                                append(" \u2022 ")
                            }
                            append(it.content)
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(modifier = Modifier.padding(top = 12.dp),
                        text = "Priority: ${taskUiState.priority}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(modifier = Modifier.padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "•".repeat(taskUiState.lifeSpent),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        Text(
                            text = "•".repeat(taskUiState.lifeSpan - taskUiState.lifeSpent),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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