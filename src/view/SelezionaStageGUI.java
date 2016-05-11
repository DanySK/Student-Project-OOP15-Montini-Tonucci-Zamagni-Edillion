package view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.entities.BasicEntity;
import model.entities.StatType;


public class SelezionaStageGUI extends JFrame implements View {

    private static final long serialVersionUID = 1408340187530329667L;
    
    
    public SelezionaStageGUI(String title) {
        super (title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton play = new JButton("PLAY");
        play.addActionListener(e -> {
            //da sistemare, mettere la chiamata a stageLoad del controller che è da sistemare anche quello
            //per provare ho messo la new della CombatGUI qui
            new CombatGUI("prova");
            this.setVisible(false);
        });
        this.getContentPane().add(play);
        this.pack();
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


