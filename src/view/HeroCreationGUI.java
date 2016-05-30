package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Game;
import model.entities.Role;

/**
 * The GUI where the player can create his hero
 * The player can choose the name of the hero and the hero's role
 */
public class HeroCreationGUI extends JFrame implements View {

    /**
     * 
     */
    private static final long serialVersionUID = -2321959936230614041L;
    
    /**
     * Constructor of the HeroCreationGUI
     * @param title title of the JFrame
     */
    public HeroCreationGUI(String title){
        super(title);
        this.initialize();
    }

    @Override
    public void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(0,2,5,5));
        JLabel heroNamelbl = new JLabel("Insert hero name:");
        JTextField heroNametf = new JTextField();
        JLabel heroRolelbl = new JLabel("Select hero role:");
        JComboBox<Role> heroRoleCB = new JComboBox<>(Role.values());
        JButton crea = new JButton("Crea");
        crea.addActionListener(e -> {
            Game.getInstance().buildHero(heroNametf.getText(),(Role)(heroRoleCB.getSelectedItem()));
            this.setVisible(false);
        });
        
        panel.add(heroNamelbl);
        panel.add(heroNametf);
        panel.add(heroRolelbl);
        panel.add(heroRoleCB);
        panel.add(crea);
        this.add(panel);
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
