package persistence;

import business.Adventure;
import business.Character;
import business.Monster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import persistence.exceptions.PersistenceException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JSONAdventureDAO implements AdventureDAO {
    private Gson gson;

    public JSONAdventureDAO(){
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }


    @Override
    public void saveAdventure(Adventure adventure) {
        try {
            List<Adventure> list = loadAdventure();
            list.add(adventure);
            Writer file = new FileWriter("data/adventures.json");
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
    public List<Adventure> loadAdventure() throws PersistenceException {
        try{
            FileReader fr = new FileReader("data/adventures.json");
            BufferedReader bf = new BufferedReader(fr);
            String pls = bf.readLine();
            if(pls == null ||pls.equals("null") || pls.equals("")){
                return new ArrayList<>();
            }
            fr = new FileReader("data/adventures.json");
            JsonArray object = JsonParser.parseReader(fr).getAsJsonArray();
            Adventure[] adventure = gson.fromJson(object, Adventure[].class);
            List<Adventure> adventures = new ArrayList<>(List.of(adventure));


            return adventures;
        } catch (FileNotFoundException e) {
            FileWriter file = null;
            try {
                file = new FileWriter("data/adventures.json");
                file.write("");
                file.flush();
                file.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new PersistenceException("Error: Couldn't open the Adventure file.\nCreating a new adventures.json \nClosing the program",e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}

