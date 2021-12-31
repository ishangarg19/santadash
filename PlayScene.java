
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates the play scene, which is the actual game with the player, crystal
 * obstacles, pause/resume options, and a score.
 * @author Ishan Garg
 * @version Dec-31-21
 */
public class PlayScene extends JComponent implements ActionListener {

    private Image background;
    private Image icon;
    
    private JFrame f;
    private Player p;
    private Action jump;
    private Crystal c;
    
    private JButton resume;
    private JButton quit;
    private JButton retry;
    private JButton pause;
    
    private JLabel showScore;
    private JLabel iceRoad;
    
    private PauseScreen ps;
    private EndScreen es;
    private CountDown cd;
    
    private Timer gameTimer;
    private Timer playerTimer;
    
    private int score;
    private int walkCount;
    private int speedUpCount;
    private boolean skipEndScene;
    private boolean isRunning;
    private boolean paused;
    private int costumeSpeed;

    /**
     * Constructor.
     */
    public PlayScene() {
        isRunning = false;
        paused = false;
        skipEndScene = false;
        score = 0;
        walkCount = 0;
        speedUpCount = 0;
        costumeSpeed = 5;

        //Makes game frame
        f = new JFrame("Santa Dash");
        f.setSize(800, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Loads images
        background = Toolkit.getDefaultToolkit().getImage("resources//background.jpg");
        icon = Toolkit.getDefaultToolkit().getImage("resources//santa_still.png");
        f.setIconImage(icon);

        p = new Player();
        ps = new PauseScreen();
        es = new EndScreen();
        cd = new CountDown();
        iceRoad = new JLabel();
        jump = new Jump();
        showScore = new JLabel();
        pause = new JButton("Pause");
        resume = new JButton("Resume");
        quit = new JButton("Quit");
        retry = new JButton("Retry");
        c = new Crystal(1200, 360);

        //Makes SPACE keybind to jump
        p.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "jump");
        p.getActionMap().put("jump", jump);
    }

    /**
     * Adds all buttons and components.
     */
    public void addObjects() {
        p.setBounds(200, 350, 35, 50);

        iceRoad.setBackground(new Color(212, 250, 255));
        iceRoad.setBounds(0, 400, 800, 100);
        iceRoad.setOpaque(true);

        c.setBounds((int)(Math.random()*400+800), c.returnY(), 25, 50);

        Font scoreFont = new Font("Serif", Font.BOLD, 35);
        showScore.setForeground(new Color(83, 77, 255));
        showScore.setFont(scoreFont);
        showScore.setText("Score: " + String.valueOf(score));
        showScore.setBounds(320, 415, 400, 30);

        pause.addActionListener(this);
        pause.setLocation(25, 410);
        pause.setSize(100, 40);
        pause.setBackground(Color.white);
        pause.setVisible(false);

        resume.addActionListener(this);
        resume.setLocation(300, 235);
        resume.setSize(200, 40);
        resume.setBackground(Color.white);
        resume.setVisible(false);

        quit.addActionListener(this);
        quit.setLocation(300, 315);
        quit.setSize(200, 40);
        quit.setBackground(Color.white);
        quit.setVisible(false);
        
        retry.addActionListener(this);
        retry.setLocation(300, 275);
        retry.setSize(200, 40);
        retry.setBackground(Color.white);
        retry.setVisible(false);

        ps.setBackground(new Color(0, 0, 0, 170));
        ps.setBounds(0, 0, 800, 500);
        ps.setOpaque(true);
        ps.setVisible(false);
        
        es.setBackground(new Color(0, 0, 0, 170));
        es.setBounds(0, 0, 800, 500);
        es.setOpaque(true);
        es.setVisible(false);
        
        cd.setBackground(new Color(0, 0, 0, 170));
        cd.setBounds(0, 0, 800, 500);
        cd.setOpaque(true);
        cd.setVisible(false);

        f.add(resume);
        f.add(quit);
        f.add(retry);
        f.add(ps);
        f.add(es);
        f.add(cd);
        f.add(pause);
        f.add(showScore);
        f.add(iceRoad);
        f.add(c);
        f.add(p);
        f.add(this);
        f.setVisible(true);
    }

    /**
     * Method to pause and unpause the program by disabling all timers and 
     * setting the pause screen components to the correct status.
     * @param toPause Whether to pause (true) or unpause (false)
     * @param actionOnly Whether to only pause without the visible menu.
     */
    public void pause(boolean toPause, boolean actionOnly) {
        paused = toPause;
        if (!actionOnly) {
            ps.setVisible(toPause);
            pause.setVisible(!toPause);
            resume.setVisible(toPause);
            quit.setVisible(toPause);
            retry.setVisible(toPause);
        }
    }
    
    /**
     * Method to end the program.
     */
    public void end() {
        es.setVisible(true);
        retry.setVisible(true);
        quit.setVisible(true);
    }
    
    /**
     * Starts a timer that moves the crystal, checks collision/game end, animates
     * Santa walk, and updates the score.
     */
    public void gameLoop() {
        cd.runCountdown();
        gameTimer = new Timer(1, (ActionEvent ae) -> {
            if (!paused) {
                walkCount++;
                speedUpCount++;
                score++; //Updates the score
                showScore.setText("Score: " + String.valueOf(score));
                c.setLocation(c.getX() - c.returnSpeed(), c.getY());
                if (!p.isAlive()) { //Checks if santa dies
                    gameTimer.stop();
                    if(!skipEndScene) {
                        pause.setVisible(false);
                        end();
                    }
                }
                if(speedUpCount >= 1000) { //Speeds up the crystals every ~3000ms
                    c.changeSpeed();
                    speedUpCount = 0;
                }
                if (c.getX() <= -20) { //Resets crystal position
                    c.setLocation((int)(Math.random()*400+800), c.returnY());
                }
                if(walkCount >= costumeSpeed*4) { //Resets santa walking animation
                    walkCount = 0;
                } else if (walkCount >= 0 && walkCount < costumeSpeed) {
                    p.setCostume("0");
                } else if (walkCount >= costumeSpeed && walkCount < costumeSpeed*2) { //Creates santa walking animation
                    p.setCostume("-1");
                } else if (walkCount >= costumeSpeed*2 && walkCount < costumeSpeed*3) {
                    p.setCostume("0");
                } else if (walkCount >= costumeSpeed*3 && walkCount < costumeSpeed*4) {
                    p.setCostume("1");
                } 
                if (c.checkCollision(p)) { //Checks crystal-santa collision
                    p.setAliveStatus(false);
                }
                
                f.repaint();
            }
        });
        gameTimer.setRepeats(true);
        gameTimer.setInitialDelay(0);
        gameTimer.setCoalesce(true);
        gameTimer.start();
    }

    /**
     * Checks for button input and runs appropriate action.
     * @param e  Retrieves user input action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pause) {
            pause(true, false);
        }
        if (e.getSource() == resume) {
            pause(false, false);
        }
        if (e.getSource() == retry) {
            int confirm = JOptionPane.showConfirmDialog(f, "Are you sure?\nYour score will not be saved.", "Alert", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                skipEndScene = true;
                p.setAliveStatus(false);
                skipEndScene = false;
                f.dispose();
                PlayScene scene = new PlayScene();
                scene.addObjects();
                scene.gameLoop();
            }
        }
        if (e.getSource() == quit) {
            int confirm = JOptionPane.showConfirmDialog(f, "Do you want to \nsave your score?", "Score", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                f.dispose();
                ScoresScene s = new ScoresScene();
                s.addObjects();
                s.setScores(score);
            } else if (confirm == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(f, "You will now return to the menu", "Done", JOptionPane.INFORMATION_MESSAGE);
                f.dispose();
                MenuScene m = new MenuScene();
                m.drawMenu();
            }
        }
    }

    /**
     * Paints the background with a translucent layer to darken the backdrop, 
     * which brightens the gameplay.
     */
    @Override
    public void paint(Graphics g) {
        //Draws image
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, 800, 400, this);

        //Makes background darker
        g.setColor(new Color(0, 0, 0, 40));
        g.fillRect(0, 0, 800, 400);
    }
    
    /**
     * Internal class to create player jump action.
     */
    private class Jump extends AbstractAction {

        double angle;

        /**
         * Creates a timer that runs from the start of the jump to the stop.
         * @param e Retrieves user input
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isRunning && !paused) { //Prevents user from jumping while already in air
                angle = 0;
                playerTimer = new Timer(5, (ActionEvent ae) -> {
                    isRunning = true;
                    angle += 18;
                    p.setLocation(p.getX(), p.getY() - (int) (14 * Math.sin(Math.toRadians(angle)))); //Parabolic jump using sine
                    if (angle >= 360) { //Stops jump after one cycle - when the player hits the ground again
                        playerTimer.stop();
                        p.setBounds(200, 350, 35, 50); //Accounts for (int) casting which prevents y-pos from returning to original                      
                        isRunning = false;
                    }
                    f.repaint();
                });
                playerTimer.setRepeats(true); //Repeats jump cycle until the jump is completed
                playerTimer.setInitialDelay(0);
                playerTimer.start();
            }
        }
    }

    /**
     * Internal class to create the pause screen.
     */
    private class PauseScreen extends JComponent {

        /**
         * Paints the translucent pause screen over the gameplay.
         */
        @Override
        public void paint(Graphics g) {
            g.setColor(new Color(0, 0, 0, 170));
            g.fillRect(0, 0, 800, 500);
            Font titleFont = new Font("Serif", Font.BOLD, 60);
            g.setFont(titleFont);
            g.setColor(Color.white);
            g.drawString("Paused", 310, 145);
        }
    }
    
    /**
     * Internal class to create the end game screen.
     */
    private class EndScreen extends JComponent {

        /**
         * Paints the translucent end screen.
         */
        @Override
        public void paint(Graphics g) {
            g.setColor(new Color(0, 0, 0, 170));
            g.fillRect(0, 0, 800, 500);
            Font titleFont = new Font("Serif", Font.BOLD, 60);
            g.setFont(titleFont);
            g.setColor(Color.white);
            g.drawString("Game Over", 270, 145);
        }
    }
    
    /**
     * Internal class to create the countdown screen.
     */
    private class CountDown extends JComponent {

        int num;
        Timer countdownTimer;
        
        /**
         * Constructor.
         */
        public CountDown() {
            num = 4; 
        }
        
        /**
         * Runs the countdown (initially halts all game procedures, then resumes them).
         */
        public void runCountdown() {
            pause(true,true);
            skipEndScene = true;
            p.setAliveStatus(false);
            JOptionPane.showMessageDialog(f, "Ready to begin?", "Alert", JOptionPane.INFORMATION_MESSAGE);
            countdownTimer = new Timer(1000, (ActionEvent e) -> {
                this.setVisible(true);
                f.repaint(); 
                num--;
                if(num <= 0) {
                    this.setVisible(false);
                    pause(false,false);
                    p.setAliveStatus(true);
                    skipEndScene = false;
                    countdownTimer.stop();
                }
            });
            countdownTimer.setRepeats(true); //Repeats jump cycle until the jump is completed
            countdownTimer.setInitialDelay(0);
            countdownTimer.start();
        }
        /**
         * Paints the translucent countdown screen with a changing countdown.
         */
        @Override
        public void paint(Graphics g) {
            g.setColor(new Color(0, 0, 0, 170));
            g.fillRect(0, 0, 800, 500);
            Font titleFont = new Font("Serif", Font.BOLD, 250);
            g.setFont(titleFont);
            g.setColor(Color.white);
            g.drawString("" + num, 350, 300);
        }
    }
}
