import business.AdventureManager;
import business.CharacterManager;
import business.CombatManager;
import persistence.*;
import presentation.Controller;
import presentation.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        try{
            AdventureDAO adventureDAO = new JSONAdventureDAO();
            CharacterDAO characterDAO = new JSONCharacterDAO();
            MonsterDAO monsterDAO = new JSONMonsterDAO();

            CharacterManager cm = new CharacterManager(characterDAO);
            AdventureManager am = new AdventureManager(adventureDAO,monsterDAO);
            CombatManager ctm = new CombatManager();

            Controller controller = new Controller(menu, cm, am,ctm);
            controller.run();

        } catch (Exception e){
            menu.showMessage(e.getMessage());
        }

    }
}