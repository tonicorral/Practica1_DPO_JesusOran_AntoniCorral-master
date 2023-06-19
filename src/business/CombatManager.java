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


    public List<Integer> getMaxVida(){
        List<Integer> maxVida = new ArrayList<>();

        for(int j = 0; j < party.size(); j++) {
            party.get(j).setVidaMax(party.get(j).vida());
            maxVida.add(j ,party.get(j).getVidaMax());
        }
        return maxVida;
    }
    public List<Integer> getCharacterHitPoints(List<Character> characters){
        List<Integer> vida = new ArrayList<>();
        for(int j = 0; j < party.size(); j++) {
            party.get(j).setVida(characters.get(j).getVida());
            vida.add(j ,party.get(j).getVida());
        }

       return vida;
    }


    public List<String[]> Attacks(List<Monster> monsters){
        List<String[]> attack = new ArrayList<>();
        for (int i = 0; i < party.size(); i++) {
            String[] c= new String[4];
            c[0]=party.get(i).getName(); //atacante
            for (int j = 0; j < monsters.size(); j++) {
                if(monsters.get(j).getHitPoints() != 0){
                    c[1] = monsters.get(j).getName();
                    c[2] = attackToMonster(monsters, j, i);
                    c[3] = "Sword slash";
                    break;
                }
            }
            attack.add(c);
        }
        for (int i = 0; i < monsters.size(); i++) {
            Random random = new Random();
            String[] c= new String[4];
            c[0]=monsters.get(i).getName();
            int rand = random.nextInt(1, party.size());
            c[1] = party.get(rand).getName();
            c[2] = attackToCharacter(monsters,i, rand);
            c[3] = monsters.get(i).getDamageType();
            attack.add(c);
        }
/*
        Collections.sort(attack,new Comparator<String[]> (){
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
        });*/
        return attack;
    }

    public String attackToMonster(List<Monster> monsters, int posicionMonster, int posicionChar){
        Random random = new Random();
        int damage = random.nextInt(1,6)+ party.get(posicionChar).getBody();
        monsters.get(posicionMonster).hitMonster(damage);
        return Integer.toString(damage);
    }

    public String attackToCharacter(List<Monster> monsters, int posicionMonster, int posicionChar){
        Random random = new Random();
        int damage = random.nextInt(1,Integer.parseInt(monsters.get(posicionMonster).getDamageDice().substring(1)));
        party.get(posicionChar).setVida(party.get(posicionChar).getVida()- damage);
        System.out.println(party.get(posicionChar).getVida());


        return Integer.toString(damage);
    }


    public boolean deadMonsters(List<Monster> monsters){
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getHitPoints() != 0){
                return false;
            }
        }
        return true;
    }

    public boolean deadCharacters(List<Character> party){
        for (int i = 0; i < party.size(); i++) {
            if(party.get(i).getVida() != 0){
                return false;
            }
        }
        return true;
    }






}
