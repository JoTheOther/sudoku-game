import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class LaunchPage extends JFrame implements ActionListener {

    JMenu helpMenu;
    JMenuItem helpItem;

    JLabel titleLabel;
    JLabel subtitleLabel;

    MyButton playButton;
    MyButton importButton;
    MyButton createButton;

    // configuration and creation of the layout and design of the GUI
    LaunchPage() {

        // configure the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
        helpItem = new JMenuItem("About");

        helpItem.addActionListener(this);
        helpMenu.add(helpItem);
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);

        // configure the title
        titleLabel = new JLabel("Sudoku");
        titleLabel.setFont(new Font("Comic Sans", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(0, 100, 600, 50);

        // configure the sub-title
        subtitleLabel = new JLabel("created by JoTheOther");
        subtitleLabel.setFont(new Font("Comic Sans", Font.PLAIN, 14));
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);
        subtitleLabel.setBounds(0, 150, 600, 30);

        // configure the play-button
        playButton = new MyButton("PLAY", Color.RED, 25, 250, 100);
        playButton.setBounds(175, 275, 250, 100);
        playButton.addActionListener(this);

        // configure the import-button
        importButton = new MyButton("Import Game", Color.LIGHT_GRAY, 20, 200, 75);
        importButton.setBounds(70, 400, 200, 75);
        importButton.addActionListener(this);

        // configure the create-button
        createButton = new MyButton("Create Game", Color.LIGHT_GRAY, 20, 200, 75);
        createButton.setBounds(330, 400, 200, 75);
        createButton.addActionListener(this);

        // configure overall layout and specific details
        this.setTitle("Sudoku");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0xD6E6F2));
        this.setResizable(false);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);

        // add the labels and buttons
        this.add(titleLabel);
        this.add(subtitleLabel);
        this.add(playButton);
        this.add(importButton);
        this.add(createButton);
    }

    // configure the actions that should be performed when a specififc button is
    // pressed
    @Override
    public void actionPerformed(ActionEvent e) {

        // open a new window with the 'play' mode
        if (e.getSource() == playButton) {
            new PlayPage();

            // open a new window with the 'import' mode
        } else if (e.getSource() == importButton) {
            new ImportPage();

            // open a new window with the 'create' mode
        } else if (e.getSource() == createButton) {
            new CreatePage();

        }
        // show an information message about the rules and tips
        if (e.getSource() == helpItem) {
            System.out.println("Help Menu clicked.");
            JOptionPane.showMessageDialog(this,
                    "Sudoku is a logic-based puzzle game played on a 9x9 grid, divided into nine 3x3 sub-grids.\n"
                            + "The goal is to fill each empty cell with a number from 1 to 9, such that each row, "
                            + "column,\nand sub-grid contains all the digits 1 to 9 without any repetition.\nThe puzzle is "
                            + "solved when all the cells are filled correctly.\n\nIf you are stuck you can reveal the "
                            + "solution by pressing 'Solution'.",
                    "About", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // set up a button object which is used for a cleaner configuration of the three
    // buttons (play, import & create)
    class MyButton extends JButton {

        MyButton(String text, Color backgroundColor, int fontSize, int width, int height) {
            super(text);
            this.setFocusable(false);
            this.setHorizontalTextPosition(JButton.CENTER);
            this.setVerticalTextPosition(JButton.CENTER);
            this.setFont(new Font("Comic Sans", Font.BOLD, fontSize));
            this.setBackground(backgroundColor);
            this.setSize(width, height);
        }
    }
}
