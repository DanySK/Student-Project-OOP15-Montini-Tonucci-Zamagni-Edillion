import controller.*;
import data.StageData;
import model.*;
import model.skills.BasicSkills;

    /**
     * 
     * Da qui partirà tutto, ora usiamola per fare test globali
     *
     */
public class Main {

    private static Entity player1 = new EntityImpl("Giocatore", 800, BasicSkills.values());
    
    public static void main(String[] args) {
        GameEngine game = new Game();
        Stage stage = StageData.getData().getNext();
        
        Entity en1 = stage.getEnemyList().get(0);
        printState(stage);
        game.attack(player1, en1, 1);
        game.attack(player1, en1, 0);
        game.attack(en1, player1, 0);
        printState(stage);
        game.attack(player1, en1, 0);
        game.attack(en1, player1, 1);
        printState(stage);
        
        stage = StageData.getData().getNext();
        en1 = stage.getEnemyList().get(0);
        System.out.println("");
        
        printState(stage);
        game.attack(player1, en1, 0);
        game.attack(en1, player1, 1);
        game.attack(player1, en1, 0);
        game.attack(player1, en1, 1);
        printState(stage);
        
        stage = StageData.getData().getNext();
        en1 = stage.getEnemyList().get(0);
        System.out.println("");
        printState(stage);
        game.attack(player1, en1, 0);
        game.attack(player1, en1, 0);
        game.attack(player1, en1, 1);
        printState(stage);
    }
    
    private static void printState(Stage stage) {
        System.out.println(stage.getName());
        System.out.println(player1.getName() + " HP: " + player1.getHp());
        System.out.println(stage.getEnemyList().get(0).getName() + " HP: " + stage.getEnemyList().get(0).getHp() + "\n");
    }

}
