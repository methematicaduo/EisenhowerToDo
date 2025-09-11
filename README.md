# Eisenhower ToDo App

A modern Android app implementing the Eisenhower Matrix for task management with intuitive drag-and-drop functionality.

## 🎯 Features

### Core Functionality
- **Eisenhower Matrix UI**: 2x2 grid layout with four distinct quadrants
- **Task Management**: Add, edit, complete, and delete tasks
- **Drag & Drop**: Move tasks between quadrants with smooth animations
- **Visual Feedback**: Quadrants highlight when hovering during drag operations

### Quadrants
1. **Top-left**: "Urgent & Important" (Red theme) - Do these first!
2. **Top-right**: "Urgent & Not Important" (Blue theme) - Delegate if possible
3. **Bottom-left**: "Not Urgent & Important" (Yellow theme) - Schedule for later
4. **Bottom-right**: "Not Urgent & Not Important" (Green theme) - Consider eliminating

### Drag & Drop System
- **Long press** any task to start dragging
- **Smooth following**: Task follows your finger naturally
- **Visual feedback**: Target quadrants highlight during drag
- **Precise positioning**: Task appears centered on your touch point
- **Intuitive drop**: Release to move task to highlighted quadrant

## 🏗️ Architecture

### MVVM Pattern
- **ViewModel**: `TaskViewModel` manages business logic and state
- **Model**: `Task` data class with `TaskCategory` enum
- **View**: Jetpack Compose UI components

### Key Components
- `MainActivity`: Main UI and drag state management
- `CategoryCard`: Individual quadrant display
- `DraggableTaskItem`: Task item with drag detection
- `DragOverlay`: Global overlay for dragged items
- `AddTaskDialog`: Task creation interface
- `ColorUtils`: Shared color management

### File Structure
```
app/src/main/java/com/example/eisenhowertodo/
├── MainActivity.kt                 # Main UI and drag coordination
├── model/
│   └── Task.kt                    # Data models
├── viewmodel/
│   └── TaskViewModel.kt           # Business logic
├── ui/
│   ├── components/
│   │   ├── CategoryCard.kt        # Quadrant display
│   │   ├── DraggableTaskItem.kt   # Task item with drag
│   │   ├── DragOverlay.kt         # Drag visual feedback
│   │   └── AddTaskDialog.kt       # Task creation
│   ├── theme/
│   │   ├── Color.kt               # Color definitions
│   │   ├── Theme.kt               # Material 3 theme
│   │   └── Type.kt                # Typography
│   └── utils/
│       └── ColorUtils.kt          # Shared color functions
```

## 🎨 Design

### Material Design 3
- Modern Material Design 3 components
- Consistent color theming across quadrants
- Smooth animations and transitions
- Accessible touch targets

### Color Scheme
- **Urgent & Important**: Red (#E53E3E)
- **Urgent & Not Important**: Blue (#3182CE)
- **Not Urgent & Important**: Yellow (#D69E2E)
- **Not Urgent & Not Important**: Green (#38A169)

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 24+
- Kotlin 1.9+

### Installation
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on device or emulator

### Building
```bash
./gradlew assembleDebug
```

## 🎮 Usage

### Adding Tasks
1. Tap the floating action button (+)
2. Enter task title and description
3. Select appropriate category
4. Tap "Add Task"

### Managing Tasks
- **Complete**: Tap the checkbox
- **Delete**: Tap the trash icon
- **Move**: Long press and drag to another quadrant

### Drag & Drop
1. **Long press** any task to start dragging
2. **Drag** to desired quadrant (it will highlight)
3. **Release** to move the task

## 🔧 Technical Details

### Drag Implementation
- Uses `detectDragGestures` for touch handling
- Global state management in `MainActivity`
- Separate drag detector to avoid button conflicts
- Precise offset calculation for natural positioning

### State Management
- `StateFlow` for reactive UI updates
- Local state for drag operations
- Proper cleanup on drag end

### Performance
- Efficient LazyColumn for task lists
- Minimal recomposition during drag
- Smooth 60fps animations

## 🐛 Troubleshooting

### Common Issues
- **Drag not working**: Ensure long press, not tap
- **Task appears in wrong position**: Check screen dimensions in drag calculation
- **Buttons not responding**: Drag detector is separate from button interactions

### Debug Features
- Console logging for drag coordinates
- Visual feedback for drag states
- Highlighted drop targets

## 📱 Screenshots

The app features a clean 2x2 matrix layout with:
- Color-coded quadrants
- Smooth drag animations
- Intuitive task management
- Modern Material Design

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is open source and available under the MIT License.

## 🙏 Acknowledgments

- Eisenhower Matrix methodology
- Material Design 3 guidelines
- Jetpack Compose framework
- Android development community