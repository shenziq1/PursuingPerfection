package ziqi.project.pursuingperfection.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ziqi.project.pursuingperfection.R

@Composable
fun SmallAvatar(
    painterRes: Int = R.drawable.ic_launcher_foreground,
    onClick: ()->Unit = {},
) {
    Image(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape).clickable {  },
        painter = painterResource(id = painterRes),
        contentDescription = "Profile Image"
    )
}