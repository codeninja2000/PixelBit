# PixelBit

PixelBit is a lightweight, user-friendly image editing application built with Java and JavaFX. It provides essential image manipulation features with an intuitive interface.

## Features

### Basic Operations
- Load and save images in common formats (JPG, PNG, GIF)
- Undo/Redo functionality for all editing operations
- Reset image to its original state

### Image Filters
- Grayscale conversion
- Sepia tone effect
- Color inversion
- Brightness adjustment
- Contrast control

### Image Manipulation
- Crop functionality
- Image resizing capabilities

## Technical Details

### System Requirements
- Java 24 or higher
- JavaFX runtime

### Project Structure
- **Model**: Contains the core image processing logic (`EditableImage`, `PBModel`)
- **View**: Handles the user interface (`PBImageView`)
- **Controller**: Manages user interactions and updates (`PBController`)
- **Commands**: Implements Command pattern for operations

### Design Patterns
- Command Pattern: For implementing undo/redo functionality
- MVC Architecture: Separation of concerns between Model, View, and Controller
- Factory Pattern: For filter creation and management



## Getting Started

### Installation
1. Clone the repository:
```bash
git clone https://github.com/codeninja2000/PixelBit.git
```
2. Build the project using your preferred IDE or command line tools (e.g., Maven, Gradle).
3. Run the application using the main class PBApplication.

It is recommended to use Intellij IDEA for the three steps above, as it provides excellent support
for JavaFX projects and should work out of the box.

### Basic Usage
1. **Open an Image**:
    - Use File → Open or the Open button in the toolbar
    - Supported formats: JPG, PNG, GIF

2. **Apply Filters**:
    - Click the filter buttons in the toolbar (Grayscale, Sepia, Invert)
    - Use sliders to adjust brightness and contrast

3. **Crop Image**:
    - Click the Crop button
    - Enter crop dimensions in the dialog
    - Click Apply to crop

4. **Save Changes**:
    - Use File → Save to save your edited image

5. **Undo/Redo**:
    - Use Edit → Undo/Redo or toolbar buttons
    - Reset to original using the Reset button

## UML Diagrams
![PixelBit UML Diagram](/src/main/resources/uml/PixelBit_ClassStructure.png)

### Command module:
![PixelBit Command Module](/src/main/resources/uml/command.png)

### Exception module:
![PixelBit Exception Module](/src/main/resources/uml/exception.png)

### Model module:
![PixelBit Model Module](/src/main/resources/uml/model.png)

### Util module:
![PixelBit Util Module](/src/main/resources/uml/util.png)

### PBController file:
![PixelBit PBController](/src/main/resources/uml/PBController.png)