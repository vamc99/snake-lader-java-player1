import java.awt.*;  
import javax.swing.*;
import java.io.*;  
import java.util.Arrays; 

public class Snake{  
JFrame f;  
Snake(){
	File folder = new File("/home/vit/vamsi/java/snake/images/");
	File[] fileNames = folder.listFiles();
	Arrays.sort(fileNames); 
	System.out.println(fileNames[0]); 
    f=new JFrame();  
    JButton []b = new JButton[100];
    ImageIcon []ic = new ImageIcon[100];     
    for(int i=0;i<100;i++){
    		ic[0] = new ImageIcon(""+fileNames[i]);
    		b[0] = new JButton(ic[0]); 
    		f.add(b[0]);
    }
    f.setLayout(new GridLayout(10,10));  
    //setting grid layout of 3 rows and 3 columns  
  
    f.setSize(762,762);  
    f.setVisible(true);  
}  
public static void main(String[] args) {  
    new Snake();  
}  
}
