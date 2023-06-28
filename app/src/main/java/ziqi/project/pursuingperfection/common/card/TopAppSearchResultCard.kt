package ziqi.project.pursuingperfection.common.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.uiState.SearchResultUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppSearchResultCard(query: String, onClick: (Int) -> Unit, uiState: SearchResultUiState) {
    val matchedTitle = query.toRegex(RegexOption.IGNORE_CASE).find(uiState.title)?.value
    val matchedContent = query.toRegex(RegexOption.IGNORE_CASE).find(uiState.content)?.value
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = { onClick(uiState.id) },
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = uiState.profilePhoto),
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
            Column(verticalArrangement = Arrangement.Top) {
                Text(
                    text = if (matchedTitle == null) AnnotatedString(uiState.title)
                    else buildAnnotatedString {
                        append(uiState.title.substringBefore(matchedTitle))
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.inverseOnSurface,
                                background = MaterialTheme.colorScheme.inverseSurface
                            )
                        ) {
                            append(matchedTitle)
                        }
                        append(uiState.title.substringAfter(matchedTitle, ""))
                    },
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = if (matchedContent == null) AnnotatedString(uiState.content)
                    else buildAnnotatedString {
                        append(uiState.content.substringBefore(matchedContent))
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.inverseOnSurface,
                                background = MaterialTheme.colorScheme.inverseSurface
                            )
                        ) {
                            append(matchedContent)
                        }
                        append(uiState.content.substringAfter(matchedContent, ""))
                    },
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}