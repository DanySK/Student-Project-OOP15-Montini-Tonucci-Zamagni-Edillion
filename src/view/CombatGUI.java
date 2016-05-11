package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CombatGUI extends JFrame {

    
    private static final long serialVersionUID = -5474755205851269039L;

    public CombatGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //la barra in alto è 40 pixel senza pack 25 con pack
        //forse esiste un modo migliroe anzichè usare sempre i cast
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        //double width = 800;
        //double height = 600;
        this.setSize((int)(width), (int)(height));
        
        //Creazione pannello superiore e inferiore
        JPanel np = new JPanel(new BorderLayout());
        JPanel sp = new JPanel(new BorderLayout());
        np.setPreferredSize(new Dimension((int)width,(int)height/2));
        sp.setPreferredSize(new Dimension((int)width,(int)height/2));
        np.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
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
        
        
        JLabel hpLabel = new JLabel("HP");
        gbc.gridx = 0;
        gbc.gridy = 0;
        hpLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sep.add(hpLabel,gbc);

        JProgressBar hpBarHero  = new JProgressBar(SwingConstants.HORIZONTAL,0,500);
        gbc.gridx = 1;
        hpBarHero.setValue(300);
        hpBarHero.setStringPainted(true);
        
        JTextField hpField = new JTextField(String.valueOf(hpBarHero.getValue()));
        
        sep.add(hpField,gbc);
        gbc.gridx = 2;
        sep.add(hpBarHero,gbc);
        
        //Creazione pannelli inferiore detro sopra e inferiore destro sotto
        JPanel swnp = new JPanel(new FlowLayout());
        JPanel swsp = new JPanel(new BorderLayout());
        swnp.setPreferredSize(new Dimension((int)width/2,(int)height/4));
        swsp.setPreferredSize(new Dimension((int)width/2,(int)height/2));
        swnp.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        swsp.setBorder(BorderFactory.createLineBorder(Color.BLUE,4));

        //Creazione bottoni nel pannello delle skill
        JButton punch = new JButton("Punch");
        punch.addActionListener(e -> {
            
            //mettere la chiamata al metodo del controller
        });
        swnp.add(punch);
        JButton button2 = new JButton("Button2");
        swnp.add(button2);
        
        //Aggiunta dei vari componenti alla CombatGUI
        swp.add(swnp,BorderLayout.NORTH);
        swp.add(swsp,BorderLayout.SOUTH);
        
        sp.add(swp,BorderLayout.WEST);
        sp.add(sep,BorderLayout.EAST);
        
        this.add(np,BorderLayout.NORTH);
        this.add(sp,BorderLayout.SOUTH);
        
        
        //visto che c'è setsize il pack non dovrebbe servire
        //this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
}
