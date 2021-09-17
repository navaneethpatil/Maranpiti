import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Game {
JFrame window;
Container con;
JPanel titleNamePanel,startButtonPanel;
JLabel titleNameLabel;
Font titleFont = new Font("Times New Roman", Font.PLAIN,90);
Font normalFont = new Font("Times New Roman", Font.PLAIN,30);
JButton startButton;
TitleScreenHandler tsHandler= new TitleScreenHandler();
public static void main(String[] args) {
new Game();
}
public Game() {
window = new JFrame();
window.setSize(800, 600);
window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
window.getContentPane().setBackground(Color.BLACK);
window.getContentPane().setLayout(null);
window.setVisible(true);
con = window.getContentPane();
titleNamePanel = new JPanel();
titleNamePanel.setBounds(100, 100, 600, 150);
titleNamePanel.setBackground(Color.black);
titleNameLabel = new JLabel("MARANPITI");
titleNameLabel.setForeground(Color.white);
titleNameLabel.setFont(titleFont);
startButtonPanel= new JPanel();
startButtonPanel.setBounds(300,400,200,100);
startButtonPanel.setBackground(Color.black);
startButton = new JButton("START");
startButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
if (e.getSource() == startButton)
{
window.dispose();
new Maranpiti();
};
}
});
startButton.setBackground(Color.black);
startButton.setForeground(Color.white);
startButton.setFont(normalFont);
startButton.addActionListener(tsHandler);
titleNamePanel.add(titleNameLabel);
startButtonPanel.add(startButton);
con.add(titleNamePanel);
con.add(startButtonPanel);
}
public class TitleScreenHandler implements ActionListener{
public void actionPerformed(ActionEvent event) {
}
}
