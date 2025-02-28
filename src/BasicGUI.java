import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BasicGUI extends JFrame {
    protected int fireSpeed;
    protected int diameter = 100;
    protected int projectileSpeed;
    protected int projectileDamage;

    protected boolean placeable = true;
    protected boolean isSelected = false;

    protected int xPosition;
    protected int yPosition;

    private ColorPanel colorPanel;

    public BasicGUI() {
        setTitle("Basic GUI");
        setSize(500, 500); // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        colorPanel = new ColorPanel();
        add(colorPanel);

        startPlacement();
    }

    // Custom panel to draw the green/brown split
    private class ColorPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();

            // Left half - Green
            g.setColor(new Color(0, 255, 0));
            g.fillRect(0, 0, width / 2, height);

            // Right half - Brown
            g.setColor(new Color(139, 69, 19)); // Brown color
            g.fillRect(width / 2, 0, width / 2, height);

            if(placeable = true) {
                Color color = new Color(0, 0, 0, 0);
                g.setColor(color);
                g.fillOval(xPosition - diameter / 2, yPosition - diameter / 2, diameter, diameter);

            } else {
                Color color = new Color(0, 200, 0, 128);
                g.setColor(color);
                g.fillOval(xPosition - diameter / 2, yPosition - diameter / 2, diameter, diameter);

            }


            // Draw crosshairs at the mouse position (not the center of the circle)

        }
    }


    public void startPlacement() {
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                xPosition = e.getX() - 4;
                yPosition = e.getY() - 23;

                isPlaceable(xPosition, yPosition);

                colorPanel.repaint();
            }
        });
    }

    // Helper method to determine valid pixel color
    // ChatGPT
    public boolean isGreen(Color color) {
        return color.getGreen() > 10 && color.getRed() < 10 && color.getBlue() < 70;
    }

    //Method to determine if monkey can be set down
    public boolean isPlaceable(int x, int y) {
        try {
            Robot robot = new Robot(); // Create a Robot instance to get screen pixels

            // Grab a 5x5 region of pixels around (x, y)
            for (int i = -2; i <= 2; i++) {  // Loop from x-2 to x+2 (5 pixels in total)
                for (int j = -2; j <= 2; j++) {  // Loop from y-2 to y+2 (5 pixels in total)
                    Color pixelColor = robot.getPixelColor(x + i, y + j);  // Get color of pixel
                    System.out.print(pixelColor.getRed() + " " + pixelColor.getGreen() + " " + pixelColor.getBlue());
                    if(!isGreen(pixelColor)){
                        placeable = false;
                        System.out.println(placeable);
                        return false;
                    }

                    // Store the color in the array
                }
            }
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        placeable = true;
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BasicGUI().setVisible(true));
    }
}