package view.combat.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import model.entities.Entity;
import model.entities.StatType;
import model.entities.BasicEntity.StatTime;

/**
 * Panel of the enemies statistics
 */
public class EnemiesPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -8397873417899636998L;
    
    private final double width = 800;
    private final double height = 600;
    
    /**
     * 
     * @param monsterList list of the monsters of the current stage
     */
    public EnemiesPanel(List<Entity> monsterList){
        
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension((int)width,(int)height/3));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        this.add(new JLabel("Name"),gbc);
        List<JLabel> monstersNameLabel = monsterList.stream().map(Entity::getName).map(JLabel::new).collect(Collectors.toList());
        monstersNameLabel.forEach(b->{
            gbc.gridy++;
            this.add(b,gbc);
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(new JLabel("HP"),gbc);
        for (Entity i : monsterList){
            JLabel label = new JLabel(String.valueOf(i.getStat(StatType.HP, StatTime.CURRENT)));
            gbc.gridy++;
            this.add(label,gbc);
        }
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(new JLabel("Mana"),gbc);
        for (Entity i : monsterList){
            JLabel label = new JLabel(String.valueOf(i.getStat(StatType.MANA, StatTime.CURRENT)));
            gbc.gridy++;
            this.add(label,gbc);
        }
        gbc.gridx = 3;
        gbc.gridy = 0;
        this.add(new JLabel("Level"),gbc);
        for (Entity i : monsterList){
            JLabel label = new JLabel(String.valueOf(i.getStat(StatType.LEVEL, StatTime.CURRENT)));
            gbc.gridy++;
            this.add(label,gbc);
        }
        gbc.gridx = 4;
        gbc.gridy = 0;
        this.add(new JLabel("Speed"),gbc);
        for (Entity i : monsterList){
            JLabel label = new JLabel(String.valueOf(i.getStat(StatType.SPEED, StatTime.CURRENT)));
            gbc.gridy++;
            this.add(label,gbc);
        }
        gbc.gridx = 5;
        gbc.gridy = 0;
        this.add(new JLabel("HP Bar"),gbc);
        for (Entity i : monsterList){
            JProgressBar pb = new JProgressBar(0,i.getStat(StatType.HP, StatTime.GLOBAL));
            pb.setValue(i.getStat(StatType.HP, StatTime.CURRENT));
            pb.setStringPainted(true);
            gbc.gridy++;
            this.add(pb,gbc);
        }
    }
}
