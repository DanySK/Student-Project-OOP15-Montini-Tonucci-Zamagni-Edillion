package view.combat.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import model.entities.StatType;

/**
 * Panel of the hero statistics
 */
public class HeroPanel extends JPanel{

    /**
     * 
     */
    private static final long serialVersionUID = 3539826908199528665L;
    
    private final double width = 800;
    private final double height = 600;
    
    /**
     * Constructor of the hero statistics panel
     * @param heroName name of the current hero
     * @param heroStats statistics of the current hero
     * @param maxHpValue Optional that contains the max hero hp value
     */
    public HeroPanel(String heroName,Map<StatType, Integer> heroStats, Optional<Integer> maxHpValue){
        
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension((int)width/2,(int)height/2));
        this.setBorder(BorderFactory.createLineBorder(Color.BLUE,3));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel nameLabel = new JLabel(heroName);
        gbc.gridx = 0;
        gbc.gridy = 0;
        nameLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(nameLabel,gbc);

        Set<StatType> heroStatsLabelSet = heroStats.keySet();
        Collection<Integer> heroStatsFieldCollection = heroStats.values();
        List<JLabel> heroStatsLabel = heroStatsLabelSet.stream().map(String::valueOf).map(JLabel::new).collect(Collectors.toList());
        List<JLabel> heroStatsField = heroStatsFieldCollection.stream().map(String::valueOf).map(JLabel::new).collect(Collectors.toList());
        heroStatsLabel.forEach(b->{
            gbc.gridy++;
            this.add(b,gbc);
        });
        gbc.gridx=1;
        gbc.gridy=0;
        heroStatsField.forEach(b->{
            gbc.gridy++;
            this.add(b,gbc);
        });
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        JProgressBar hpBar = new JProgressBar(0,maxHpValue.get());
        hpBar.setValue(heroStats.get(StatType.HP));
        hpBar.setStringPainted(true);
        this.add(hpBar,gbc);
        
        gbc.gridy = 6;
        JProgressBar expBar = new JProgressBar(0,heroStats.get(StatType.LEVEL)*100);
        expBar.setValue(heroStats.get(StatType.EXP));
        expBar.setStringPainted(true);
        this.add(expBar,gbc);
        
    }
}
