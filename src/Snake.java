import java.awt.*;  
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays; 

public class Snake implements ActionListener {
    //cellMap is used to map player score and image icon array
    private int [] cellMap = {0,90,91,92,93,94,95,96,97,98,99,89,88,87,86,85,84,83,82,81,80,70,71,72,73,74,75,76,77,78,79,69,
            68,67,66,65,64,63,62,61,60,50,51,52,53,54,55,56,57,58,59,49,48,47,46,45,44,43,42,41,40,30,31,32,33,34,35,
            36,37,38,39,29,28,27,26,25,24,23,22,21,20,10,11,12,13,14,15,16,17,18,19,9,8,7,6,5,4,3,2,1};

    private JFrame mainFrame;    // main frame
    private JPanel contentPane;  // content pane used to hold all the panes
    private JPanel gamePanel;    // game panel
    private JPanel sidePanel;    // score board panel
    private JLabel []b;          // array of label
    private ImageIcon []ic;      // array of image icons
    private JLabel displayScore, score; // display score label and score label
    private JButton diceButton;   // dice Button
    private Player p1,p2;

    private int player1 = 0, player2 = 0;
    private int player1Previous = 0, player2Previous = 0;
    private int diceValue;

    private Snake(){
        // initialize player 1
        p1 = new Player("Veda");

        // game panel with Grid Layout 10 by 10
        gamePanel =new JPanel(new GridLayout(10,10));
        gamePanel.setPreferredSize(new Dimension(762, 762));

        // read file names from the folder. it reads entire path
        File folder = new File("images/tiles");
        File[] fileNames = folder.listFiles();
        Arrays.sort(fileNames);

        // create 100 labels and set each label with corresponding image
        b = new JLabel[100];
        ic = new ImageIcon[100];
        for(int i=0;i<100;i++){
    		ic[i] = new ImageIcon(""+fileNames[i]);
    		b[i] = new JLabel(ic[i]);
    		gamePanel.add(b[i]);
        }
        // dice button with action Listener
        diceButton = new JButton(new ImageIcon("images/dice.jpg"));
        diceButton.setPreferredSize(new Dimension(126, 126));
        diceButton.addActionListener(this::actionPerformed);

        score  = new JLabel("Score");

        // show dice value
        displayScore = new JLabel();
        displayScore.setForeground(Color.RED);
        displayScore.setFont(new Font("Serif", Font.PLAIN, 30));

        // side panel
        sidePanel = new JPanel(new GridLayout(3,1));
        sidePanel.setPreferredSize(new Dimension(266, 762));
        sidePanel.add(diceButton);
        sidePanel.add(score);
        sidePanel.add(displayScore);

        // content pane
        contentPane = new JPanel(new FlowLayout());
        contentPane.add(gamePanel);
        contentPane.add(sidePanel);

        // main frame
        mainFrame = new JFrame();
        mainFrame.setContentPane(contentPane);
        mainFrame.setSize(1028,762);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {  
        new Snake();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //roll Dice
        diceValue = Dice.rollDice();

        player1 = p1.calculatePlayerValue(diceValue);
        player1Previous = p1.getPreviousScore();

        displayScore.setText(""+diceValue);
        if(p1.isWin()){
            JOptionPane.showMessageDialog(mainFrame, p1.name+" won the match");
        }
        if(player1Previous!=0&&player1<100) {
            b[cellMap[player1]].setIcon(null);
            b[cellMap[player1]].setText("P1");
            b[cellMap[player1Previous]].setIcon(ic[cellMap[player1Previous]]);
        }
        if(player1Previous==0 && player1<100){
            b[cellMap[player1]].setIcon(null);
            b[cellMap[player1]].setText("P1");
        }
    }
}