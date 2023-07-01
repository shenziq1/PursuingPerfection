package ziqi.project.pursuingperfection.common.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.uiState.CategoryUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    categoryUiState: CategoryUiState,
    selected: Boolean,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    shape: CornerBasedShape = MaterialTheme.shapes.small,
) {
    Card(
        modifier = modifier.size(80.dp),
        onClick = { onClick(categoryUiState.category) },
        shape = shape,
        colors = if (selected) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        else CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.weight(0.7f),
                painter = painterResource(id = categoryUiState.profilePhoto),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .weight(0.3f)
                    .padding(horizontal = 8.dp),
                text = categoryUiState.category,
                style = style
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCardVariant(
    onClick: () -> Unit,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.small,
) {
    Card(
        modifier = modifier.size(80.dp),
        onClick = onClick,
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}