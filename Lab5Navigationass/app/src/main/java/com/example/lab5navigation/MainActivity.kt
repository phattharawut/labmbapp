package com.example.lab5navigation

import android.R
import android.R.attr.name
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab5navigation.ui.theme.Lab5NavigationTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5NavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ComposeAllNavigationAss(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}





@Composable
fun ComposeAllNavigationAss(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.First.route,
        modifier = modifier
    ) {
        composable(ScreenRoute.First.route) {
            MyScreen1(navController)
        }
        composable(ScreenRoute.Second.route) {
            MyScreen2(navController)
        }
    }
}




@Composable
fun AccContent(
    username: String, onUsernameChange: (String) -> Unit,
    password: String, onPasswordChange: (String) -> Unit,


    ) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = username,
            onValueChange = onUsernameChange,
            label = { Text(text = "Username") }
        )

        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )


    }
}

@Composable
fun EmailContent(
    email: String , onEmailChange: (String) -> Unit ,

    ) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {


        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = email,
            onValueChange = onEmailChange,
            label = { Text(text = "E-mail") }
        )
    }
}
@Composable
fun GenderSelection(
    selectedItem: String?,
    onSelectedGenderChange: (String) -> Unit
) {
    val genderList = listOf("Male", "Female", "Other")

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
        ,

        text = "Gender: ${selectedItem ?: "Not selected"}"
    )

    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        genderList.forEach { gender ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp) // กำหนดระยะห่างระหว่างตัวเลือก
            ) {
                RadioButton(
                    selected = selectedItem == gender,
                    onClick = {
                        onSelectedGenderChange(gender)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Magenta // เปลี่ยนสีของ RadioButton เมื่อเลือก
                    )
                )
                Text(text = gender, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun DateContent(
    selectedDate: String,  // รับค่าที่เลือกวันที่
    onDateSelected: (String) -> Unit // callback สำหรับวันที่ที่เลือก
) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val currentYear = mCalendar.get(Calendar.YEAR)
    val currentMonth = mCalendar.get(Calendar.MONTH)
    val currentDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    // Creating a DatePickerDialog
    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext,
        { _, year: Int, month: Int, dayOfMonth: Int ->
            val monthStr = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
            onDateSelected("$year-$monthStr-$dayStr") // ส่งวันที่ในรูปแบบ YYYY-MM-DD
        },
        currentYear,
        currentMonth,
        currentDay
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(all = 5.dp),
            text = "Select Date of Birth"
        )

        // DatePicker Button
        FilledIconButton(onClick = { mDatePickerDialog.show() }) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Outlined.DateRange,
                contentDescription = "Date Icon"
            )
        }

        // Display the selected date
        Text(text = "Selected Date of Birth: $selectedDate")
    }
}



@Composable

fun MyScreen1(navController: NavHostController) {

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("Male") }
    var email by rememberSaveable { mutableStateOf("") }
    var birthday by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Page title
        Text(
            text = "Page 1",
            modifier = Modifier
                .background(Color.LightGray, RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Register Form",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        GenderSelection(
            selectedItem = gender,
            onSelectedGenderChange = { gender = it }
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )

        DateContent(
            selectedDate = birthday,
            onDateSelected = { birthday = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val student = Student(username, password, gender, email, birthday)
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("student", student)

                navController.navigate(ScreenRoute.Second.route)
            }
        ) {
            Text("Register")
        }
    }
}


@Composable
fun MyScreen2(navController: NavHostController) {

    val student =
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Student>("student")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Magenta
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Page 2",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.LightGray, RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        student?.let {
            Text("Name: ${it.username}\n")
            Text("Password: ${it.password}\n")
            Text("Gender: ${it.gender}\n")
            Text("E-mail: ${it.email}\n")
            Text("Birthday: ${it.birthday}\n")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navController.navigate(ScreenRoute.First.route) {
                    popUpTo(ScreenRoute.First.route) { inclusive = true }
                }
            }
        ) {
            Text("Go to Page1")
        }
    }
}


