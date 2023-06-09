package ziqi.project.pursuingperfection.common.card

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.common.SmallAvatar
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.utils.shortConvert


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskOverviewCard(
    currentChecked: Boolean,
    uiState: TaskUiState,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var checked by remember { mutableStateOf(currentChecked) }
    var expanded by remember { mutableStateOf(false) }
    val constraintHeight = 100.dp
    val expandedModifier = if (expanded) Modifier else Modifier.height(constraintHeight)

    val cardContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
    val priority = when (uiState.priority) {
        0 -> R.drawable.bolt
        1 -> R.drawable.cloudy
        else -> R.drawable.sunny
    }
    val contents = if (expanded) uiState.contents else uiState.contents.subList(
        0,
        minOf(3, uiState.contents.size)
    )
    val showExpand = uiState.contents.size > 3

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
        onClick = { onClick(uiState.id) },
        colors = CardDefaults.cardColors(containerColor = cardContainerColor),
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmallAvatar(){}
                    Column {
                        Text(
                            modifier = Modifier,
                            text = uiState.category,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            modifier = Modifier,
                            text = "${uiState.timeStart.shortConvert()} - ${uiState.timeEnd.shortConvert()}",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                Icon(
                    painter = painterResource(id = priority),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    //tint = MaterialTheme.colorScheme.tertiary
                )

            }
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                text = uiState.title,
                style = MaterialTheme.typography.titleLarge
            )

            Column(modifier = Modifier) {
                contents.forEach {
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
            }
            if (showExpand) {
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
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}