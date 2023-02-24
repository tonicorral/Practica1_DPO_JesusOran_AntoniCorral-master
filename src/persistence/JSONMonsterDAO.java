package persistence;

import business.Monster;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import persistence.exceptions.PersistenceException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONMonsterDAO implements MonsterDAO {
    private Gson gson;

    public JSONMonsterDAO() {
        this.gson = new Gson();
    }

    public List <Monster> loadMonsters() throws  PersistenceException {
        try{

        JsonArray object = JsonParser.parseReader(new FileReader("data/monsters.json")).getAsJsonArray();
        Monster[] monster = gson.fromJson(object, Monster[].class);
        List<Monster> monsters = new ArrayList<>(List.of(monster));
       monsters.addAll(List.of(monster));

        return monsters;

        } catch (FileNotFoundException e) {
            throw new PersistenceException("Error: Couldn't open the Monsters file. Closing the program",e);
        }
    }

    @Override
    public Monster[] getAll() {
        return new Monster[0];
    }
}



