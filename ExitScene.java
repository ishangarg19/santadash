import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates the exit scene to show credits for images and game creation.
 * @author Ishan Garg
 * @version Dec-31-21
 */
public class ExitScene extends JComponent implements ActionListener {

    private JFrame f;
    private JButton exit;
    private Image icon;
    private Image background;
    
    /**
     * Constructor.
     */
    public ExitScene() {
        f = new JFrame("Santa Dash");
        f.setSize(800,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        exit = new JButton("Confirm Exit");

        icon = Toolkit.getDefaultToolkit().getImage("resources//santa_still.png");
        f.setIconImage(icon);
        
        background = Toolkit.getDefaultToolkit().getImage("resources//background.jpg");
    }
    
    /**
     * Adds the button.
     */
    public void addObjects() {
        exit.addActionListener(this);
        exit.setLocation(300, 400);
        exit.setSize(200, 50);
        exit.setBackground(Color.white);
        
        f.add(exit);
        f.add(this);
        f.setVisible(true);
    }
    
    /**
     * Checks for button input, then exits.
     * @param e Retrieves an action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            System.exit(0);
        }
    }
    
    /**
     * Paints the scene.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //Background
        g2d.drawImage(background, 0, 0, 800, 500,this);
        
        g.setColor(new Color(212,250,255));
        g.fillRoundRect(160, 190, 480, 50, 10, 10);
        g.fillRoundRect(220, 260, 350, 100, 10, 10);

        //Title
        Font titleFont = new Font("Serif", Font.BOLD, 50);
        g.setFont(titleFont);
        g.setColor(Color.white);
        g.drawString("Thanks for Playing!", 180, 115);

        //Credits
        g.setColor(Color.black);
        Font creditFont = new Font("Serif", Font.PLAIN, 15);
        g.setFont(creditFont);
        g.drawString("Background Scenery Credit: https://wallpaperaccess.com/aesthetic-purple-sky", 170, 210);
        g.drawString("All other graphics by Ishan Garg", 300, 230);
        
        //Project Info
        Font nameFont = new Font("Serif", Font.BOLD, 20);
        g.setFont(nameFont);
        g.setFont(nameFont);
        g.drawString("Programmed by Ishan Garg", 275, 285);
        g.drawString("December 31, 2021", 320, 315);
        g.drawString("MCPT 2021-2022 Holiday Game Jam", 235, 345);
    }
}