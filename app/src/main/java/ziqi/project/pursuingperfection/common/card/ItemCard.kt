package ziqi.project.pursuingperfection.common.card

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.common.TaskTextField
import ziqi.project.pursuingperfection.uiState.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    item: Item,
    inEdit: Boolean,
    onCardClick: (Int) -> Unit,
    onCardRemove: (Item) -> Unit,
    onCheckChange: (Item) -> Unit,
    onEditFinish: (Item) -> Unit,
    scroll: (Float) -> Unit
) {
    val checked: Boolean
    val textDecoration: TextDecoration

    when (item.checked) {
        true -> {
            checked = true
            textDecoration = TextDecoration.LineThrough
        }

        false -> {
            checked = false
            textDecoration = TextDecoration.None
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(80.dp)
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 16.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
        ),
        onClick = { onCardClick(item.id) }
    ) {
        when (inEdit) {
            false -> {
                Row(
                    modifier = Modifier
                        .heightIn(80.dp)
                        .padding(start = 4.dp, top = 14.dp, bottom = 14.dp, end = 12.dp)
                        .offset(x = (-2).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = checked, onCheckedChange = {
                        onCheckChange(item)
                        Log.d("testTaskId", item.id.toString())
                    })
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(text = item.content, textDecoration = textDecoration)
                        //Divider()
                    }
                }
            }

            true -> Row(
                modifier = Modifier
                    .heightIn(80.dp)
                    .padding(start = 4.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    modifier = Modifier.offset(x = (-2).dp)
                )
                TaskTextField(
                    item = item,
                    onEditFinish = onEditFinish,
                    onCardRemove = onCardRemove,
                    scroll = scroll,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }

    }
}