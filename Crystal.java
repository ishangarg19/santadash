
import java.awt.*;
import javax.swing.*;

/**
 * Creates the Crystal obstacle object.
 *
 * @author Ishan Garg
 * @version Dec-31-21
 */
public class Crystal extends JComponent {

    private int x;
    private int y;
    private int speed;
    private Image crystal;

    /**
     * Constructor.
     *
     * @param x starting x position
     * @param y starting y position
     */
    public Crystal(int x, int y) {
        speed = 8;
        this.x = x;
        this.y = y;

        crystal = Toolkit.getDefaultToolkit().getImage("resources//crystal.png");

    }

    /**
     * Checks for collision with Component c.
     * @param c The component to check collision with
     * @return Whether there was collision
     */
    public boolean checkCollision(JComponent c) {
        return c.getX() + 5 < this.getX() + 20
                && c.getX() + 20 > this.getX() + 5
                && c.getY() < this.getY() + 45
                && c.getY() + 45 > this.getY();
    }

    /**
     * Increases the speed of the crystals over time using a timer.
     */
    public void changeSpeed() {
        if(speed<24) {
            speed += 1;
        }
    }
    
    /**
     * Getter method for the x-value
     * @return Returns x
     */
    public int returnX() {
        return x;
    }
    
    /**
     * Getter method for the y-value
     * @return Returns y
     */
    public int returnY() {
        return y;
    }
    
    /**
     * Getter method for the speed
     * @return Returns y
     */
    public int returnSpeed() {
        return speed;
    }

    /**
     * Paints the crystal.
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(crystal, 0, 0, null);
    }
}
