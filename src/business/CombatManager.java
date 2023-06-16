package business;

import persistence.MonsterDAO;

import java.util.*;

public class CombatManager {
    private List<Character> party;
    public void runAdventure(){
    }

    public HashMap<String,String[]> preparationPhaseAction(List<Character> party){
        HashMap<String,String[]> partyActions = new HashMap<>();
        for (int i = 0; i < party.size(); i++) {
            partyActions.put(party.get(i).getName(),party.get(i).preparationAction());
        }
        this.party=party;
        return partyActions;
    }

    public List<String[]> preparationPhaseInitiative(List<Monster> monsters){
        List<String[]> iniciative = new ArrayList<>();
        for (int i = 0; i < party.size(); i++) {
            String[] c= new String[2];
            c[0]=party.get(i).getName();
            c[1]= String.valueOf(party.get(i).rollInitiative());
            iniciative.add(c);
        }
        for (int i = 0; i < monsters.size(); i++) {
            String[] c= new String[2];
            c[0]=monsters.get(i).getName();
            c[1]= String.valueOf(monsters.get(i).rollInitiative());
            iniciative.add(c);
        }
        Collections.sort(iniciative,new Comparator<String[]> (){
            @Override
            public int compare(String[] o1, String[] o2) {
                if(o1[1]==o2[1]){
                    return 0;
                } else if (Integer.parseInt(o1[1]) > Integer.parseInt(o2[1])) {
                    return -1;
                }else {
                    return 1;
                }
            }
        });
        return iniciative;
    }


}
