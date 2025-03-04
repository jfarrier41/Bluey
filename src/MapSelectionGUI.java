import javax.swing.*;
import java.awt.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/*
Filename: RunGame.java
Authors: Jace Claassen and Joe Farrier
Description: The MapSelectionGUI class represents the screen where the player can select a map to play.
It displays a map preview, description, and navigation buttons to cycle through available maps.
 */
public class MapSelectionGUI extends JPanel {
    private int selectedMapIndex = 0;  // Index to track the currently selected map

    // Array of map names and their respective descriptions
    private final String[] mapNames = {"Maze", "MonkeyLane", "ParkPath", "SpringTrack"};
    private final String[] mapDescriptions = {
            "A complex maze filled with twists and turns.",
            "A winding lane with plenty of obstacles.",
            "A serene park path with natural challenges.",
            "A spring-themed track with lots of curves."
    };

    private BufferedImage mapImage;  // BufferedImage to hold the current map image
    private final RunGame runGame;  // Instance of RunGame for screen transitions
    private final int screenWidth, screenHeight;  // Dimensions of the screen
    private final HomeScreenGUI homeScreenGUI;  // Reference to the HomeScreen for navigation
    private final JLabel mapDescriptionLabel;  // Label to display the map description
    private final JPanel descriptionBackground;  // Panel background for description area
    private int fontSize;  // Font size for map description based on screen size

    /**
     * Constructor for MapSelectionGUI, initializing the screen with the given width, height, and home screen reference.
     * It sets up map navigation buttons, displays the map preview and description, and adds event listeners.
     *
     * @param runGame     The RunGame instance used for screen transitions
     * @param width       The width of the screen
     * @param height      The height of the screen
     * @param homeScreenGUI  The HomeScreen instance for returning to the home screen
     */
    public MapSelectionGUI(RunGame runGame, int width, int height, HomeScreenGUI homeScreenGUI) {
        this.runGame = runGame;
        this.screenWidth = width;
        this.screenHeight = height;
        this.homeScreenGUI = homeScreenGUI;
        setLayout(null);  // Set layout to null for absolute positioning of components

        // Load the first map image
        loadMapImage();

        // Calculate dynamic font size based on screen dimensions
        fontSize = Math.min(screenWidth / 30, screenHeight / 30);

        // Create transparent overlay for clarity of buttons
        JPanel overlay = new JPanel();
        overlay.setBounds(0, 0, screenWidth, screenHeight);
        overlay.setOpaque(false);
        overlay.setLayout(null);
        add(overlay);

        // Left Arrow Button
        JButton leftButton = createStyledButton("←");
        leftButton.setBounds(screenWidth / 4, (screenHeight - screenWidth / 16) / 2, screenWidth / 16, screenWidth / 16);
        leftButton.addActionListener(e -> changeMap(-1));  // Cycle maps left
        overlay.add(leftButton);

        // Right Arrow Button
        JButton rightButton = createStyledButton("→");
        rightButton.setBounds(screenWidth - screenWidth / 4 - screenWidth / 16, (screenHeight - screenWidth / 16) / 2, screenWidth / 16, screenWidth / 16);
        rightButton.addActionListener(e -> changeMap(1));  // Cycle maps right
        overlay.add(rightButton);

        // Confirm Selection Button Background (centered between arrows)
        JPanel confirmPanel = new JPanel();
        int confirmButtonWidth = 150;
        int confirmButtonHeight = 70;
        confirmPanel.setBounds((screenWidth - confirmButtonWidth) / 2, (screenHeight - confirmButtonHeight) / 2, confirmButtonWidth, confirmButtonHeight);
        confirmPanel.setBackground(new Color(0, 0, 0, 150));  // Semi-transparent black background
        confirmPanel.setLayout(null);
        overlay.add(confirmPanel);

        // Confirm Selection Button
        JButton confirmButton = createStyledButton("Select");
        confirmButton.setBounds(10, 10, confirmButtonWidth - 20, confirmButtonHeight - 20);
        confirmButton.addActionListener(e -> confirmSelection());  // Confirm map selection
        confirmPanel.add(confirmButton);

        // Description panel with transparent background
        descriptionBackground = new JPanel();
        descriptionBackground.setBounds((screenWidth - screenWidth / 2) / 2, screenHeight - (screenHeight / 3), screenWidth / 2, screenHeight / 12);
        descriptionBackground.setBackground(new Color(0, 0, 0, 150));
        descriptionBackground.setLayout(new BorderLayout());
        overlay.add(descriptionBackground);

        // Map Description Label (initial description for first map)
        mapDescriptionLabel = new JLabel(mapDescriptions[selectedMapIndex]);
        mapDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));  // Dynamic font size
        mapDescriptionLabel.setForeground(Color.WHITE);
        mapDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionBackground.add(mapDescriptionLabel, BorderLayout.CENTER);

        // Return Home Button
        JButton returnHomeButton = createStyledButton("Return Home");
        returnHomeButton.setBounds(10, 10, screenWidth / 5, screenHeight / 12);
        returnHomeButton.addActionListener(e -> returnHome());  // Return to HomeScreen
        overlay.add(returnHomeButton);
    }

    /**
     * Creates a styled JButton with custom font, color, and hover effects.
     *
     * @param text The text to display on the button
     * @return A JButton with the desired style
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, fontSize));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 150));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setFocusPainted(false);

        // Hover effect to change text color
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setForeground(Color.YELLOW);  // Change text color to yellow on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setForeground(Color.WHITE);  // Reset text color
            }
        });

        return button;
    }

    /**
     * Changes the currently selected map based on the direction (-1 for left, 1 for right).
     * It loads the new map image and updates the description label.
     *
     * @param direction The direction to change the map (-1 for left, 1 for right)
     */
    private void changeMap(int direction) {
        selectedMapIndex = (selectedMapIndex + direction + mapNames.length) % mapNames.length;  // Cycle through maps
        loadMapImage();  // Load the image of the new map
        mapDescriptionLabel.setText(mapDescriptions[selectedMapIndex]);  // Update map description text
        fontSize = Math.min(screenWidth / 30, screenHeight / 30);  // Recalculate font size
        mapDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        descriptionBackground.setBounds((screenWidth - screenWidth / 2) / 2, screenHeight - (screenHeight / 3), screenWidth / 2, screenHeight / 12);  // Adjust description size
        repaint();
    }

    /**
     * Loads the image for the currently selected map.
     */
    private void loadMapImage() {
        // Path to the folder containing map images
        String mapFolderPath = "src/MapImg/";
        String imagePath = mapFolderPath + mapNames[selectedMapIndex] + ".png";
        try {
            mapImage = ImageIO.read(new File(imagePath));  // Read the image from file
        } catch (IOException e) {
            System.err.println("Failed to load image: " + imagePath);
            mapImage = null;  // Set mapImage to null if loading fails
        }
    }

    /**
     * Confirms the map selection and transitions to the game.
     * Displays a dialog with the selected map name.
     */
    private void confirmSelection() {
        System.out.println("Map selected: " + mapNames[selectedMapIndex]);
        JOptionPane.showMessageDialog(this, "You selected: " + mapNames[selectedMapIndex]);  // Show confirmation dialog
    }

    /**
     * Returns to the HomeScreen.
     */
    private void returnHome() {
        runGame.setContentPane(homeScreenGUI);  // Switch content pane to home screen
        runGame.revalidate();  // Revalidate the layout
        runGame.repaint();  // Repaint the content
    }

    /**
     * Paints the map image onto the screen.
     *
     * @param g The Graphics object used to paint the component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, getWidth(), getHeight() - (getHeight() / 10), this);  // Draw the map image stretched to fill the component
        }
    }
}
