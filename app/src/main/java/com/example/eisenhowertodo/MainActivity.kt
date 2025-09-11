package com.example.eisenhowertodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eisenhowertodo.model.Task
import com.example.eisenhowertodo.model.TaskCategory
import com.example.eisenhowertodo.ui.components.AddTaskDialog
import com.example.eisenhowertodo.ui.components.CategoryCard
import com.example.eisenhowertodo.ui.components.DragOverlay
import com.example.eisenhowertodo.ui.theme.EisenhowerToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EisenhowerToDoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EisenhowerToDoApp()
                }
            }
        }
    }
}

@Composable
fun EisenhowerToDoApp() {
    val viewModel: com.example.eisenhowertodo.viewmodel.TaskViewModel = viewModel()
    val tasks by viewModel.tasks.collectAsState()
    
    // Drag and drop state
    var isDragging by remember { mutableStateOf(false) }
    var draggedTask by remember { mutableStateOf<Task?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var dragDelta by remember { mutableStateOf(Offset.Zero) }
    var highlightedCategory by remember { mutableStateOf<TaskCategory?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    
    // Get tasks by category
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
    
    // Drag callbacks
    val onDragStart: (Task, Offset) -> Unit = { task, offset ->
        isDragging = true
        draggedTask = task
        dragDelta = Offset.Zero
        dragOffset = Offset(200f, 300f) // Fixed starting position
        println("Drag started at: $offset")
    }
    
    val onDragOffsetChange: (Offset) -> Unit = { offset ->
        // Use the offset as the drag delta
        dragDelta = offset
        dragOffset = Offset(200f, 300f) + offset // Fixed starting position + drag delta
        println("Drag delta: $offset, final position: $dragOffset")
        // Simple quadrant detection
        val screenWidth = 400f
        val screenHeight = 800f
        val normalizedX = (dragOffset.x / screenWidth).coerceIn(0f, 1f)
        val normalizedY = (dragOffset.y / screenHeight).coerceIn(0f, 1f)
        
        highlightedCategory = when {
            normalizedX < 0.5f && normalizedY < 0.5f -> TaskCategory.URGENT_IMPORTANT
            normalizedX >= 0.5f && normalizedY < 0.5f -> TaskCategory.URGENT_NOT_IMPORTANT
            normalizedX < 0.5f && normalizedY >= 0.5f -> TaskCategory.NOT_URGENT_IMPORTANT
            normalizedX >= 0.5f && normalizedY >= 0.5f -> TaskCategory.NOT_URGENT_NOT_IMPORTANT
            else -> null
        }
    }
    
    val onDragEnd: () -> Unit = {
        val currentTask = draggedTask
        val targetCategory = highlightedCategory
        if (targetCategory != null && currentTask != null) {
            viewModel.moveTask(currentTask.id, targetCategory)
        }
        isDragging = false
        draggedTask = null
        dragOffset = Offset.Zero
        dragDelta = Offset.Zero
        highlightedCategory = null
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Drag overlay
            DragOverlay(
                isDragging = isDragging,
                draggedTask = draggedTask,
                dragOffset = dragOffset
            )
            
            // Main content - 2x2 matrix
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Top row
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    // Urgent & Important
                    CategoryCard(
                        category = TaskCategory.URGENT_IMPORTANT,
                        tasks = urgentImportantTasks,
                        onTaskToggle = viewModel::toggleTaskCompletion,
                        onTaskDelete = viewModel::deleteTask,
                        onDragStart = onDragStart,
                        onDragOffsetChange = onDragOffsetChange,
                        onDragEnd = onDragEnd,
                        isHighlighted = highlightedCategory == TaskCategory.URGENT_IMPORTANT,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Urgent & Not Important
                    CategoryCard(
                        category = TaskCategory.URGENT_NOT_IMPORTANT,
                        tasks = urgentNotImportantTasks,
                        onTaskToggle = viewModel::toggleTaskCompletion,
                        onTaskDelete = viewModel::deleteTask,
                        onDragStart = onDragStart,
                        onDragOffsetChange = onDragOffsetChange,
                        onDragEnd = onDragEnd,
                        isHighlighted = highlightedCategory == TaskCategory.URGENT_NOT_IMPORTANT,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Bottom row
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    // Not Urgent & Important
                    CategoryCard(
                        category = TaskCategory.NOT_URGENT_IMPORTANT,
                        tasks = notUrgentImportantTasks,
                        onTaskToggle = viewModel::toggleTaskCompletion,
                        onTaskDelete = viewModel::deleteTask,
                        onDragStart = onDragStart,
                        onDragOffsetChange = onDragOffsetChange,
                        onDragEnd = onDragEnd,
                        isHighlighted = highlightedCategory == TaskCategory.NOT_URGENT_IMPORTANT,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Not Urgent & Not Important
                    CategoryCard(
                        category = TaskCategory.NOT_URGENT_NOT_IMPORTANT,
                        tasks = notUrgentNotImportantTasks,
                        onTaskToggle = viewModel::toggleTaskCompletion,
                        onTaskDelete = viewModel::deleteTask,
                        onDragStart = onDragStart,
                        onDragOffsetChange = onDragOffsetChange,
                        onDragEnd = onDragEnd,
                        isHighlighted = highlightedCategory == TaskCategory.NOT_URGENT_NOT_IMPORTANT,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
    
    // Add task dialog
    if (showAddDialog) {
        AddTaskDialog(
            onDismiss = { showAddDialog = false },
            onAddTask = { title, description, category ->
                viewModel.addTask(title, description, category)
            }
        )
    }
}
