package view;

import javax.swing.JButton;
import javax.swing.JFrame;


public class StageGUI extends JFrame {

    //Avviare questa dal main, (da sistemare)
    private static final long serialVersionUID = 1408340187530329667L;
    
    
    public StageGUI(String title) {
        super (title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton play = new JButton("PLAY");
        play.addActionListener(e -> {
            new CombatGUI("prova");
            this.setVisible(false);
        });
        this.getContentPane().add(play);
        this.pack();
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               new StageGUI("Game");
            }
            
        });
    }
}


