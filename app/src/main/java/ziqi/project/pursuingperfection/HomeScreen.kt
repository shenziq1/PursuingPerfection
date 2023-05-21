package ziqi.project.pursuingperfection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        LazyColumn() {
            items(items = items) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {
                    Text(modifier = Modifier.padding(8.dp), text = it)
                }
            }
        }
    }

}