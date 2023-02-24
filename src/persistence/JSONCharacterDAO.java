package persistence;

import business.Character;
import business.Monster;
import com.google.gson.*;
import persistence.exceptions.PersistenceException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JSONCharacterDAO implements CharacterDAO{

    private Gson gson;

    public JSONCharacterDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void saveCharacter(Character character) {
        try {
            List<Character> list = loadCharacters();
            list.add(character);
            Writer file = new FileWriter("data/characters.json",false);
            file.write(this.gson.toJson(list));
            file.flush();
            file.close();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Character> loadCharacters() throws PersistenceException {
        try{

            JsonArray object = JsonParser.parseReader(new FileReader("data/characters.json")).getAsJsonArray();
            Character[] character = gson.fromJson(object, Character[].class);
            List<Character> characters = new ArrayList<>(List.of(character));

            return characters;
        } catch (FileNotFoundException e) {
           throw new PersistenceException("Error: Couldn't open the Characters file. Closing the program",e);
        }
    }

    @Override
    public void saveCharacter(List <Character> characterList) {
        try {
            Writer file = new FileWriter("data/characters.json",false);
            file.write(this.gson.toJson(characterList));
            file.flush();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
