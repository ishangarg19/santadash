import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates the Instructions scene, with information on how to play the game.
 * @author Ishan Garg
 * @version Dec-31-21
 */
public class InstructionScene extends JComponent implements ActionListener {

    private JFrame f;
    private JButton back;
    private Image icon;
    private Image example;
    
    /**
     * Constructor.
     */
    public InstructionScene() {
        f = new JFrame("Santa Dash");
        f.setSize(800,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        back = new JButton("↰ Return to Menu");
        
        icon = Toolkit.getDefaultToolkit().getImage("resources//santa_still.png");
        f.setIconImage(icon);
        
        example = Toolkit.getDefaultToolkit().getImage("resources//example.png");
    }
    
    /**
     * Adds the button.
     */
    public void addObjects() {
        back.addActionListener(this);
        back.setLocation(300, 400);
        back.setSize(200, 50);
        back.setBackground(Color.white);
        
        f.add(back);
        f.add(this);
        f.setVisible(true);
    }
    
    /**
     * Checks for button input, then returns to menu.
     * @param e Retrieves an action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            f.dispose();
            MenuScene m = new MenuScene();
            m.drawMenu();
        }
    }
    
    /**
     * Paints the scene.
     */
    @Override
    public void paint(Graphics g) {
        //Background
        g.setColor(new Color(212,250,255));
        g.fillRect(0, 0, 800, 500);

        //Title
        Font titleFont = new Font("Serif", Font.BOLD, 30);
        g.setFont(titleFont);
        g.setColor(Color.black);
        g.drawString("Instructions", 320, 75);

        //Body
        Font bodyFont = new Font("Serif", Font.PLAIN, 20);
        g.setFont(bodyFont);
        g.drawString("Your goal is to keep Santa alive for as long as possible (it is endless!)", 40, 140);
        g.drawString("Do so by jumping over the crystal obstacles.", 40, 165);
        g.drawString("Press the SPACE key to jump, and hold SPACE to constantly jump.", 40, 190);
        g.drawString("The greater your score, the longer you ran for!", 40, 215);
        g.drawString("THE CRYSTALS WILL SPEED UP OVER TIME, so be ready!!! Good Luck!", 40, 240);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(example, 275, 270, 250, 125, this);
    }
}