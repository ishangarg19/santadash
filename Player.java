
import java.awt.*;
import javax.swing.*;

/**
 * Creates the player (Santa).
 *
 * @author Ishan Garg
 * @version Dec-31-21
 */
public class Player extends JComponent {

    private Image playerActive;
    private Image playerWalk1;
    private Image playerStill;
    private Image playerWalk2;
    private boolean alive;

    /**
     * Constructor.
     */
    public Player() {
        alive = true;
        playerWalk1 = Toolkit.getDefaultToolkit().getImage("resources//santa_walk1.png");
        playerStill = Toolkit.getDefaultToolkit().getImage("resources//santa_still.png");
        playerWalk2 = Toolkit.getDefaultToolkit().getImage("resources//santa_walk2.png");
        playerActive = playerStill;
    }

    /**
     * Getter method to let other classes check if the player is alive.
     *
     * @return
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Setter method to allow other classes to update alive status.
     *
     * @param a New alive status
     */
    public void setAliveStatus(boolean a) {
        alive = a;
    }

    public void setCostume(String costume) {
        switch (costume) {
            case "-1":
                playerActive = playerWalk1;
                break;
            case "0":
                playerActive = playerStill;
                break;
            case "1":
                playerActive = playerWalk2;
            default:
                break;
        }
        this.repaint();
    }

    /**
     * Paints the player only.
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(playerActive, 0, 0, null);
        repaint();
    }
}
