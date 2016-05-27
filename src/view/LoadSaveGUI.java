package view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Game;
import model.entities.BasicEntity;
import model.entities.StatType;

public class LoadSaveGUI extends JFrame implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 388167727584142026L;
    
    public LoadSaveGUI(String title,String[] existingSave){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JLabel loadSavelbl = new JLabel("What savegame do you want to load?");
        JComboBox<String> loadSaveCB = new JComboBox<>(existingSave);
        JButton loadSaveButton = new JButton("Load");
        loadSaveButton.addActionListener(e -> {
            Game.getInstance().gioca(/*(String)loadSaveCB.getSelectedItem()*/);
            this.setVisible(false);
        });
        
        panel.add(loadSavelbl);
        panel.add(loadSaveCB);
        panel.add(loadSaveButton);
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
