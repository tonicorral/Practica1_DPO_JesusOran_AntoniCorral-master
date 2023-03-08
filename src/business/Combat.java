package business;

import java.util.ArrayList;
import java.util.List;

public class Combat {
    private List <Monster> monsters;

    public Combat(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public List<String[]> getMonsterList(){
        List<String[]> list = new ArrayList<>();

        for (int i = 0; i < monsters.size(); i++) {
            if(!list.isEmpty()) {
                for (int j = 0; j < list.size(); j++) {
                    if(monsters.get(i).getName().equals(list.get(j)[1])){
                        list.get(j)[0] = String.valueOf (Integer.parseInt(list.get(j)[0]) +1);
                        break;
                    }
                    else{
                        String[] m = {"1", monsters.get(i).getName()};
                        list.add(m);
                    }
                }
            }
            else{
                String[] m = {"1", monsters.get(i).getName()};
                list.add(m);
            }
        }
        return list;
    }
}
