package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Game;

/**
 * The GUI that starts the game
 * The PLAY button starts a new game
 * The LOAD GAME button allows the player to load a savegame
 * The CREDITS button shows who made the game
 */
public class StartGUI extends JFrame implements View {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7946344536440453522L;
    
    /**
     * Constructor of the StartGUI
     * @param title title of the JFrame
     */
    public StartGUI(String title){
        super(title);
        this.initialize();
    }

    @Override
    public void initialize() {
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        
        JButton gioca = new JButton("PLAY");
        gioca.addActionListener(e -> {
            new HeroCreationGUI("Hero Creation");
            this.setVisible(false);
        });
        
        JButton loadgame = new JButton("LOAD GAME");
        loadgame.addActionListener(e -> {
            Game.getInstance().continues();
            this.setVisible(false);
        });
        JButton credits = new JButton("CREDITS");
        credits.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "OOP Project 2015/2016\n"
                    + "Planned and carried out by:\n"
                    + "Leonardo Montini: Model\n"
                    + "Enrico Zamagni: Controller\n"
                    + "Riccardo Tonucci: View", 
                    "Credits", 
                    JOptionPane.INFORMATION_MESSAGE);
        });
        
        panel.add(gioca);
        panel.add(loadgame);
        panel.add(credits);
        
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
