package ziqi.project.pursuingperfection.common

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.data.Category
import ziqi.project.pursuingperfection.data.Home
import ziqi.project.pursuingperfection.data.Time
import ziqi.project.pursuingperfection.data.Title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransitionScreenAppBar(
    pageNumber: Int,
    animatedProgressValue: Float,
    onCancelClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    Surface(
        modifier = Modifier.heightIn(56.dp),
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
    ) {
        Column(modifier = Modifier.padding(top = 16.dp)) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "$pageNumber of 4")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onCancelClick) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
                TextButton(onClick = onSkipClick) {
                    Text(text = "Skip")
                }
            }
            LinearProgressIndicator(
                progress = animatedProgressValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            )
        }
    }

}