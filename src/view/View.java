package view;

import java.util.List;

import model.entities.BasicEntity;
import model.entities.StatType;

public interface View {

    //sistemare l'interfaccia perchè attualmente in ogni classe sarebbe da implementare
    //i metodi qua sotto, ma è sbagliato
    public void refreshState(List<BasicEntity> enemyList);
   
    public void refreshHero(StatType stat, int value);
   
    public void victory();
   
    public void defeat();        
}
