package com.example.eisenhowertodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eisenhowertodo.model.TaskCategory
import com.example.eisenhowertodo.ui.components.AddTaskDialog
import com.example.eisenhowertodo.ui.components.CategoryCard
import com.example.eisenhowertodo.ui.theme.EisenhowerToDoTheme
import com.example.eisenhowertodo.ui.theme.White
import com.example.eisenhowertodo.ui.theme.Black
import com.example.eisenhowertodo.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EisenhowerToDoTheme {
                EisenhowerMatrixApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EisenhowerMatrixApp(
    viewModel: TaskViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    val showAddTaskDialog by viewModel.showAddTaskDialog.collectAsState()
    val focusedCategory by viewModel.focusedCategory.collectAsState()
    
    // Remember task lists for each category to prevent unnecessary recompositions
    val urgentImportantTasks = remember(tasks) { 
        viewModel.getTasksByCategory(TaskCategory.URGENT_IMPORTANT) 
    }
    val urgentNotImportantTasks = remember(tasks) { 
        viewModel.getTasksByCategory(TaskCategory.URGENT_NOT_IMPORTANT) 
    }
    val notUrgentImportantTasks = remember(tasks) { 
        viewModel.getTasksByCategory(TaskCategory.NOT_URGENT_IMPORTANT) 
    }
    val notUrgentNotImportantTasks = remember(tasks) { 
        viewModel.getTasksByCategory(TaskCategory.NOT_URGENT_NOT_IMPORTANT) 
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (focusedCategory != null) {
                TopAppBar(
                    title = {
                        Text(
                            text = focusedCategory!!.displayName,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.exitFocusMode() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Geri",
                                tint = White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Black
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddTaskDialog() },
                containerColor = White,
                contentColor = Black,
                modifier = Modifier.size(56.dp),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (focusedCategory != null) {
                // Focus mode - show only the selected category
                val focusedTasks = remember(tasks, focusedCategory) {
                    viewModel.getTasksByCategory(focusedCategory!!)
                }
                
                CategoryCard(
                    category = focusedCategory!!,
                    tasks = focusedTasks,
                    onTaskToggle = viewModel::toggleTaskCompletion,
                    onTaskDelete = viewModel::deleteTask,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            } else {
                // Main content - 2x2 matrix
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Matrix grid with dividing lines
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        // Matrix grid
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            // Top row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(0.dp)
                            ) {
                                // Urgent & Important
                                CategoryCard(
                                    category = TaskCategory.URGENT_IMPORTANT,
                                    tasks = urgentImportantTasks,
                                    onTaskToggle = viewModel::toggleTaskCompletion,
                                    onTaskDelete = viewModel::deleteTask,
                                    onCategoryClick = { viewModel.focusOnCategory(TaskCategory.URGENT_IMPORTANT) },
                                    modifier = Modifier.weight(1f)
                                )

                                // Vertical divider
                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .fillMaxHeight()
                                        .background(MaterialTheme.colorScheme.onBackground)
                                )

                                // Urgent but Not Important
                                CategoryCard(
                                    category = TaskCategory.URGENT_NOT_IMPORTANT,
                                    tasks = urgentNotImportantTasks,
                                    onTaskToggle = viewModel::toggleTaskCompletion,
                                    onTaskDelete = viewModel::deleteTask,
                                    onCategoryClick = { viewModel.focusOnCategory(TaskCategory.URGENT_NOT_IMPORTANT) },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            // Horizontal divider
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(MaterialTheme.colorScheme.onBackground)
                            )

                            // Bottom row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(0.dp)
                            ) {
                                // Not Urgent but Important
                                CategoryCard(
                                    category = TaskCategory.NOT_URGENT_IMPORTANT,
                                    tasks = notUrgentImportantTasks,
                                    onTaskToggle = viewModel::toggleTaskCompletion,
                                    onTaskDelete = viewModel::deleteTask,
                                    onCategoryClick = { viewModel.focusOnCategory(TaskCategory.NOT_URGENT_IMPORTANT) },
                                    modifier = Modifier.weight(1f)
                                )

                                // Vertical divider
                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .fillMaxHeight()
                                        .background(MaterialTheme.colorScheme.onBackground)
                                )

                                // Not Urgent and Not Important
                                CategoryCard(
                                    category = TaskCategory.NOT_URGENT_NOT_IMPORTANT,
                                    tasks = notUrgentNotImportantTasks,
                                    onTaskToggle = viewModel::toggleTaskCompletion,
                                    onTaskDelete = viewModel::deleteTask,
                                    onCategoryClick = { viewModel.focusOnCategory(TaskCategory.NOT_URGENT_NOT_IMPORTANT) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            // Add task dialog
            if (showAddTaskDialog) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.8f))
                        .clickable { viewModel.hideAddTaskDialog() },
                    contentAlignment = Alignment.Center
                ) {
                    AddTaskDialog(
                        onDismiss = { viewModel.hideAddTaskDialog() },
                        onAddTask = { title, description, category ->
                            viewModel.addTask(title, description, category)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EisenhowerMatrixAppPreview() {
    EisenhowerToDoTheme {
        EisenhowerMatrixApp()
    }
}