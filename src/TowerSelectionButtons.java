import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Filename: TowerSelectionButtons.java
 * Author: Jace Claassen
 * Description: TowerSelectionButtons represents a panel displaying tower selection buttons
 * in a grid format, allowing players to choose towers for placement in the game.
 * It provides scroll functionality using up and down arrow buttons to navigate
 * through multiple pages of available towers.
 */
public class TowerSelectionButtons extends JPanel {
    private static final int ROWS = 2;
    private static final int COLS = 2;
    private int currentPage = 0;
    private final List<String> towerNames;
    private final JPanel buttonPanel;
    private final JButton prevButton, nextButton;
    private final JFrame runGame;
    private final BufferedImage currentMap;
    private final TowerPanel towerPanel;
    private final JLayeredPane layeredPane;
    private final GameRunningGUI gameRunningGUI;
    private String displayTowerName;
    private int displayTowerCost;


    // Default tower images used for display purposes
    private static final List<String> DEFAULT_TOWER_IMAGES = List.of(
            "DartMonkey.png", "TackShooter.png","SniperMonkey.png",
            "Ninja.png", "BombTower.png","IceTower.png","GlueGunner.png",
            "Wizard.png","SuperMonkey.png"
    );

    /**
     * Constructs a TowerSelectionButtons panel with the necessary components.
     *
     * @param rungGame   Reference to the main game frame.
     * @param currentMap The current map image.
     * @param towerPanel The panel managing tower placement.
     * @param layeredPane The layered pane for managing UI layers.
     */
    public TowerSelectionButtons(RunGame rungGame, BufferedImage currentMap, TowerPanel towerPanel, JLayeredPane layeredPane, GameRunningGUI gameRunningGUI) {
        this.towerNames = DEFAULT_TOWER_IMAGES;
        this.runGame = rungGame;
        this.currentMap = currentMap;
        this.towerPanel = towerPanel;
        this.layeredPane = layeredPane;
        this.gameRunningGUI = gameRunningGUI;
        this.displayTowerName = "Towers";
        this.displayTowerCost = 0;

        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(true);

        // Initialize scroll buttons
        prevButton = new JButton("↑");
        nextButton = new JButton("↓");

        // Set button styles with hover effects
        setArrowButtonStyle(prevButton);
        setArrowButtonStyle(nextButton);

        prevButton.setPreferredSize(new Dimension(30, 40));
        nextButton.setPreferredSize(new Dimension(30, 40));

        add(prevButton, BorderLayout.NORTH);
        add(nextButton, BorderLayout.SOUTH);

        // Initialize button panel
        buttonPanel = new JPanel(new GridLayout(ROWS, COLS));
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        add(buttonPanel, BorderLayout.CENTER);

        updateButtonGrid();

        prevButton.addActionListener(e -> showPrevPage());
        nextButton.addActionListener(e -> showNextPage());
    }

    /**
     * Sets the styling for the arrow buttons, including background color and hover effects.
     *
     * @param arrowButton The button to style.
     */
    private void setArrowButtonStyle(JButton arrowButton) {
        arrowButton.setBackground(new Color(211, 211, 211, 45));
        arrowButton.setOpaque(true);
        arrowButton.setContentAreaFilled(true);
        arrowButton.setBorderPainted(false);
        arrowButton.setFocusPainted(false);

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

    /**
     * Updates the button grid to display the correct set of towers based on the current page.
     */
    private void updateButtonGrid() {
        buttonPanel.removeAll();
        int startIndex = currentPage * 4;
        int endIndex = Math.min(startIndex + 7, towerNames.size());

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

    /**
     * Creates a button representing a tower, including an image icon and hover effects.
     *
     * @param imageName The name of the image file for the tower.
     * @return A JButton representing the tower.
     */
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
        button.addActionListener(e -> handleButtonClick(imageName));
        return button;
    }

    /**
     * Handles tower selection when a button is clicked.
     *
     * @param imageName The name of the selected tower's image.
     */
    private void handleButtonClick(String imageName) {
        new SoundEffect("Click.wav", false, 1f);
        Tower tower;

        switch (imageName) {
            case "DartMonkey.png":
                tower = new DartMonkey(runGame, currentMap);
                displayTowerName = "Dart Monkey";
                break;
            case "SuperMonkey.png":
                tower = new SuperMonkey(runGame, currentMap);
                displayTowerName = "Super Monkey";
                break;
            case "BombTower.png":
                tower = new BombTower(runGame, currentMap);
                displayTowerName = "Bomb Tower";
                break;
            case "GlueGunner.png":
                tower = new GlueGunner(runGame, currentMap);
                displayTowerName = "Glue Gunner";
                break;
            case "IceTower.png":
                tower = new IceTower(runGame, currentMap);
                displayTowerName = "Frozen Monkey";
                break;
            case "Ninja.png":
                tower = new Ninja(runGame, currentMap);
                displayTowerName = "Ninja Monkey";
                break;
            case "SniperMonkey.png":
                tower = new SniperMonkey(runGame, currentMap);
                displayTowerName = "Sniper Monkey";
                break;
            case "TackShooter.png":
                tower = new TackShooter(runGame, currentMap);
                displayTowerName = "Tack Shooter";
                break;
            case "Wizard.png":
                tower = new Wizard(runGame, currentMap);
                displayTowerName = "Wizard Monkey";
                break;
            default:
                return;
        }
        displayTowerCost = tower.getCost();
        if(tower.getCost() > gameRunningGUI.getCurrentCash()) {
            return;
        }
        tower.isSelected = true;
        towerPanel.setTower(tower);
        layeredPane.setLayer(towerPanel, JLayeredPane.DRAG_LAYER);

        towerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                towerPanel.setCursor(Cursor.getDefaultCursor());
                towerPanel.removeMouseListener(this);
            }
        });

        towerPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Moves to the previous page of tower selection, if possible.
     */
    private void showPrevPage() {
        new SoundEffect("Click.wav", false, 1f);
        if (currentPage > 0) {
            currentPage--;
            updateButtonGrid();
        }
    }

    /**
     * Moves to the next page of tower selection, if possible.
     */
    private void showNextPage() {
        new SoundEffect("Click.wav", false, 1f);
        if ((currentPage + 1) * 4 < towerNames.size()) {
            currentPage++;
            updateButtonGrid();
        }
    }
    public int getDisplayTowerCost(){
        return displayTowerCost;
    }
    public String getDisplayTowerName() {
        return displayTowerName;
    }
}
