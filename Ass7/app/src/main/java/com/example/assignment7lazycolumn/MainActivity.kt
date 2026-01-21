package com.example.assignment7lazycolumn

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment7lazycolumn.ui.theme.Assignment7LazyColumnTheme

// 1. Define the Data Class


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment7LazyColumnTheme {
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
fun MyScreen(modifier: Modifier = Modifier) {

    val studentItemsList = remember { mutableStateListOf<Member>() }
    val contextForToast = LocalContext.current

    // Add dialog state
    var showAddDialog by rememberSaveable { mutableStateOf(false) }
    var textFieldEmail by rememberSaveable { mutableStateOf("") }
    var textFieldName by rememberSaveable { mutableStateOf("") }
    var textFieldSalary by rememberSaveable { mutableStateOf("") } // Fixed typo Saraly -> Salary

    // 2. Fix: selectedGender should be a single String, not a List
    var selectedGender by rememberSaveable { mutableStateOf("Male") }

    // Delete dialog state
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    // State to track the student pending deletion
    var studentToDelete by rememberSaveable { mutableStateOf<Member?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Member Lists:",
                fontSize = 25.sp,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    // Clear values and reset default gender
                    textFieldEmail = ""
                    textFieldName = ""
                    textFieldSalary = ""
                    selectedGender = "Male"
                    showAddDialog = true
                }
            ) {
                Text(text = "Add Member")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // LazyColumn
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(items = studentItemsList) { index, item ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        Toast.makeText(
                            contextForToast, "Click on ${item.Name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Name: ${item.Name}\nGender: ${item.Gender}\nE-mail: ${item.Email}\nSalary: ${item.Salary}",
                            modifier = Modifier.weight(1f)
                        )

                        TextButton(
                            onClick = {
                                studentToDelete = item
                                showDeleteDialog = true
                            }
                        ) {
                            Text(text = "Delete", color = Color.Red)
                        }
                    }
                }
            }
        }
    }

    // Add Student Dialog
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(text = "Enter Information") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = textFieldName,
                        onValueChange = { textFieldName = it },
                        label = { Text(text = "Name") },
                        singleLine = true
                    )

                    // --- Gender Section ---
                    Text(text = "Gender :", color = Color.Gray, modifier = Modifier.padding(top = 8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // 3. Fix: Defined the list here instead of null
                        val genderOptions = listOf("Male", "Female", "Other")

                        genderOptions.forEach { text ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .selectable(
                                        selected = (text == selectedGender),
                                        onClick = { selectedGender = text }
                                    )
                                    .padding(end = 5.dp)
                            ) {
                                RadioButton(
                                    selected = (text == selectedGender),
                                    onClick = { selectedGender = text },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color.Magenta,
                                        unselectedColor = Color.Gray
                                    )
                                )
                                Text(
                                    text = text,
                                    modifier = Modifier.padding(start = 0.5.dp),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = textFieldEmail,
                        onValueChange = { textFieldEmail = it },
                        label = { Text(text = "E-mail") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = textFieldSalary,
                        onValueChange = { textFieldSalary = it },
                        label = { Text(text = "Salary") }, // Fixed Label
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (textFieldName.isNotEmpty() && textFieldEmail.isNotEmpty() && textFieldSalary.isNotEmpty()) {
                            // Add new member
                            studentItemsList.add(
                                Member(
                                    Name = textFieldName,
                                    Gender = selectedGender,
                                    Email = textFieldEmail,
                                    Salary = textFieldSalary
                                )
                            )
                            showAddDialog = false
                        } else {
                            Toast.makeText(
                                contextForToast,
                                "Please fill all fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog && studentToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = "Warning") },
            text = {
                // 4. Fix: Uses item.name safely
                Text(text = "Are you sure you want to delete ${studentToDelete?.Name}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        studentItemsList.remove(studentToDelete)
                        Toast.makeText(
                            contextForToast,
                            "${studentToDelete?.Name} is deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                        showDeleteDialog = false
                        studentToDelete = null
                    }
                ) {
                    Text(text = "Yes", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = "No")
                }
            }
        )
    }
}