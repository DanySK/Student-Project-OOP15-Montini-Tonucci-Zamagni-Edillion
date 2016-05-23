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
import model.stages.StageData;


public class SelezionaStageGUI extends JFrame implements View {

    private static final long serialVersionUID = 1408340187530329667L;
    
    
    public SelezionaStageGUI(String title) {
        super (title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JLabel selectlbl = new JLabel("Selezionare lo stage:");
        JComboBox<StageData> selectCB = new JComboBox<>(StageData.values());
        JButton play = new JButton("PLAY");
        play.addActionListener(e -> {
            //da sistemare, mettere la chiamata a stageLoad del controller che è da sistemare anche quello
            //per provare ho messo la new della CombatGUI qui
            Game.getInstance().stageLoad((StageData)selectCB.getSelectedItem());
            //new CombatGUI("prova");
            this.setVisible(false);
        });
        
        panel.add(selectlbl);
        panel.add(selectCB);        
        panel.add(play);
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


