package persistence;

import business.Character;
import business.Monster;
import persistence.exceptions.PersistenceException;

import java.util.List;

public interface CharacterDAO {

    void saveCharacter(Character character);
    List<Character> loadCharacters() throws PersistenceException;
    void saveCharacter (List <Character> characterList);

    void updateCharacter(Character character);
}
