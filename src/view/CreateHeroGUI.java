package view;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Game;
import model.entities.BasicEntity;
import model.entities.Role;
import model.entities.StatType;

public class CreateHeroGUI extends JFrame implements View {

    /**
     * 
     */
    private static final long serialVersionUID = -2321959936230614041L;
    
    public CreateHeroGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(0,2,5,5));
        JLabel heroNamelbl = new JLabel("Inserire nome eroe:");
        JTextField heroNametf = new JTextField();
        JLabel heroRolelbl = new JLabel("Selezionare ruolo eroe");
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

    @Override
    public void refreshState(List<BasicEntity> enemyList) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void refreshHero(StatType stat, int value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void victory() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void defeat() {
        // TODO Auto-generated method stub
        
    }
    
}
