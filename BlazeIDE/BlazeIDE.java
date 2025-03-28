import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BlazeIDE {
    // Class variables to track current file and text area
    private static JTextArea mainEditor;
    private static File currentFile = null;
    private static JFrame frame;
    private static JTextField titleField;

    // Functions to run when buttons pressed
    public static void saveButtonPressed() {
        if (currentFile == null) {
            // No file is open, so use Save As dialog
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save File");
            
            int userSelection = fileChooser.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                currentFile = fileChooser.getSelectedFile();
                titleField.setText(currentFile.getName());
            } else {
                return; // User canceled the save operation
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
            writer.write(mainEditor.getText());
            JOptionPane.showMessageDialog(frame, "File saved successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void openButtonPressed() {
        // Check if there are unsaved changes
        if (currentFile != null) {
            int response = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save changes to the current file?",
                    "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            
            if (response == JOptionPane.YES_OPTION) {
                saveButtonPressed();
            } else if (response == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text Files", "txt", "java", "py", "html", "css", "js");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                mainEditor.setText(content.toString());
                titleField.setText("BLAZE IDE - " + currentFile.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error opening file: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void newButtonPressed() {
        // Check if there are unsaved changes
        if (currentFile != null) {
            int response = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save changes to the current file?",
                    "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            
            if (response == JOptionPane.YES_OPTION) {
                saveButtonPressed();
            } else if (response == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        
        // Clear the editor and reset current file
        mainEditor.setText("");
        currentFile = null;
        titleField.setText("BLAZE IDE - New File");
    }

    public static void main(String[] args) {
        // List of components to add to JFRAME
        mainEditor = new JTextArea();

        JButton saveButton = new JButton("Save");
        JButton openButton = new JButton("Open");
        JButton newButton = new JButton("New");
        titleField = new JTextField("BLAZE IDE - New File");

        // Create the main window object
        frame = new JFrame("Blaze IDE");

        // Set up the window
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create panels
        // Main editor section
        JPanel editorPane = new JPanel();
        editorPane.setLayout(new BorderLayout());
        editorPane.setBackground(Color.GRAY);
        
        // Utilites for saving and managing files
        JPanel utilityWindow = new JPanel();
        utilityWindow.setLayout(new GridLayout(2, 2));
        utilityWindow.setBackground(Color.LIGHT_GRAY);
        
        // Buffer below the utilities
        JPanel buffer = new JPanel();
        buffer.setBackground(Color.LIGHT_GRAY);

        // Create split pane to divide utility and buffer
        JSplitPane splitPaneBuffer = new JSplitPane(JSplitPane.VERTICAL_SPLIT, utilityWindow, buffer);
        splitPaneBuffer.setDividerLocation(200);
        splitPaneBuffer.setResizeWeight(0.5);

        // Create split pane to divide left and right
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorPane, splitPaneBuffer);
        splitPane.setDividerLocation(570);
        splitPane.setResizeWeight(0.85);

        // Add components to panels
        JScrollPane scrollPane = new JScrollPane(mainEditor);
        editorPane.add(scrollPane, BorderLayout.CENTER);

        utilityWindow.add(titleField);
        utilityWindow.add(saveButton);
        utilityWindow.add(openButton);
        utilityWindow.add(newButton);

        // Add action listeners to buttons
        saveButton.addActionListener(e -> {
            // Save the file
            saveButtonPressed();
        });

        openButton.addActionListener(e -> {
            // Open a file
            openButtonPressed();
        });

        newButton.addActionListener(e -> {
            // Create a new file
            newButtonPressed();
        });

        // Add window listener to check for unsaved changes when closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (currentFile != null) {
                    int response = JOptionPane.showConfirmDialog(frame,
                            "Do you want to save changes before exiting?",
                            "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
                    
                    if (response == JOptionPane.YES_OPTION) {
                        saveButtonPressed();
                    } else if (response == JOptionPane.CANCEL_OPTION) {
                        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        return;
                    }
                }
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
        // Add the split pane to the frame
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);
        
        // Make the frame visible after adding all components
        frame.setVisible(true);
    }
}
