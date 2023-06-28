package ziqi.project.pursuingperfection.common.card

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.ui.theme.md_theme_light_secondaryContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityCard(
    selected: Boolean,
    priority: String,
    @DrawableRes painterRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
) {
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    val cardColor by remember { mutableStateOf(defaultColor) }
    val animatedCardColor = animateColorAsState(targetValue = cardColor)
    Card(
        onClick = { onClick() },
        shape = shape,
        colors = if (selected) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        else CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = painterRes),
                contentDescription = null,
                modifier = Modifier.weight(0.7f),
            )
            Text(
                text = priority,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(0.3f),
            )
        }
    }

}