package view;
/**
 * Interface for the LoadGame
 */
public interface LoadSaveGUI {
    
    /**
     * Initializes the LoadGameGUI with the savegames array
     * @param existingSave array containing all the names of the savegames
     */
    void initialize(String[] existingSave);
}
