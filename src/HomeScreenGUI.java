import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
Filename: RunGame.java
Authors: Jace Claassen and Joe Farrier
Description: The HomeScreen class represents the main menu screen of the Tower Defense Game.
It contains the title label and a button to navigate to the map selection screen.
 */
public class HomeScreenGUI extends JPanel {

    /**
     * Constructor for HomeScreen, initializing the screen with the given width and height.
     * It sets up the title and the "Play" button that allows the user to navigate to the map selection screen.
     *
     * @param runGame The RunGame instance used to transition to the map selection screen
     * @param width   The width of the screen
     * @param height  The height of the screen
     */
    public HomeScreenGUI(RunGame runGame, int width, int height) {
        // Instance of the RunGame class to handle screen transitions
        // Dimensions for the screen layout
        setLayout(null);  // Set layout to null to use absolute positioning for components

        // Create a title label at the top of the screen
        JLabel titleLabel = new JLabel("Bloons Tower Defense", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 50, width, 50);  // Set position and size
        add(titleLabel);

        // Create the map selection button
        JButton mapSelectionButton = createStyledButton("Play");
        mapSelectionButton.setBounds((width - 220) / 2, (height - 90) / 2, 220, 70);  // Center the button
        mapSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When the button is clicked, transition to the map selection screen
                runGame.showMapSelectionScreen();
            }
        });
        add(mapSelectionButton);

        // Set the background color with semi-transparency
        setBackground(new Color(0, 0, 0, 150));
    }

    /**
     * Creates a styled JButton with custom font, color, and hover effects.
     *
     * @param text The text to be displayed on the button
     * @return A JButton with the desired styling
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 28));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);  // Transparent background
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));  // White border
        button.setFocusPainted(false);  // Remove focus paint to avoid focus outline

        // Add mouse listeners for hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Change text color to yellow on hover
                button.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Revert text color to white when hover ends
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }
}
