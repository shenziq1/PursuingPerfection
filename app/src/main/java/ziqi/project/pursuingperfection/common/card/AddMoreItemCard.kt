package ziqi.project.pursuingperfection.common.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.common.TaskTextField
import ziqi.project.pursuingperfection.uiState.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMoreItemCard(
    inEdit: Boolean,
    newItemId: Int,
    onCardClick: () -> Unit,
    onCardRemove: (Item) -> Unit,
    onEditFinish: (Item) -> Unit,
    scroll: (Float) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(80.dp)
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
        ),
        onClick = onCardClick
    ) {
        Row(
            modifier = Modifier
                .heightIn(80.dp)
                .padding(start = 4.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (inEdit) {
                true -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = false,
                            onCheckedChange = {},
                            modifier = Modifier.offset(x = (-2).dp)
                        )
                        TaskTextField(
                            item = Item(id = newItemId),
                            onEditFinish = onEditFinish,
                            onCardRemove = onCardRemove,
                            scroll = scroll,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }

                false -> {
                    IconButton(onClick = onCardClick, modifier = Modifier.offset(x = (-2).dp)) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Task",
                            modifier = Modifier
                                .size(20.dp)
                                .border(
                                    width = 1.7.dp,
                                    color = LocalContentColor.current,
                                    shape = MaterialTheme.shapes.extraSmall
                                )
                        )
                    }
                }
            }
        }
    }
}