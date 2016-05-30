package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Game;
import model.stages.StageData;
import model.stages.StageState;

/**
 * The GUI that allows the player to select
 * the stage he wants to play, the stages are unlocked
 * sequentially
 */
public class StageSelectionGUI extends JFrame implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 1408340187530329667L;
    
    /**
     * Constructor of the StageSelectionGUI
     * @param title title of the JFrame
     */
    public StageSelectionGUI(String title) {
        super (title);
        this.initialize();
        
    }

    @Override
    public void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JLabel selectlbl = new JLabel("Select the stage you want to play:");
        JComboBox<String> selectCB = new JComboBox<>();
        
        for (StageData sd : StageData.values()){
            if(sd.getState() == StageState.DONE || sd.getState() == StageState.UNLOCKED){
                selectCB.addItem(sd.getName());
            }
        }
        
        JButton play = new JButton("PLAY");
        play.addActionListener(e -> {
            for (StageData s : StageData.values()){
                if(s.getName().equals((String)selectCB.getSelectedItem())){
                    Game.getInstance().stageLoad(s);
                    this.setVisible(false);
                }
            }
        });
        
        panel.add(selectlbl);
        panel.add(selectCB);        
        panel.add(play);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}


