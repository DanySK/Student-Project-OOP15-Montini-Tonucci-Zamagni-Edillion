package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.StageLoopImp;
import model.entities.Entity;
import model.entities.StatType;
import model.skills.Skill;
import view.panels.EnemiesPanel;

public class CombatGUI extends JFrame {

    private static final long serialVersionUID = -5474755205851269039L;


    List<JButton> attackButtonList = new ArrayList<>();
   
    public CombatGUI(String title, StageLoopImp riferimentoController, List<Entity> monsterList, String heroName, 
            Map<StatType, Integer> heroStats, List<Skill> heroSkills) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //la barra in alto è 40 pixel senza pack 25 con pack
        //forse esiste un modo migliroe anzichè usare sempre i cast
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //double width = screenSize.getWidth();
        //double height = screenSize.getHeight();
        double width = 800;
        double height = 600;
        this.setSize((int)(width), (int)(height));
        

        //Creazione pannello superiore e inferiore
        //generateEnemiesPanel(monsterList);
        generateEnemiesPanel(monsterList);
        JPanel sp = new JPanel(new BorderLayout());
        
        sp.setPreferredSize(new Dimension((int)width,(int)height/2));
        
        sp.setBorder(BorderFactory.createLineBorder(Color.GREEN,4));
        
        //Creazione pannelli inferiore destro e inferiore sinistro
        JPanel swp = new JPanel(new BorderLayout());
        JPanel sep = new JPanel(new GridBagLayout());
        swp.setPreferredSize(new Dimension((int)width/2,(int)height/2));
        sep.setPreferredSize(new Dimension((int)width/2,(int)height/2));
        swp.setBorder(BorderFactory.createLineBorder(Color.RED,3));
        sep.setBorder(BorderFactory.createLineBorder(Color.BLUE,3));
        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel nameLabel = new JLabel(heroName);
        gbc.gridx = 0;
        gbc.gridy = 0;
        nameLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sep.add(nameLabel,gbc);

        Set<StatType> heroStatsLabelSet = heroStats.keySet();
        Collection<Integer> heroStatsFieldCollection = heroStats.values();
        List<JLabel> heroStatsLabel = heroStatsLabelSet.stream().map(String::valueOf).map(JLabel::new).collect(Collectors.toList());
        List<JTextField> heroStatsField = heroStatsFieldCollection.stream().map(String::valueOf).map(JTextField::new).collect(Collectors.toList());
        heroStatsLabel.forEach(b->{
            gbc.gridy++;
            sep.add(b,gbc);
        });
        gbc.gridy=0;
        gbc.gridx=1;
        heroStatsField.forEach(b->{
            gbc.gridy++;
            sep.add(b,gbc);
        });
        
        //Creazione pannelli inferiore detro sopra e inferiore destro sotto
        JPanel swnp = new JPanel(new FlowLayout());
        JPanel swsp = new JPanel(new BorderLayout());
        swnp.setPreferredSize(new Dimension((int)width/2,(int)height/4));
        swsp.setPreferredSize(new Dimension((int)width/2,(int)height/2));
        swnp.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        swsp.setBorder(BorderFactory.createLineBorder(Color.BLUE,4));
        
        //Creazione bottoni nel pannello delle skill
        for (Skill i : heroSkills){
            JButton button = new JButton(i.getName());
            attackButtonList.add(button);
            button.addActionListener(e -> {
                String[] monsterArray = monsterList.stream().map(Entity::getName).map(String::new).toArray(size -> new String[size]);
                int n = JOptionPane.showOptionDialog(this,
                        "Choose the enemy to attack",
                        "Select enemy to attack",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        monsterArray,
                        monsterArray[0]);
               riferimentoController.attack(i, n);
            });
            swnp.add(button);
        }
        
        //Aggiunta dei vari componenti alla CombatGUI
        swp.add(swnp,BorderLayout.NORTH);
        swp.add(swsp,BorderLayout.SOUTH);
        
        sp.add(swp,BorderLayout.WEST);
        sp.add(sep,BorderLayout.EAST);
        
        this.add(sp,BorderLayout.SOUTH);
        
        
        //visto che c'è setsize il pack non dovrebbe servire
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public void enableButtons(boolean state){
        for (JButton b : attackButtonList){
            b.setEnabled(state);
        }
    }
    
    //Generazione del pannello dei nemici
    public JPanel generateEnemiesPanel(List<Entity> monsterList){
        JPanel panel1 = new EnemiesPanel(monsterList);
        this.add(panel1,BorderLayout.NORTH);
        panel1.revalidate();
        return panel1;
    }
}
