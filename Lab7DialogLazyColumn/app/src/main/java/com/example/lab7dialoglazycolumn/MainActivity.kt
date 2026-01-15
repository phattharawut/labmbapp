package com.example.lab7dialoglazycolumn

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab7dialoglazycolumn.ui.theme.Lab7DialogLazyColumnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab7DialogLazyColumnTheme {
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
fun CheckboxGroup(
    items: List<String>,
    onSelectionChange: (List<String>) -> Unit
) {
    val selectedItems = remember { mutableStateListOf<String>() }

    Column {
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedItems.contains(item),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedItems.add(item)
                        } else {
                            selectedItems.remove(item)
                        }
                        onSelectionChange(selectedItems)
                    }
                )
                Text(text = item)
            }
        }
    }
}

@Composable
fun MyScreen(modifier: Modifier) {

    val studentItemsList = remember { mutableStateListOf<Student>() }
    val contextForToast = LocalContext.current
    val hobbyList = listOf("Reading", "Painting", "Cooking")

    // Add dialog state
    var showAddDialog by rememberSaveable { mutableStateOf(false) }
    var textFieldID by rememberSaveable { mutableStateOf("") }
    var textFieldName by rememberSaveable { mutableStateOf("") }
    var textFieldAge by rememberSaveable { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf(mutableListOf<String>()) }

    // Delete dialog state
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    // State to track the student pending deletion
    var studentToDelete by rememberSaveable { mutableStateOf<Student?>(null) }

    Column(
        modifier = Modifier.padding(all = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(height = 20.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Student Lists:",
                fontSize = 25.sp,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    // Clear the values before opening the dialog
                    textFieldID = ""
                    textFieldName = ""
                    showAddDialog = true
                    textFieldAge = ""
                }
            ) {
                Text(text = "Add Student")
            }
        }

        Spacer(modifier = Modifier.height(height = 10.dp))

        // LazyColumn
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space = 10.dp)
        ) {
            itemsIndexed(items = studentItemsList) { index, item ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(size = 16.dp),
                    onClick = {
                        Toast.makeText(
                            contextForToast, "Click on ${item.name}",
                             Toast.LENGTH_SHORT
                        ).show()


                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "ID: ${item.id}\nName: ${item.name}\nAge: ${item.age}\nHobby: ${item.hobby}",
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
            title = { Text(text = "Enter Student Information") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp)
                ) {
                    OutlinedTextField(
                        value = textFieldID,
                        onValueChange = { textFieldID = it },
                        label = { Text(text = "ID") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = textFieldName,
                        onValueChange = { textFieldName = it },
                        label = { Text(text = "Name") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = textFieldAge,
                        onValueChange = { textFieldAge = it },
                        label = { Text(text = "Age") },
                        singleLine = true
                    )

                    CheckboxGroup(items = hobbyList) { newSelectedItems ->
                        selectedItem.clear()
                        selectedItem.addAll(newSelectedItems)
                    }




                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Validate input before saving
                        if (textFieldName.isNotEmpty() && textFieldID.isNotEmpty() && textFieldAge.isNotEmpty() ) {
                            studentItemsList.add(Student(textFieldID, textFieldName, textFieldAge,selectedItem.joinToString(", ")))
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
                Text(text = "Are you sure you want to delete ${studentToDelete?.name}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        studentItemsList.remove(studentToDelete)
                        Toast.makeText(
                            contextForToast,
                             "${studentToDelete?.name} is deleted",
                             Toast.LENGTH_SHORT
                        ).show()
                        showDeleteDialog = false
                        studentToDelete = null // Reset
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



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab7DialogLazyColumnTheme {
        Greeting("Android")
    }
}