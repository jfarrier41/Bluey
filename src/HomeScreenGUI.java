import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The HomeScreenGUI class represents the main menu screen of the Tower Defense Game.
 * It provides a graphical interface containing the game's title and a "Play" button.
 * When the "Play" button is clicked, the application transitions to the map selection screen.
 * This class extends JPanel and is intended to be used as the initial view displayed
 * to the player when launching the game.
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
public class HomeScreenGUI extends JPanel {
    private BufferedImage backgroundImage;

    /**
     * Constructor for HomeScreenGUI, initializing the screen with the given width and height.
     * It sets up the main menu interface, including a "Play" button to navigate to the map
     * selection screen and a "How To Play" button to display game instructions.
     *
     * @param runGame The RunGame instance used to transition to the map selection screen.
     * @param width   The width of the window, used to position components.
     * @param height  The height of the window, used for layout scaling.
     */
    public HomeScreenGUI(RunGame runGame, int width, int height) {
        // Set layout to null to use absolute positioning for components
        setLayout(null);

        // Create the map selection button
        JButton mapSelectionButton = createStyledButton("Play");
        mapSelectionButton.setBounds((width - 220) / 2, 620, 220, 70);  // Center the button
        mapSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SoundEffect("Click.wav", false, 1f);
                // When the button is clicked, transition to the map selection screen
                runGame.showMapSelectionScreen();
            }
        });

        // Create and configure the "How To Play" button
        JButton gameInfoButton = createStyledButton("How To Play");
        gameInfoButton.setBounds(width - 240, 20, 220, 70);  // Center the button
        gameInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File("src/Info/GameDescription.txt");
                    StringBuilder content = new StringBuilder();
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        content.append(scanner.nextLine()).append("\n");
                    }
                    scanner.close();

                    JTextArea textArea = new JTextArea(content.toString());
                    textArea.setWrapStyleWord(true);
                    textArea.setLineWrap(true);
                    textArea.setEditable(false);
                    textArea.setCaretPosition(0);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 300));

                    JOptionPane.showMessageDialog(null, scrollPane, "How to Play", JOptionPane.PLAIN_MESSAGE);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Unable to load game instructions.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        try {
            backgroundImage = ImageIO.read(new File("src/DesignImg/homeScreen.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(mapSelectionButton);
        add(gameInfoButton);
    }

    /**
     * Paints the component by drawing the background image if it exists.
     * This method overrides the default paint behavior to provide a custom background
     * for the home screen panel.
     *
     * @param g the Graphics object used for drawing the component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
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
        button.setForeground(Color.BLACK);
        button.setContentAreaFilled(false);  // Transparent background
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));  // White border
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
