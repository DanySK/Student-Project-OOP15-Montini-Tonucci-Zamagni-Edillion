package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.StageLoopImp;
import model.entities.Entity;
import model.entities.StatType;
import model.skills.Skill;
import view.panels.EnemiesPanel;
import view.panels.HeroPanel;

public class CombatGUI extends JFrame {

    private static final long serialVersionUID = -5474755205851269039L;


    List<JButton> attackButtonList = new ArrayList<>();
    JPanel sp = new JPanel(new BorderLayout());
    JPanel swnp = new JPanel(new FlowLayout());
    
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
        
        //Chiamata al metodo che genera il panello dei nemici
        generateEnemiesPanel(monsterList);
        
        sp.setPreferredSize(new Dimension((int)width,(int)height/2));
        
        sp.setBorder(BorderFactory.createLineBorder(Color.GREEN,4));
        
        //Creazione pannelli inferiore destro e inferiore sinistro
        JPanel swp = new JPanel(new BorderLayout());
        swp.setPreferredSize(new Dimension((int)width/2,(int)height/2));
        
        swp.setBorder(BorderFactory.createLineBorder(Color.RED,3));
        
        //Chiamata al metodo che genera il pannello dell'eroe
        generateHeroPanel(heroName,heroStats);
        
        //Creazione pannelli inferiore detro sopra e inferiore destro sotto
        
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
        
        this.add(sp,BorderLayout.SOUTH);
        
        //visto che c'è setsize il pack non dovrebbe servire
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public void Victory() {
        JOptionPane.showMessageDialog(this, "Complimenti");
        this.setVisible(false);
        new SelezionaStageGUI("Selezione dello stage da affrontare");
    }
    
    //Generazione del pannello delle statistiche dell'hero
    public JPanel generateHeroPanel(String heroName, Map<StatType, Integer> heroStats) {
        JPanel heroStatsPanel = new HeroPanel(heroName, heroStats);
        sp.add(heroStatsPanel,BorderLayout.EAST);
        heroStatsPanel.repaint();
        heroStatsPanel.revalidate();
        return heroStatsPanel;
    }
    
    //Enable dei bottoni
    public void enableButtons(boolean state){
        for (JButton b : attackButtonList){
            b.setEnabled(state);
        }
        swnp.revalidate();
    }
    
    //Generazione del pannello dei nemici
    public JPanel generateEnemiesPanel(List<Entity> monsterList){
        JPanel monsterStatsPanel = new EnemiesPanel(monsterList);
        this.add(monsterStatsPanel,BorderLayout.NORTH);
        monsterStatsPanel.repaint();
        monsterStatsPanel.revalidate();
        return monsterStatsPanel;
    }
}
