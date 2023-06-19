package ziqi.project.pursuingperfection.screen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.common.SmallAvatar
import ziqi.project.pursuingperfection.uiState.Item
import ziqi.project.pursuingperfection.viewModel.TaskDetailViewModel


@Composable
fun TaskScreen(onBackClick: () -> Unit, viewModel: TaskDetailViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var inEditId by remember { mutableStateOf(-1) }
    Scaffold(
        topBar = {
            TaskTopBar(
                title = uiState.value.title,
                timeCreated = uiState.value.timeCreated.toString(),
                profilePhoto = uiState.value.profilePhoto,
                category = uiState.value.category,
                onBackClick = onBackClick,
                onContentClick = { inEditId = it }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding), state = lazyListState) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
            itemsIndexed(items = uiState.value.contents, key = { index, _ -> index }) { _, item ->
                ItemCard(
                    item = item,
                    inEdit = item.id == inEditId,
                    onCardClick = { inEditId = it },
                    onCardRemove = {
                        coroutineScope.launch {
                            viewModel.removeTaskItem(it)
                        }
                    },
                    onCheckChange = { coroutineScope.launch { viewModel.updateCheckedStatus(it) } },
                    onEditFinish = {
                        coroutineScope.launch { viewModel.replaceTask(item, it) }
                        inEditId = -1
                    },
                    scroll = { coroutineScope.launch { lazyListState.animateScrollBy(it) } }
                )
            }
            item {
                val newItemId = uiState.value.contents.last().id + 1
                AddMoreItemCard(
                    inEdit = inEditId == newItemId,
                    newItemId = newItemId,
                    onCardClick = { inEditId = newItemId },
                    onEditFinish = {
                        coroutineScope.launch { viewModel.addTaskItem(it) }
                        inEditId = -1
                    },
                    onCardRemove = { inEditId = -1 },
                    scroll = {
                        coroutineScope.launch {
                            lazyListState.animateScrollBy(it)
                        }
                    })
            }
            item {
                Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp / 5))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(
    title: String,
    timeCreated: String,
    profilePhoto: Int,
    category: String,
    onBackClick: () -> Unit,
    onContentClick: (Int) -> Unit
) {
    LargeTopAppBar(
        title = {
            Text(
                text = "aaaaaaaaaaaaaa",
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 2,
                modifier = Modifier.padding(end = 16.dp)
            )
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
            Text(text = category, style = MaterialTheme.typography.labelLarge)
            SmallAvatar(profilePhoto)
            Text(text = "Apr.8 - Aug.6", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(16.dp))
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
        )
    )
}

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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(80.dp)
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        ),
        onClick = { onCardClick(item.id) }
    ) {
        when (inEdit) {
            false -> when (item.checked) {
                true -> Row(
                    modifier = Modifier
                        .heightIn(80.dp)
                        .padding(start = 4.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = true, onCheckedChange = { onCheckChange(item) })
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = item.content, textDecoration = TextDecoration.LineThrough)
                }

                false -> Row(
                    modifier = Modifier
                        .heightIn(80.dp)
                        .padding(start = 4.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = false, onCheckedChange = {
                        onCheckChange(item)
                        Log.d("testTaskId", item.id.toString())
                    })
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = item.content)
                }
            }

            true -> Row(
                modifier = Modifier
                    .heightIn(80.dp)
                    .padding(start = 4.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = false, onCheckedChange = {})
                TaskTextField(
                    item = item,
                    onEditFinish = onEditFinish,
                    onCardRemove = onCardRemove,
                    scroll = scroll,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }
}

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
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
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
                        Checkbox(checked = false, onCheckedChange = {})
                        TaskTextField(
                            item = Item(id = newItemId),
                            onEditFinish = onEditFinish,
                            onCardRemove = onCardRemove,
                            scroll = scroll,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                false -> {
                    IconButton(onClick = onCardClick) {
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TaskTextField(
    item: Item,
    onEditFinish: (Item) -> Unit,
    onCardRemove: (Item) -> Unit,
    scroll: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var value by remember {
        mutableStateOf(
            TextFieldValue(
                text = item.content,
                selection = TextRange(item.content.length)
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
                if (it.height > currentHeight) {
                    scroll(lineHeightPx)
                } else {
                    scroll(-lineHeightPx)
                }
                currentHeight = it.height
            },
        value = value,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),
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
                onCardRemove(item)
            }
        }),
        visualTransformation = VisualTransformation.None,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimaryContainer),
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
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                ),
                contentPadding = PaddingValues(bottom = 8.dp)
            )
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
