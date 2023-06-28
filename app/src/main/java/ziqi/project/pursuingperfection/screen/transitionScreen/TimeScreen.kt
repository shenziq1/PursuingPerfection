package ziqi.project.pursuingperfection.screen.transitionScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.utils.longConvert
import ziqi.project.pursuingperfection.viewModel.editViewModel.EditTimeViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeScreen(
    onBackClick: () -> Unit,
    onNextClick: (Int) -> Unit,
    viewModel: EditTimeViewModel = hiltViewModel()
) {
    var clickedDatePicker by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val dateRangePickerState = rememberDateRangePickerState()
    val fullDateFormatter: DatePickerFormatter =
        remember { DatePickerFormatter(selectedDateSkeleton = "YYYY MMM dd") }
    val shortDateFormatter: DatePickerFormatter =
        remember { DatePickerFormatter(selectedDateSkeleton = "MMM dd") }
    val timeStart = dateRangePickerState.selectedStartDateMillis?.let {
        LocalDateTime.ofEpochSecond(
            it/1000, 0, ZoneOffset.UTC
        )
    }
    val timeEnd = dateRangePickerState.selectedEndDateMillis?.let {
        LocalDateTime.ofEpochSecond(
            it/1000, 0, ZoneOffset.UTC
        )
    }

    LaunchedEffect(Unit){
        viewModel.initialize()
    }


    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Set up a time period for your task:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Button(onClick = { clickedDatePicker = true }) {
                Text(text = "Date Picker")
            }
            DateRangePickerDefaults.DateRangePickerHeadline(
                dateRangePickerState,
                fullDateFormatter,
                modifier = Modifier
            )
            if (clickedDatePicker) {
                DatePickerDialog(
                    onDismissRequest = {
                        clickedDatePicker = false
                        Log.d("timeScreen", timeStart?.longConvert().toString())
                        Log.d("timeScreen", timeEnd?.longConvert().toString())
                    },
                    confirmButton = { /*TODO*/ }) {
                    DateRangePicker(state = dateRangePickerState, title = {}, headline = {
                        DateRangePickerDefaults.DateRangePickerHeadline(
                            dateRangePickerState,
                            shortDateFormatter,
                            modifier = Modifier.padding(start = 32.dp)
                        )
                    })
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onBackClick, shape = MaterialTheme.shapes.small) {
                Text(text = "Back")
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(onClick = {
                onNextClick(viewModel.id)
                viewModel.updateNewTaskTime(
                    timeStart ?: LocalDateTime.now(),
                    timeEnd ?: LocalDateTime.now().plusHours(24)
                )
                coroutineScope.launch {
                    viewModel.updateTaskToRepository()
                }
            }, shape = MaterialTheme.shapes.small) {
                Text(text = "Next")
            }
        }
    }
}