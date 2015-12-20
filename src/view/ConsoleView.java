package view;
import model.Entity;

/**
 * 
 * Test class, da rimuovere -Leonardo 
 *
 */
public class ConsoleView implements View {
    
    Entity player;
    Entity enemy;
    
    public ConsoleView(Entity player, Entity enemy) {
        this.player = player;
        this.enemy = enemy;
    }
    
    @Override
    public void refreshState() {
        System.out.println(this.player.getName() + ": " + this.player.getHp() + " HP");       
        //System.out.println(enemy.getName() + ": " + player.getHp() + " HP");       
    }
    
    public void printAction(Entity attacker, Entity target, int skillId) {
        System.out.println(attacker.getName() + " attacca " + target.getName() + " con " + attacker.getSkill(skillId) + ", danni: " 
                            + attacker.getSkill(skillId).getDamage());
    }

}
