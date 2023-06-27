package ziqi.project.pursuingperfection.common.topBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.common.SmallAvatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailTopBar(
    title: String,
    timeStart: String,
    timeEnd: String,
    profilePhoto: Int,
    category: String,
    priority: Int,
    onBackClick: () -> Unit,
    onTitleClick: () -> Unit,
    onTimeClick: () -> Unit,
    onCategoryClick: () -> Unit,
    onPriorityClick: () -> Unit,
    onDelete: () -> Unit
) {
    val priorityIcon = when (priority) {
        0 -> R.drawable.bolt
        1 -> R.drawable.cloudy
        else -> R.drawable.sunny
    }
    var expand by remember {
        mutableStateOf(false)
    }
    LargeTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.fillMaxWidth(0.85f)) {
                    TextButton(
                        onClick = onTitleClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineMedium,
                            maxLines = 2
                        )
                    }
                }
                IconButton(onClick = onPriorityClick) {
                    Icon(
                        painter = painterResource(id = priorityIcon),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            SmallAvatar(painterRes = profilePhoto, onClick = onCategoryClick)
            TextButton(
                onClick = onTimeClick,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
            ) {
                Text(text = "$timeStart - $timeEnd", style = MaterialTheme.typography.labelLarge)
            }
            Column() {
                IconButton(onClick = { expand = !expand }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
                DropdownMenu(expanded = expand, onDismissRequest = { expand = false }) {
                    DropdownMenuItem(text = { Text(text = "Delete") }, onClick = onDelete)
                }
            }

            //Spacer(modifier = Modifier.width(16.dp))
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
        )
    )
}