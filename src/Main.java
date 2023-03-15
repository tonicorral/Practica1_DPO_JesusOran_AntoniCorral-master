import business.AdventureManager;
import business.CharacterManager;
import business.CombatManager;
import business.Monster;
import persistence.*;
import persistence.exceptions.PersistenceException;
import presentation.Controller;
import presentation.Menu;

import java.io.FileNotFoundException;
import java.util.List;

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