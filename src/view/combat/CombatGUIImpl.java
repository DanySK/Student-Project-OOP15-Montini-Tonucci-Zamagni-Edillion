package view.combat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.StageLoopImp;
import model.entities.Entity;
import model.entities.StatType;
import model.skills.Skill;
import view.StageSelectionGUI;
import view.combat.panels.EnemiesPanel;
import view.combat.panels.HeroPanel;

/**
 * 
 * The main GUI of the game
 * Here is where the player can attack the enemies,
 * or check the hero or enemies statistics
 */
public class CombatGUIImpl extends JFrame implements CombatGUI {

    /**
     * 
     */
    private static final long serialVersionUID = -5474755205851269039L;

    private final List<JButton> attackButtonList = new ArrayList<>();
    private final JPanel northernPanel = new JPanel(new BorderLayout());
    private final JPanel southernPanel = new JPanel(new BorderLayout());
    private final JPanel southernWesternNorthernPanel = new JPanel(new FlowLayout());
    private final JPanel southernWesternSouthernPanel = new JPanel(new BorderLayout());
    private final JProgressBar turnProgressBar = new JProgressBar();
    private final JLabel turnlbl = new JLabel(String.valueOf(100));
    private final JTextArea log = new JTextArea();
    private Optional<Integer> maxHpValue = Optional.empty();
    
    /**
     * Constructor of the CombatGUI
     * @param title title of the JFrame
     * @param controllerReference the instance of the Controller
     * @param monsterList list of the monsters of the current stage
     * @param heroName name of the current hero
     * @param heroStats statistics of the current hero
     * @param heroSkills skills usable by the current hero
     */
    public CombatGUIImpl(String title, StageLoopImp controllerReference, List<Entity> monsterList, String heroName, 
            Map<StatType, Integer> heroStats, List<Skill> heroSkills) {
        super(title);
        this.initialize(controllerReference, monsterList, heroName, heroStats, heroSkills);
    }
    
    @Override
    public void initialize(StageLoopImp controllerReference, List<Entity> monsterList, String heroName, 
            Map<StatType, Integer> heroStats, List<Skill> heroSkills) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        double width = 800;
        double height = 600;
        this.setSize((int)(width), (int)(height));
        
        //Generation of the enemies panel
        generateEnemiesPanel(monsterList);
        
        //Creation of the combat log panel
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setPreferredSize(new Dimension((int)width,(int)height/6));
        logPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,3));
        log.setEditable(false);
        JScrollPane scrollBar = new JScrollPane(log);
        logPanel.add(scrollBar);
        
        //Creation of the southern panel
        southernPanel.setPreferredSize(new Dimension((int)width,(int)height/2));
        southernPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,4));
        
        //Creation of the southern western panel
        JPanel southernWesternPanel = new JPanel(new BorderLayout());
        southernWesternPanel.setPreferredSize(new Dimension((int)width/2,(int)height/2));
        southernWesternPanel.setBorder(BorderFactory.createLineBorder(Color.RED,3));
        
        //Generation of the hero statistics panel
        generateHeroPanel(heroName,heroStats);
        
        //Creation of the southern western panels
        southernWesternNorthernPanel.setPreferredSize(new Dimension((int)width/2,(int)height/4));
        southernWesternNorthernPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        southernWesternSouthernPanel.setPreferredSize(new Dimension((int)width/2,(int)height/2));
        southernWesternSouthernPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,4));
        
        //Creation of the hero skills buttons
        for (Skill i : heroSkills){
            JButton button = new JButton(i.getName());
            attackButtonList.add(button);
            button.addActionListener(e -> {
                String[] monsterArray = monsterList.stream().map(Entity::getName).map(String::new).toArray(size -> new String[size]);
                int n = JOptionPane.showOptionDialog(this,
                        "Choose the enemy you want to attack",
                        "Select the enemy you want to attack",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        monsterArray,
                        monsterArray[0]);
                if (n != -1){
                    controllerReference.attack(i, n);    
                }
            });
            southernWesternNorthernPanel.add(button);
        }
        
        southernWesternNorthernPanel.add(turnlbl);
        southernWesternNorthernPanel.add(turnProgressBar);
        
        southernWesternPanel.add(southernWesternNorthernPanel,BorderLayout.NORTH);
        southernWesternPanel.add(southernWesternSouthernPanel,BorderLayout.SOUTH);
        
        northernPanel.add(logPanel,BorderLayout.SOUTH);
        southernPanel.add(southernWesternPanel,BorderLayout.WEST);
        
        this.add(northernPanel,BorderLayout.NORTH);
        this.add(southernPanel,BorderLayout.SOUTH);
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        
    }
    
    @Override
    public void victory(int exp, int gold) {
        JOptionPane.showMessageDialog(this, "Congratulations, you have earned " + exp + " experience and " + gold + " gold.");
        this.setVisible(false);
        new StageSelectionGUI("Select the stage you want to face");
    }
    
    @Override
    public void defeat() {
        JOptionPane.showMessageDialog(this, "You failed the stage.");
        this.setVisible(false);
        new StageSelectionGUI("Select the stage you want to face");
    }
    
    @Override
    public void generateHeroPanel(String heroName, Map<StatType, Integer> heroStats) {
        if(!maxHpValue.isPresent()){
            maxHpValue = Optional.of(heroStats.get(StatType.HP));
        }
        JPanel heroStatsPanel = new HeroPanel(heroName, heroStats, maxHpValue);
        southernPanel.add(heroStatsPanel,BorderLayout.EAST);
        heroStatsPanel.repaint();
        heroStatsPanel.revalidate();
    }
    
    @Override
    public void enableButtons(boolean state){
        for (JButton b : attackButtonList){
            b.setEnabled(state);
        }
        southernWesternNorthernPanel.revalidate();
    }
    
    @Override
    public void generateEnemiesPanel(List<Entity> monsterList){
        JPanel monsterStatsPanel = new EnemiesPanel(monsterList);
        northernPanel.add(monsterStatsPanel,BorderLayout.NORTH);
        monsterStatsPanel.repaint();
        monsterStatsPanel.revalidate();
    }
    
    @Override
    public void refreshCombatLog(String attacker, String target, String skill, int damage){
        log.append(attacker + " attacks " + target + " with " + skill + " for " + damage + " damage\n");
    }
    
    @Override
    public void refreshCount(int time){
        turnProgressBar.setValue(time);
        turnProgressBar.repaint();
        turnProgressBar.revalidate();
        turnlbl.setText(String.valueOf(time));
        turnlbl.repaint();
        turnlbl.revalidate();
    } 
}
