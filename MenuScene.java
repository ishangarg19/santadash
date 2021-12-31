
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates the Menu scene, with buttons to play the game, go to instructions, 
 * show the leaderboard, and exit.
 * @author Ishan Garg
 * @version Dec-31-21
 */
public class MenuScene extends JComponent implements ActionListener {

    private JButton play;
    private JButton instruct;
    private JButton scores;
    private JButton exit;
    private Image background;
    private JFrame f;
    private Image icon;

    /**
     * Constructor.
     */
    public MenuScene() {
        f = new JFrame("Santa Dash");
        f.setSize(800, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        icon = Toolkit.getDefaultToolkit().getImage("resources//santa_still.png");
        f.setIconImage(icon);

        background = Toolkit.getDefaultToolkit().getImage("resources//background.jpg");
        play = new JButton("Play");
        instruct = new JButton("Instructions");
        scores = new JButton("Leaderboard");
        exit = new JButton("Exit");
    }

    /**
     * Adds all buttons.
     */
    public void drawMenu() {

        play.addActionListener(this);
        play.setLocation(300, 200);
        play.setSize(200, 50);
        play.setBackground(randColor());
        f.add(play);

        instruct.addActionListener(this);
        instruct.setLocation(300, 250);
        instruct.setSize(200, 50);
        instruct.setBackground(randColor());
        f.add(instruct);

        scores.addActionListener(this);
        scores.setLocation(300, 300);
        scores.setSize(200, 50);
        scores.setBackground(randColor());
        f.add(scores);

        exit.addActionListener(this);
        exit.setLocation(300, 350);
        exit.setSize(200, 50);
        exit.setBackground(randColor());
        f.add(exit);

        f.add(this);
        f.setVisible(true);
    }

    /**
     * Generates a random button color for aesthetics.
     * @return 
     */
    private Color randColor() {
        switch ((int) (Math.random() * 3)) {
            case 1:
                return Color.white;
            case 2:
                return new Color(204, 255, 204);
            default:
                return new Color(255, 204, 204);
        }
    }

    /**
     * Checks for button input and runs appropriate action.
     * @param e Retrieves input command
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            f.dispose();
            PlayScene p = new PlayScene();
            p.addObjects();
            p.gameLoop();
            p.pause(true, true);
        }
        if (e.getSource() == instruct) {
            f.dispose();
            InstructionScene i = new InstructionScene();
            i.addObjects();
        }
        if (e.getSource() == scores) {
            f.dispose();
            ScoresScene s = new ScoresScene();
            s.addObjects();
        }
        if (e.getSource() == exit) {
            f.dispose();
            ExitScene es = new ExitScene();
            es.addObjects();
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

        //Title
        Font titleFont = new Font("Serif", Font.BOLD, 60);
        g.setFont(titleFont);
        g.setColor(Color.white);
        g.drawString("Santa Dash", 260, 130);

        //Name
        Font nameFont = new Font("Serif", Font.PLAIN, 16);
        g.setFont(nameFont);
        g.drawString("Ishan Garg", 10, 455);
    }
}
