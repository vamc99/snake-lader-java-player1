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
            36,37,38,39,29,28,27,26,25,24,23,22,21,20,10,11,12,13,14,15,16,17,18,19,9,8,7,6,5,4,3,2,1,0};

    private JFrame mainFrame;    // main frame
    private JPanel contentPane;  // content pane used to hold all the panes
    private JPanel gamePanel;    // game panel
    private JPanel sidePanel;    // score board panel
    private JLabel []b;          // array of label
    private ImageIcon []ic;      // array of image icons
    private JLabel displayScore, score, displayPlayer,displayPlayerScore; // display score label and score label
    private JButton diceButton;   // dice Button
    private Player p1,p2;

    private int player1 = 0, player2 = 0,choice=1;
    private int player1Previous = 0, player2Previous = 0;
    private int diceValue;
    private String file;     //dice image
    ImageIcon player1Img,player2Img,playersImg;

    private Snake(){
        // initialize player 1
        String name1 = JOptionPane.showInputDialog("Enter Player One Name");
        p1 = new Player(name1);
        String name2 = JOptionPane.showInputDialog("Enter Player Two Name");
        p2 = new Player(name2);

        // game panel with Grid Layout 10 by 10
        gamePanel =new JPanel(new GridLayout(10,10,2,2));
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

        player1Img = new ImageIcon((
                (new ImageIcon("images/red.jpg")).
                        getImage()).
                getScaledInstance(76, 76, java.awt.Image.SCALE_SMOOTH));

        player2Img = new ImageIcon((
                (new ImageIcon("images/blue.jpg")).
                        getImage()).
                getScaledInstance(76, 76, java.awt.Image.SCALE_SMOOTH));

        playersImg = new ImageIcon((
                (new ImageIcon("images/redblue.jpg")).
                        getImage()).
                getScaledInstance(76, 76, java.awt.Image.SCALE_SMOOTH));

        // dice button with action Listener
        diceButton = new JButton(new ImageIcon("images/dice.jpg"));
        diceButton.setPreferredSize(new Dimension(126, 126));
        diceButton.addActionListener(this);

        score  = new JLabel("Score");

        // show dice value
        displayScore = new JLabel();
        displayScore.setForeground(Color.RED);
        displayScore.setFont(new Font("Serif", Font.PLAIN, 30));

        //show player score
        displayPlayerScore = new JLabel(p1.name+"'s Turn ");
        displayPlayerScore.setForeground(Color.BLUE);
        displayPlayerScore.setFont(new Font("Serif", Font.PLAIN, 30));


        // side panel
        sidePanel = new JPanel(new GridLayout(3,1));
        sidePanel.setPreferredSize(new Dimension(266, 762));
        sidePanel.add(diceButton);
        // sidePanel.add(displayPlayer);
        sidePanel.add(displayPlayerScore);
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
        mainFrame.setTitle("Snake Lader Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        new Snake();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //roll Dice
        diceValue = Dice.rollDice();
        file="images/"+diceValue+".jpg";
        diceButton.setIcon(new ImageIcon(((new ImageIcon(file)).getImage()).getScaledInstance(126, 126, java.awt.Image.SCALE_SMOOTH)));

        ImageIcon player1Img = new ImageIcon((
                (new ImageIcon("images/red.jpg")).
                        getImage()).
                getScaledInstance(76, 76, java.awt.Image.SCALE_SMOOTH));

        ImageIcon player2Img = new ImageIcon((
                (new ImageIcon("images/blue.jpg")).
                        getImage()).
                getScaledInstance(76, 76, java.awt.Image.SCALE_SMOOTH));

        ImageIcon playersImg = new ImageIcon((
                (new ImageIcon("images/redblue.jpg")).
                        getImage()).
                getScaledInstance(76, 76, java.awt.Image.SCALE_SMOOTH));

        if(choice>0){
            player1 = p1.calculatePlayerValue(diceValue);
            player1Previous = p1.getPreviousScore();
            if(p1.isWin()){
                b[cellMap[player1]].setIcon(player1Img);
                b[cellMap[player1Previous]].setIcon(ic[cellMap[player1Previous]]);
                JOptionPane.showMessageDialog(mainFrame, p1.name+" won the match");
            }else {
                displayPlayerScore.setText(p2.name+"'s Turn");
                if(player1Previous!=0&&player1<100) {
                    if(player1==player2){
                        b[cellMap[player1]].setIcon(playersImg);
                        b[cellMap[player1Previous]].setIcon(ic[cellMap[player1Previous]]);
                    }
                    else if(player1Previous==player2){
                        b[cellMap[player1Previous]].setIcon(player2Img);
                        b[cellMap[player1]].setIcon(player1Img);

                    }
                    else{
                        b[cellMap[player1]].setIcon(player1Img);
                        b[cellMap[player1Previous]].setIcon(ic[cellMap[player1Previous]]);
                    }
                }
                else if(player1+diceValue>100)
                {
                    b[cellMap[player1Previous]].setIcon(null);
                    b[cellMap[player1Previous]].setIcon(player1Img);
                }
                if(player1Previous==0 && player1<100){
                    b[cellMap[player1]].setIcon(player1Img);
                }
                choice=-choice;
            }
        }
        else{
            player2=p2.calculatePlayerValue(diceValue);
            player2Previous = p2.getPreviousScore();

            if(p2.isWin()){
                b[cellMap[player2]].setIcon(player2Img);
                b[cellMap[player2Previous]].setIcon(ic[cellMap[player2Previous]]);
                JOptionPane.showMessageDialog(mainFrame, p2.name+" won the match");
            }else {
                displayPlayerScore.setText(p1.name+"'s Turn");
                if(player2Previous!=0&&player2<100) {
                    b[cellMap[player2]].setIcon(null);

                    if(player1==player2){
                        b[cellMap[player2]].setIcon(playersImg);
                        b[cellMap[player2Previous]].setIcon(ic[cellMap[player2Previous]]);
                    }
                    else if(player2Previous==player1){
                        b[cellMap[player2Previous]].setIcon(player1Img);
                        b[cellMap[player2]].setIcon(player2Img);
                    }
                    else{
                        b[cellMap[player2]].setIcon(player2Img);
                        b[cellMap[player2Previous]].setIcon(ic[cellMap[player2Previous]]);
                    }
                }
                else if(player2+diceValue>100){
                    //b[cellMap[player2Previous]].setIcon(null);
                    b[cellMap[player2Previous]].setIcon(player2Img);
                }
                if(player2Previous==0 && player2<100){
                    b[cellMap[player2]].setIcon(player2Img);
                }
                choice=-choice;
            }
        }
    }
}
