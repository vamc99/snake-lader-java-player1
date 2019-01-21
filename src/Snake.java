import java.awt.*;  
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays; 

public class Snake implements ActionListener {
    public int [] cellMap = {0,90,91,92,93,94,95,96,97,98,99,89,88,87,86,85,84,83,82,81,80,70,71,72,73,74,75,76,77,78,79,69,
            68,67,66,65,64,63,62,61,60,50,51,52,53,54,55,56,57,58,59,49,48,47,46,45,44,43,42,41,40,30,31,32,33,34,35,
            36,37,38,39,29,28,27,26,25,24,23,22,21,20,10,11,12,13,14,15,16,17,18,19,9,8,6,7,5,4,3,2,1};

    public JFrame mainFrame;
    public JPanel contentPane;
    public JPanel gamePanel;
    public JPanel sidePanel;
    public JLabel []b;
    public ImageIcon []ic;
    public JLabel displayScore, score;
    public JButton diceButton;
    public Player p1,p2;

    int player1 = 0, player2 = 0;
    int player1Previous = 0, player2Previous = 0;
    int diceValue, cellNumber;

    public Snake(){
        p1 = new Player("vamsi");
        mainFrame = new JFrame();
	    File folder = new File("images/tiles");
	    File[] fileNames = folder.listFiles();
    	Arrays.sort(fileNames);
        gamePanel=new JPanel();
        b = new JLabel[100];
        ic = new ImageIcon[100];
        for(int i=0;i<100;i++){
    		ic[i] = new ImageIcon(""+fileNames[i]);
    		b[i] = new JLabel(ic[i]);
    		gamePanel.add(b[i]);
        }

        contentPane = new JPanel(new FlowLayout());
        gamePanel.setLayout(new GridLayout(10,10));  
        gamePanel.setPreferredSize(new Dimension(762, 762));
        sidePanel = new JPanel(new GridLayout(3,1));
        diceButton = new JButton(new ImageIcon("images/dice.jpg"));
        score  = new JLabel("Score");
        displayScore = new JLabel();
        diceButton.setPreferredSize(new Dimension(126, 126));
        sidePanel.add(diceButton);
        sidePanel.add(score);
        sidePanel.add(displayScore);
        diceButton.addActionListener(this::actionPerformed);
        sidePanel.setPreferredSize(new Dimension(266, 762));
        contentPane.add(gamePanel);
        contentPane.add(sidePanel);
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
        if(player1Previous!=0&&player1<=100) {
            b[cellMap[player1]].setIcon(null);
            b[cellMap[player1]].setText("P1");
            b[cellMap[player1Previous]].setIcon(ic[cellMap[player1Previous]]);
        }
        if(player1Previous==0){
            b[cellMap[player1]].setIcon(null);
            b[cellMap[player1]].setText("P1");
        }
    }
}