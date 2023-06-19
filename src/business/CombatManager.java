package business;

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

    public int getCharacterHitPoints(List<Monster> monsters, List<String[]> attacks, List<Integer> vida, int k){
        for (int i = 0; i < attacks.size(); i++) {
            String characterName = attacks.get(i)[0];
            int monsterIndex = -1;

            for (int j = 0; j < monsters.size(); j++) {
                if (monsters.get(j).getName().equals(characterName)) {
                    monsterIndex = j;
                    break;
                }
            }
            if (monsterIndex != -1) {
                System.out.println("vida:" + vida.get(k) + "su numero" + k + " resta el monstruo"+ monsters.get(monsterIndex).getName() + " resta el valor"+ Integer.parseInt(attacks.get(i)[2]));
                vida.add(k, vida.get(k) - Integer.parseInt(attacks.get(i)[2]));
            }
        }
        System.out.println(vida.get(k));
       return vida.get(k);
    }

    public List<String[]> Attacks(List<String[]> iniciative, List<Monster> monsters, List<Integer> vida){
        List<String[]> attack = new ArrayList<>();
        for (int i = 0; i < iniciative.size(); i++) {
            String characterName = iniciative.get(i)[0];
            int monsterIndex = -1;
            int partyIndex = -1;

            // Buscar el índice del monstruo y del personaje en las respectivas listas
            for (int j = 0; j < monsters.size(); j++) {
                if (monsters.get(j).getName().equals(characterName)) {
                    monsterIndex = j;
                    break;
                }
            }
            for (int k = 0; k < party.size(); k++) {
                if (party.get(k).getName().equals(characterName)) {
                    partyIndex = k;
                    break;
                }
            }
            if (monsterIndex != -1) {
                Random random = new Random();
                String[] c = new String[5];
                c[0] = monsters.get(monsterIndex).getName();
                int rand = random.nextInt(0, party.size());
                c[1] = party.get(rand).getName();
                c[2] = attackToCharacter(monsters, monsterIndex);
                c[3] = monsters.get(monsterIndex).getDamageType();
                c[4] = Integer.toString(rand);
                attack.add(c);
            } else if (partyIndex != -1) {
                String[] c = new String[5];
                c[0] = party.get(partyIndex).getName(); // atacante
                for (int b = 0; b < monsters.size(); b++) {
                    if (monsters.get(b).getHitPoints() != 0) {
                        c[1] = monsters.get(b).getName();
                        c[2] = attackToMonster(monsters, b, partyIndex);
                        c[3] = "Sword slash";
                        c[4] = "9";
                        break;
                    }
                }
                attack.add(c);
            }
        }
        return attack;
    }

    public String attackToMonster(List<Monster> monsters, int posicionMonster, int posicionChar){
        Random random = new Random();
        int damage = random.nextInt(1,6)+ party.get(posicionChar).getBody();
        monsters.get(posicionMonster).hitMonster(damage);
        return Integer.toString(damage);
    }

    public String attackToCharacter(List<Monster> monsters, int posicionMonster){
        Random random = new Random();
        int damage = random.nextInt(1,Integer.parseInt(monsters.get(posicionMonster).getDamageDice().substring(1)));
        //party.get(posicionChar).setVida(party.get(posicionChar).getVida()- damage);
        //System.out.println(party.get(posicionChar).getVida());


        return Integer.toString(damage);
    }


    public List<Integer> selectRandCharacters(List<String[]> attacks){
        List<Integer> rand = new ArrayList<>();
        for (int i = 0; i < attacks.size(); i++) {
            if(attacks.get(i)[4].equals("0") || attacks.get(i)[4].equals("1") || attacks.get(i)[4].equals("2")){
                rand.add(Integer.parseInt(attacks.get(i)[4]));
            }
        }
        return rand;
    }



    public boolean deadMonsters(List<Monster> monsters){
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getHitPoints() != 0){
                return false;
            }
        }
        return true;
    }



    /*
    public boolean deadCharacters(List<Character> party){
        for (int i = 0; i < party.size(); i++) {
            if(party.get(i).getVida() != 0){
                return false;
            }
        }
        return true;
    }
*/





}
