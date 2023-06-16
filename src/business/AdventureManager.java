package business;

import persistence.AdventureDAO;
import persistence.MonsterDAO;
import persistence.exceptions.PersistenceException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AdventureManager {
    private AdventureDAO adventureDAO;
    private MonsterDAO monsterDAO;

    public AdventureManager(AdventureDAO adventureDAO, MonsterDAO monsterDAO) {
        this.adventureDAO = adventureDAO;
        this.monsterDAO = monsterDAO;
    }


    public void saveAdventure(String adventureName,List <List<String[]>> encounters){
       // List <Adventure> adventures = adventureDAO;
        Combat[] combats = new Combat[encounters.size()];
        try {
            List <Monster> monsters = monsterDAO.loadMonsters();
            for (int i = 0; i < encounters.size(); i++) {
                List <Monster> monsterList = new ArrayList<>();
                for (int j = 0; j < encounters.get(i).size(); j++) {
                    for (int k = 0; k < Integer.parseInt(encounters.get(i).get(j)[1]); k++) {
                        String monsterName=encounters.get(i).get(j)[0];
                        for (int l = 0; l < monsters.size(); l++) {
                            if(monsterName.equals(monsters.get(l).getName())){
                                monsterList.add(monsters.get(l));
                                break;
                            }
                        }
                    }
                }
                combats[i]=new Combat(monsterList);
            }
            adventureDAO.saveAdventure(new Adventure(adventureName,combats));
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean checkAdventure(String nameAdventure){
       List<Adventure> adventures = null;
        try {
            adventures = adventureDAO.loadAdventure();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < adventures.size(); i++) {
            if(nameAdventure.equals(adventures.get(i).getAdventureName())){
                return true;
            }
        }
        for (int i = 0; i < adventures.size(); i++) {
            if(!nameAdventure.equals(adventures.get(i).getAdventureName())){
                return false;
            }
        }
        return true;
    }

    public List<String[]> getMonstersInfo() {
        List<String[]> names = new ArrayList<>();
        List<Monster> monsters = null;
        try {
            monsters = monsterDAO.loadMonsters();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Monster monster : monsters) {
            String[] m = new String[2];
            m[0]=monster.getName();
            m[1]=monster.getChallenge();
            names.add(m);
        }

        return names;
    }

    public List<String> getAllAdventureName(){
        try {
            List <Adventure> adventures = adventureDAO.loadAdventure();
            List<String> info = new ArrayList<>();

            for (int i = 0; i < adventures.size(); i++) {
                info.add(adventures.get(i).getAdventureName());
            }
            return info;
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }
    public String getAdventureName(int number){
        try {
            List <Adventure> adventures = adventureDAO.loadAdventure();
            String info = adventures.get(number).getAdventureName();
            return info;
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumEncounters(int numAdventure){
        List <Adventure> adventures = null;
        try {
            adventures = adventureDAO.loadAdventure();
            return adventures.get(numAdventure).getNumEncounter();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String[]> getCombatMonsters(int numAdv, int numComb){
        List <Adventure> adventures = null;
        try {
            adventures = adventureDAO.loadAdventure();
            return adventures.get(numAdv).getCombats()[numComb].getMonsterList();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSpirit(List<String> party){
        try {
            List <Adventure> adventures = adventureDAO.loadAdventure();
            try {
                List<Monster> monsters = monsterDAO.loadMonsters();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Monster> getMonsterList(int numAdv, int numComb){
        List <Adventure> adventures = null;
        try {
            adventures = adventureDAO.loadAdventure();
            return adventures.get(numAdv).getCombats()[numComb].getMonsters();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }

}

