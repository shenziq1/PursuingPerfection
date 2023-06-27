package ziqi.project.pursuingperfection.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ziqi.project.pursuingperfection.uiState.Item

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TaskTextField(
    item: Item,
    onEditFinish: (Item) -> Unit,
    onCardRemove: (Item) -> Unit,
    scroll: (Float) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    maxLines: Int = 20,
    defaultValueOn: Boolean = false,
    defaultValue: String = ""
) {
    var value by remember {
        mutableStateOf(
            TextFieldValue(
                text = item.content,
                selection = TextRange(0, item.content.length)
            )
        )
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val lineHeightPx = with(LocalDensity.current) {
        24.sp.toPx()
    }
    var currentHeight by remember {
        mutableStateOf(0)
    }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onSizeChanged {
                if (currentHeight != 0) {
                    if (it.height > currentHeight) {
                        scroll(lineHeightPx)
                    } else {
                        scroll(-lineHeightPx)
                    }
                }
                currentHeight = it.height
            },
        value = value,
        textStyle = textStyle,
        onValueChange = {
            value = it
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            if (value.text != "") {
                onEditFinish(item.copy(content = value.text))
//                keyboardController?.hide()
//                focusManager.clearFocus()
            } else {
                if (defaultValueOn) onEditFinish(item.copy(content = defaultValue))
                //else onEditFinish(item.copy(content = value.text))
                onCardRemove(item)
            }
        }),
        visualTransformation = VisualTransformation.None,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimaryContainer),
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value.text,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = null,
                label = null,
                leadingIcon = null,
                trailingIcon = null,
                prefix = null,
                suffix = null,
                supportingText = null,
                shape = MaterialTheme.shapes.extraSmall,
                singleLine = false,
                enabled = true,
                isError = false,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
                ),
                contentPadding = PaddingValues(bottom = 8.dp)
            )
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}