import java.awt.*;
import javax.swing.*;


class BlazeIDE {
    
    public static void main(String[] args) {

        // List of components to add to JFRAME
        JTextArea mainEditor = new JTextArea();

        JButton saveButton = new JButton("Save");
        JButton openButton = new JButton("Open");
        JButton newButton = new JButton("New");
        JTextField Title = new JTextField("BLAZE IDE");


        // Create the main window object
        JFrame frame = new JFrame("Blaze IDE");

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
        splitPane.setDividerLocation(500);
        splitPane.setResizeWeight(0.65);

        // Add components to panels
        editorPane.add(mainEditor, BorderLayout.CENTER);

        utilityWindow.add(Title);
        utilityWindow.add(saveButton);
        utilityWindow.add(openButton);
        utilityWindow.add(newButton);
        
        // Add the split pane to the frame
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);
        
        // Make the frame visible after adding all components
        frame.setVisible(true);
    }
}
