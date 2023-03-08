package business;

import persistence.CharacterDAO;
import persistence.exceptions.PersistenceException;

import java.util.ArrayList;
import java.util.List;

public class CharacterManager {

    private CharacterDAO characterDAO;

    public CharacterManager(CharacterDAO characterDAO) {
        this.characterDAO = characterDAO;
    }

    public List<String> showCharacters(String playerName){
        List<String> characterName = new ArrayList<>();
        try {
            List<Character> characters = characterDAO.loadCharacters();
            if(playerName.equals("")){
                for (int i = 0; i < characters.size(); i++) {
                        characterName.add(characters.get(i).getName());

                }
            }
            else {
                for (int i = 0; i < characters.size(); i++) {
                    if (characters.get(i).getPlayer().contains(playerName)) {
                        characterName.add(characters.get(i).getName());
                    }
                }
            }
            return characterName;
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] showInformation(String name){
        try {
            String[] info = new String[8];
            List<Character> characters = characterDAO.loadCharacters();
            for (int i = 0; i < characters.size(); i++) {
                if(characters.get(i).getName().equals(name)){
                    info[0] = characters.get(i).getName();
                    info[1] = characters.get(i).getPlayer();
                    info[2] = characters.get(i).getClase();
                    info[3] = String.valueOf(characters.get(i).getLevel());
                    info[4] = String.valueOf(characters.get(i).getXp());
                    info[5] = String.valueOf(characters.get(i).getBody());
                    info[6] = String.valueOf(characters.get(i).getMind());
                    info[7] = String.valueOf(characters.get(i).getSpirit());
                }
            }
            return info;


        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] createCharacter(String name, String playerName, int Level) {
        Character character= new Character(name,playerName,Level);
        int[] dados= character.tiradaDados();
        characterDAO.saveCharacter(character);
        return dados;

    }

    public int[] getStats(String name){
        int[] stats = new int[3];
        try {
            List<Character> characters = characterDAO.loadCharacters();

            for (int i = 0; i < characters.size(); i++) {
                if(characters.get(i).getName().equals(name)){
                    stats[0] = characters.get(i).getBody();
                    stats[1] = characters.get(i).getMind();
                    stats[2] = characters.get(i).getSpirit();
                }
            }
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
        return stats;
    }

    public boolean checkName(String name){
        try {
            List<Character> characters = characterDAO.loadCharacters();

            for (int i = 0; i < characters.size(); i++) {
                if(characters.get(i).getName().equalsIgnoreCase(name)){
                    return true;
                }
            }
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean checkCorrectName(String name){
        char[] nameChar = name.toCharArray();

        for (char c:nameChar) {
            if(!(c == ' ' || java.lang.Character.isLetter(c))){ //si no es ni espacio ni letter retrurn false
                return false;
            }
        }
        return true;
    }

    public String buildName(String name){
        String[] nameSeparated;
        String finalName = "";
        String nameLower = name.toLowerCase();
        nameSeparated = nameLower.split(" ");
        for (String s: nameSeparated){
            String f = s.substring(1);
            finalName += java.lang.Character.toUpperCase(s.charAt(0))+f+" ";
        }

        return finalName.substring(0, finalName.length()-1);
    }

    public void eliminateCharacter(String name){
        try {
            List<Character> characters = characterDAO.loadCharacters();
            //eliminar
            int c=-1;

            for (int i = 0; i < characters.size(); i++) {
                if(characters.get(i).getName().equals(name)){
                    c=i;
                }
            }
            characters.remove(c);
            characterDAO.saveCharacter(characters);
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public int countCharacter(){
        try {
            List<Character> characters = characterDAO.loadCharacters();
            return characters.size();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Character> getParty(List<String> characterName){
        try {
            List<Character> characters = characterDAO.loadCharacters();
            List<Character> party = new ArrayList<>();
            for (int i = 0; i < characterName.size(); i++) {
                for (int j = 0; j < characters.size(); j++) {
                    if( characters.get(j).equals(characterName.get(i))){
                        party.add(characters.get(j));
                        break;
                    }
                }
            }
            return party;
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }


}
