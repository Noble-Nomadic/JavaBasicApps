import javax.swing.*;
import java.io.*;
import java.awt.*;

class BlazeIDE {
    
    public static void main(String[] args) {
        // Create the main window object
        JFrame frame = new JFrame("Blaze IDE");

        // Set up the window
        frame.setSize(600, 400);
        frame.setLayout(null);
        frame.setVisible(true);


        JPanel EditorPane = new JPanel();
        EditorPane.setLayout(new flowLayout());
        EditorPane.setBackground(Color.GRAY);
        
        JPanel UtilityWindow = new JPanel();
        UtilityWindow.setLayout(new gridLayout(2, 2));
        UtilityWindow.setBackground(Color.LIGHT_GRAY);

        JSplit splitPanel = new JSplitPane(JPane.HORINZONTAL_SPLIT, EditorPane, UtilityWindow);
        splitPanel.setDividerLocation(200);
        splitPanel.setResizeWeight(0.65);

        


        frame.setVisible(true);
    }
}