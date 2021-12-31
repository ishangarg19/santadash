
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * Creates the leaderboard scene, with a top-10 list of names and corresponding
 * scores stored in and retrieved from a file, and a clear option to reset the board.
 * @author Ishan Garg
 * @version Dec-31-21
 */
public class ScoresScene extends JComponent implements ActionListener {

    private JFrame f;
    private Image background;
    private JButton back;
    private JButton clear;
    private int[] scores;
    private String[] names;
    private int numPlayers = 0;
    private String newName;
    private int newScore;
    private boolean clearFile = false;
    private Image icon;

    /**
     * Constructor.
     */
    public ScoresScene() {
        f = new JFrame("Santa Dash");
        f.setSize(800, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        back = new JButton("↰ Menu");
        clear = new JButton("Clear");

        background = Toolkit.getDefaultToolkit().getImage("resources//background.jpg");
        icon = Toolkit.getDefaultToolkit().getImage("resources//santa_still.png");
        f.setIconImage(icon);

        names = new String[10];
        scores = new int[10];
    }

    /**
     * Adds all buttons.
     */
    public void addObjects() {
        back.setVerticalAlignment(SwingConstants.CENTER);
        back.addActionListener(this);
        back.setLocation(40, 30);
        back.setSize(100, 50);
        back.setBackground(Color.white);

        clear.addActionListener(this);
        clear.setLocation(650, 30);
        clear.setSize(100, 50);
        clear.setBackground(Color.white);

        f.add(back);
        f.add(clear);
        f.add(this);
        f.setVisible(true);
    }

    /**
     * Checks for button input.
     * @param e Retrieves input action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            f.dispose();
            MenuScene m = new MenuScene();
            m.drawMenu();
        }
        if (e.getSource() == clear) {
            int confirm = JOptionPane.showConfirmDialog(f, "Are you sure?", 
                    "Alert", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                clearScores();
            }
            repaint();
        }
    }

    /**
     * Reads names and scores from the file.
     */
    public void readFile() {
        String line; //Stores data read from file
        BufferedReader in;

        try { //Trys to read the file
            int count = 0; //Number of players that have been read

            in = new BufferedReader(new FileReader("resources//game_scores.txt")); //Accesses the scores file

            if (!clearFile) { //Does not read new data if the user is having the file cleared, otherwise reads normally
                line = in.readLine();
                numPlayers = Integer.parseInt(line);

                line = in.readLine();
                line = in.readLine();
                while (count < numPlayers) { //Retrieves data constantly for the total size of the array/number of players
                    names[count] = line;
                    line = in.readLine();

                    scores[count] = Integer.parseInt(line);
                    line = in.readLine();
                    line = in.readLine();

                    count++;
                }
            } else {
                in.close(); //Closes file without reading otherwise to prevent errors
            }
        } catch (Exception e) { //Catches exception and informs user that there was an error
            JOptionPane.showMessageDialog(f, "Error Occured. Please try again.", 
                    "Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * Writes new scores to the file, and writes placeholders if the file is cleared.
     */
    public void writeFile() {
        PrintWriter output;

        try { //Tries to write to the file
            output = new PrintWriter(new FileWriter("resources//game_scores.txt"));

            if (!clearFile) { //Only writes all scores to the file when the user is not having the file cleared
                output.println(numPlayers);
                for (int i = 0; i < numPlayers; i++) {
                    output.println();
                    output.println(names[i]);
                    output.println(scores[i]);
                }
            } else { //Otherwise sets the number of students to zero and assigns *no player* tag to each name position, the score of zero
                numPlayers = 0;
                output.println(numPlayers);
                for (int i = 0; i < 10 - numPlayers; i++) {
                    output.println();
                    output.println("*no player*");
                    output.println(0);
                }
            }
            output.close();
        } catch (IOException e) { //Informs the user there has been an error
            JOptionPane.showMessageDialog(f, "Something went wrong while uploading score.", 
                    "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Method to sort the scores from highest to lowest.
     * @param scoreArr Retrieves Scores array to sort
     * @param nameArr Retrieves Names array to assign the same index as scores
     * to after sorting
     */
    protected void sortScores(int[] scoreArr, String[] nameArr) {
        //One by one move boundary of unsorted subarray 
        for (int i = 0; i < 9; i++) {
            //Find the maximum element in unsorted array 
            int maxIndex = i;
            for (int j = i + 1; j < 10; j++) {
                if (scoreArr[j] > scoreArr[maxIndex]) {
                    maxIndex = j;
                }
            }

            // Swap the found maximum element with the first element
            int temp = scoreArr[maxIndex];
            String tempName = nameArr[maxIndex];

            scoreArr[maxIndex] = scoreArr[i];
            nameArr[maxIndex] = nameArr[i];

            scoreArr[i] = temp;
            nameArr[i] = tempName;
        }
    }

    /**
     * Method to set a new score by asking for the player's name then writing
     * new data to file.
     * @param n The new score to add
     */
    public void setScores(int n) {
        newScore = n;
        readFile(); //Retrieves existing valid data so it is not overwritten
        newName = JOptionPane.showInputDialog(f, "Please enter your name "
                + "\n(Press CANCEL to skip saving score): ", "Save your Score",
                JOptionPane.QUESTION_MESSAGE); //Gets the user's name
        if (newName == null) { //Skips score save if user presses CANCEL
            JOptionPane.showMessageDialog(f, "Your score has not been saved.",
                    "Done", JOptionPane.INFORMATION_MESSAGE);
            newScore = 0;
        } else if (newName.equals("") || newName.equals("\n") || newName.equals(" ")) {
            JOptionPane.showMessageDialog(f, "Blank names are not permitted. ",
                    "Alert", JOptionPane.WARNING_MESSAGE); //Gets the user's name
            setScores(newScore);
        } else {
            if (newScore != 0) { //Disregards any score that equals 0
                numPlayers++;
            }
            if (numPlayers <= 10 && newScore != 0) { //Always keeps the number of players <=10
                names[numPlayers - 1] = newName; //Assigns latest elements in arrays to the new players name and score
                scores[numPlayers - 1] = newScore;
                JOptionPane.showMessageDialog(f, "Complete. Your result is now "
                        + "on the leaderboard!", "Done", JOptionPane.INFORMATION_MESSAGE);
            } else if (newScore == 0) { //Informs user that a score of 0 is invalid
                JOptionPane.showMessageDialog(f, "Sorry, your score is not high "
                        + "enough \nto make it to the scoreboard.", "Done", 
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (numPlayers > 10) { //Replaces smallest score with the new score after 10 scores are already present
                sortScores(scores, names);
                numPlayers = 10; //Sets leaderboard to full (max of 10)

                if (scores[9] > newScore) { //Checks if new score qualifies for top 10
                    JOptionPane.showMessageDialog(f, "Sorry, your score is not "
                            + "high enough \nto make it to the scoreboard.", "Done", 
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (scores[9] <= newScore) {
                    names[numPlayers - 1] = newName; //Assigns latest elements in arrays to the new players name and score
                    scores[numPlayers - 1] = newScore;
                    JOptionPane.showMessageDialog(f, "Complete. Your result is "
                            + "now on the leaderboard!", "Done", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            writeFile(); //Writes scores to file including the new score
            repaint();
        }
    }

    /**
     * Method to clear all scores on the scoreboard.
     */
    protected void clearScores() {
        clearFile = true;
        writeFile(); //Writes placeholders to file
        clearFile = false;
        for (int i = 0; i < 10; i++) { //Clears array contents
            names[i] = null;
            scores[i] = 0;
        }
        JOptionPane.showMessageDialog(f, "Scoreboard cleared!", "Done", 
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Paints the leaderboard.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //Background
        g2d.drawImage(background, 0, 0, 800, 500, this);

        //Scoreboard
        g.setColor(Color.white);
        Font titleFont = new Font("Serif", Font.BOLD, 40);
        g.setFont(titleFont);
        g.drawString("Leaderboard", 290, 75);
        g.setColor(new Color(83, 77, 255));
        g.fillRoundRect(85, 110, 630, 325, 10, 10);

        //Score slots
        g.setColor(new Color(212, 250, 255));
        for (int i = 0; i < 300; i += 30) {
            g.fillRoundRect(100, 125 + i, 600, 25, 10, 10);
        }

        g.setColor(new Color(83, 77, 255));
        Font bodyFont = new Font("Serif", Font.BOLD, 18);
        g.setFont(bodyFont);

        //Preps scores for display
        readFile();
        sortScores(scores, names);

        //Paints 1., 2., etc.
        for (int i = 1; i <= 10; i++) {
            g.drawString(String.valueOf(i) + ".", 150, 113 + i * 30);
        }

        //Paints scores and names
        for (int i = 0; i < 10; i++) {
            if (scores[i] > 0) {
                g.drawString(names[i], 350, 143 + i * 30);
                g.drawString("" + scores[i], 550, 143 + i * 30);
            }
        }
    }
}
