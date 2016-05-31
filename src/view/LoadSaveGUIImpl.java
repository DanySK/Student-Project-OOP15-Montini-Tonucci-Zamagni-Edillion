package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Game;

/**
 * The GUI that allows the player to resume a game previously saved
 */
public class LoadSaveGUIImpl extends JFrame implements LoadSaveGUI {

    /**
     * 
     */
    private static final long serialVersionUID = 388167727584142026L;
    
    /**
     * Constructor of the LoadSaveGUI
     * @param title the title of the JFrame
     * @param existingSave array of the savegames
     */
    public LoadSaveGUIImpl(String title, String[] existingSave){
        super(title);
        this.initialize(existingSave);
    }

    @Override
    public void initialize(String[] existingSave) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JLabel loadSavelbl = new JLabel("What savegame do you want to load?");
        for (int i = 0; i < existingSave.length; i++){
            existingSave[i] = existingSave[i].substring(0, (existingSave[i].length())-4);
        }
        JComboBox<String> loadSaveCB = new JComboBox<>(existingSave);
        JButton loadSaveButton = new JButton("Load");
        loadSaveButton.addActionListener(e -> {
            Game.getInstance().beginPlay((String)loadSaveCB.getSelectedItem() + ".dat");
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
}
