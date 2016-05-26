package view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Game;
import controller.GameEngine;
import model.entities.BasicEntity;
import model.entities.StatType;

public class StartGUI extends JFrame implements View {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7946344536440453522L;

    public StartGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        
        JButton gioca = new JButton("GIOCA");
        gioca.addActionListener(e -> {
            Game.getInstance().gioca();
            this.setVisible(false);
        });
        
        JButton loadgame = new JButton("LOAD GAME");
        loadgame.addActionListener(e -> {
            Game.getInstance().continues();
            this.setVisible(false);
        });
        
        JButton settings = new JButton("SETTINGS");
        JButton credits = new JButton("CREDITS");
        
        panel.add(gioca);
        panel.add(loadgame);
        panel.add(settings);
        panel.add(credits);
        
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
