package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.entities.StatType;

public class HeroPanel extends JPanel{

    /**
     * 
     */
    private static final long serialVersionUID = 3539826908199528665L;
    
    double width = 800;
    double height = 600;
    
    public HeroPanel(String heroName,Map<StatType, Integer> heroStats){
        
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
        List<JTextField> heroStatsField = heroStatsFieldCollection.stream().map(String::valueOf).map(JTextField::new).collect(Collectors.toList());
        heroStatsLabel.forEach(b->{
            gbc.gridy++;
            this.add(b,gbc);
        });
        gbc.gridy=0;
        gbc.gridx=1;
        heroStatsField.forEach(b->{
            gbc.gridy++;
            this.add(b,gbc);
        });
    }
}
