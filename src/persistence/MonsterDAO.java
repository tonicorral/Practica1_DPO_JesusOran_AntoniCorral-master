package persistence;


import business.Monster;

import persistence.exceptions.PersistenceException;

import java.io.FileNotFoundException;
import java.util.List;

public interface MonsterDAO {
    List <Monster> loadMonsters() throws PersistenceException, FileNotFoundException;

    Monster[] getAll();
}
