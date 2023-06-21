package persistence;

import business.Adventure;
import business.Monster;
import persistence.exceptions.PersistenceException;

import java.util.List;

public interface AdventureDAO {
    void saveAdventure(Adventure adventure);
    List <Adventure> loadAdventure() throws PersistenceException;


}
