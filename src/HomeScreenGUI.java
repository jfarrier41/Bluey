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
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 * HomeScreenGUI represents the main menu screen of the Tower Defense Game.
 * It contains the title label and buttons, including one to navigate to the map selection screen.
 * This class handles the display of the home screen and the interaction for transitioning to other game screens.
 */
public class HomeScreenGUI extends JPanel {
    private BufferedImage backgroundImage;  // Background image for the home screen

    /**
     * Constructor for HomeScreenGUI, initializing the screen with the given width and height.
     * It sets up the title and the "Play" and "How To Play" buttons.
     * The "Play" button transitions to the map selection screen, and the "How To Play" button
     * displays game instructions in a message dialog.
     *
     * @param runGame The RunGame instance used to transition to the map selection screen.
     * @param width   The width of the screen.
     * @param height  The height of the screen.
     */
    public HomeScreenGUI(RunGame runGame, int width, int height) {
        // Set layout to null to use absolute positioning for components
        setLayout(null);

        // Create and set up the "Play" button
        JButton mapSelectionButton = createStyledButton("Play");
        mapSelectionButton.setBounds((width - 220) / 2, 620, 220, 70);  // Center the button
        mapSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SoundEffect("Click.wav", false, 1f);
                // When clicked, transition to the map selection screen
                runGame.showMapSelectionScreen();
            }
        });

        // Create and set up the "How To Play" button
        JButton gameInfoButton = createStyledButton("How To Play");
        gameInfoButton.setBounds(width - 240, 20, 220, 70);  // Position at the top right
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

                    // Display game instructions in a message dialog
                    JOptionPane.showMessageDialog(null, scrollPane, "How to Play", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Unable to load game instructions.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Load background image for the home screen
        try {
            backgroundImage = ImageIO.read(new File("src/DesignImg/homeScreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add buttons to the panel
        add(mapSelectionButton);
        add(gameInfoButton);
    }

    /**
     * Paints the home screen by drawing the background image.
     * It is called whenever the component needs to be re-rendered.
     *
     * @param g The graphics context used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);  // Draw background image
        }
    }

    /**
     * Creates a styled JButton with custom font, color, and hover effects.
     * The button has a transparent background, a border, and changes text color when hovered over.
     *
     * @param text The text to be displayed on the button.
     * @return A JButton with the desired styling.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 28));
        button.setForeground(Color.BLACK);
        button.setContentAreaFilled(false);  // Transparent background
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));  // Border for button
        button.setFocusPainted(false);  // Disable focus paint (no focus outline)

        // Add mouse listeners for hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setForeground(Color.YELLOW);  // Change text color to yellow when hovered
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setForeground(Color.WHITE);  // Revert text color to white when not hovered
            }
        });

        return button;  // Return the styled button
    }
}
