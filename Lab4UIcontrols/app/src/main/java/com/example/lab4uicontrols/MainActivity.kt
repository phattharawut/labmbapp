package com.example.lab4uicontrols

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab4uicontrols.ui.theme.Lab4UIcontrolsTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4UIcontrolsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyScreen(

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Composable
fun IdNameContent(
    id: String, onIDChange: (String) -> Unit,
    name: String, onNameChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = id,
            onValueChange = onIDChange,
            label = { Text(text = "Student ID") }
        )

        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = name,
            onValueChange = onNameChange,
            label = { Text(text = "Name") }
        )
    }
}

@Composable
fun CreditContent(
    creditText: String,
    onCreditTextChange: (String) -> Unit,
    onCreditChange: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = creditText,
            onValueChange = { input ->
                if (input.isEmpty() || input.all { it.isDigit() }) {
                    onCreditTextChange(input)


                    input.toIntOrNull()?.let {
                        onCreditChange(it)
                    }
                }
            },
            label = { Text("Credit") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
}

@Composable
fun TimeContent(
    selectedHour: String,
    selectedMinute: String,
    onTimeSelected: (String, String) -> Unit
) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val hour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val minute = mCalendar.get(Calendar.MINUTE)

    // Creating a TimePickerDialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, hour: Int, minute: Int ->
            val hStr = if (hour < 10) "0$hour" else "$hour"
            val mStr = if (minute < 10) "0$minute" else "$minute"
            onTimeSelected(hStr, mStr)
        },
        hour,
        minute,
        true // 24HourView
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            modifier = Modifier.padding(all = 5.dp),
            text = "Select Time"
        )

        FilledIconButton(onClick = { mTimePickerDialog.show() }) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Outlined.DateRange,
                contentDescription = "Time Icon"
            )
        }

        Text(text = "(HH:MM) = $selectedHour:$selectedMinute")
    }
}


@Composable
fun MyScreen(modifier: Modifier = Modifier) {

    // State variables
    var textInformation by rememberSaveable { mutableStateOf("") }
    var id by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }

    var subject by rememberSaveable{mutableStateOf("Select subject")}

    var creditText by rememberSaveable { mutableStateOf("") }
    var credit by rememberSaveable { mutableStateOf(0) }

    var hour by rememberSaveable { mutableStateOf("00") }
    var minute by rememberSaveable { mutableStateOf("00")}


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(all = 10.dp),
            text = "Enter Student Information",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )

        // UI State and Event
        IdNameContent(
            id = id,
            onIDChange = { id = it },
            name = name,
            onNameChange = { name = it }
        )

        SubjectDropdown(
            selectedSubject = subject,
            onSubjectChange = { subject = it}
        )
        CreditContent(
            creditText = creditText,
            onCreditTextChange = { creditText = it },
            onCreditChange = { credit = it }
        )
        TimeContent(
            selectedHour = hour,
            selectedMinute = minute,
            onTimeSelected = {h,m ->
                hour =h
                minute =m
            }
        )





        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                textInformation = "Student Name: $name\nID: $id\n"+
                        "Subject: $subject \n" + "Credit: $credit \n"+ "Time = $hour : $minute \n"
            }
        ) {
            Text(text = "Show Information")
        }

        // Show information
        if (textInformation.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .width(400.dp)
                    .padding(all = 16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .padding(all = 16.dp)
            ) {
                Text(
                    text = "Student Information:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = textInformation,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDropdown(
    selectedSubject: String,
    onSubjectChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val subjectsList = listOf(
        "Select subject",
        "SC362087 Mobile Device Programming",
        "CP 410 804 Programming for Mobile Application",
        "SC362085 Database Analysis and Design"
    )

    var expanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable { keyboardController?.hide() },
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            modifier = Modifier
                .width(400.dp)
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable,
                    enabled = true
                )
                .clickable { keyboardController?.hide() },
            readOnly = true,
            value = selectedSubject,
            onValueChange = {},
            label = { Text(text = "Subjects") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            subjectsList.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        onSubjectChange(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4UIcontrolsTheme {
        MyScreen()
    }
}