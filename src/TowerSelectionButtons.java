import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TowerSelectionButtons extends JPanel {
    private static final int ROWS = 4;
    private static final int COLS = 2;
    private int currentPage = 0;
    private final List<String> towerNames;
    private final JButton[][] buttons = new JButton[ROWS][COLS];
    private final JPanel buttonPanel;
    private final JButton prevButton, nextButton;
    private final JFrame runGame;
    private BufferedImage currentMap;
    private final TowerPanel towerPanel;
    private final JLayeredPane layeredPane;
    // Temporarily used for side panel images
    private static final List<String> DEFAULT_TOWER_IMAGES = List.of(
            "DartMonkey.png", "SuperMonkey.png", "BombTower.png",
            "DartMonkey.png", "SuperMonkey.png", "BombTower.png",
            "DartMonkey.png", "SuperMonkey.png", "BombTower.png",
            "DartMonkey.png", "SuperMonkey.png", "BombTower.png",
            "DartMonkey.png", "SuperMonkey.png", "BombTower.png",
            "DartMonkey.png", "SuperMonkey.png", "BombTower.png",
            "DartMonkey.png", "SuperMonkey.png", "BombTower.png"
    );

    public TowerSelectionButtons(RunGame rungGame, BufferedImage currentMap, TowerPanel towerPanel,JLayeredPane layeredPane) {
        this.towerNames = DEFAULT_TOWER_IMAGES;
        this.runGame = rungGame;
        this.currentMap = currentMap;
        this.towerPanel = towerPanel;
        this.layeredPane = layeredPane;

        setLayout(new BorderLayout());

        // Set transparent background for the panel
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(true);

        // Initialize the up and down buttons for scrolling
        prevButton = new JButton("↑");
        nextButton = new JButton("↓");

        // Set button styles with hover effects
        setArrowButtonStyle(prevButton);
        setArrowButtonStyle(nextButton);

        // Set button sizes
        prevButton.setPreferredSize(new Dimension(30, 40));
        nextButton.setPreferredSize(new Dimension(30, 40));

        // Add scroll buttons to layout
        add(prevButton, BorderLayout.NORTH);
        add(nextButton, BorderLayout.SOUTH);

        // Initialize the button panel with grid layout
        buttonPanel = new JPanel(new GridLayout(ROWS, COLS));
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        add(buttonPanel, BorderLayout.CENTER);

        // Update the grid of buttons
        updateButtonGrid();

        // Set action listeners for the scroll buttons
        prevButton.addActionListener(e -> showPrevPage());
        nextButton.addActionListener(e -> showNextPage());
    }

    private void setArrowButtonStyle(JButton arrowButton) {
        arrowButton.setBackground(new Color(211, 211, 211, 45));
        arrowButton.setOpaque(true);
        arrowButton.setContentAreaFilled(true);
        arrowButton.setBorderPainted(false);
        arrowButton.setFocusPainted(false);

        // Hover effect for arrow buttons
        arrowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                arrowButton.setBackground(new Color(255, 255, 0, 100));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                arrowButton.setBackground(new Color(211, 211, 211, 45));
            }
        });
    }

    private void updateButtonGrid() {
        buttonPanel.removeAll();
        int startIndex = currentPage * 8;
        int endIndex = Math.min(startIndex + 8, towerNames.size());

        int index = startIndex;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (index < endIndex) {
                    buttonPanel.add(createButton(towerNames.get(index)));
                    index++;
                } else {
                    buttonPanel.add(new JLabel());
                }
            }
        }

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private JButton createButton(String imageName) {
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/TowerImages/" + imageName));
        button.setIcon(icon);
        button.setPreferredSize(new Dimension(100, 100));
        button.setBackground(new Color(211, 211, 211, 45));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        // Hover effect for button background
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 255, 0, 100));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(211, 211, 211, 45));
            }
        });

        button.addActionListener(e -> handleButtonClick((RunGame) runGame, currentMap,towerPanel,layeredPane));
        return button;
    }

    private void handleButtonClick(RunGame runGame,BufferedImage currentMap, TowerPanel towerPanel,JLayeredPane layeredPane) {
        Tower tower = new DartMonkey(runGame, 5, 100, 10, 20, "src/TowerImages/DartMonkey.png",currentMap);
        tower.isSelected = true;
        towerPanel.setTower(tower);
        layeredPane.setLayer(towerPanel, JLayeredPane.DRAG_LAYER);
    }

    private void showPrevPage() {
        if (currentPage > 0) {
            currentPage--;
            updateButtonGrid();
        }
    }

    private void showNextPage() {
        if ((currentPage + 1) * 8 < towerNames.size()) {
            currentPage++;
            updateButtonGrid();
        }
    }
}
