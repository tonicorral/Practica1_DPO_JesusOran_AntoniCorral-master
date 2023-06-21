package business;

import java.util.*;

public class CombatManager {
    private List<Character> party;


    public void runAdventure(){
    }

    public List<String[]> encountersList(List<Monster> monsters){
        List<String[]> monstersList = new ArrayList<>();
        for (int i = 0; i < monsters.size(); i++) {
            String monsterName = monsters.get(i).getName();
            boolean found = false;

            for (int j = 0; j < monstersList.size(); j++) {
                if (monstersList.get(j)[0].equals(monsterName)) {
                    int count = Integer.parseInt(monstersList.get(j)[1]);
                    count++;
                    monstersList.get(j)[1] = String.valueOf(count);
                    found = true;
                    break;
                }
            }

            if (!found) {
                String[] monsterEntry = new String[2];
                monsterEntry[0] = monsterName;
                monsterEntry[1] = "1";
                monstersList.add(monsterEntry);
            }
        }
        return monstersList;
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
/*
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
    }*/

    public List<String[]> Attacks(List<String[]> iniciative, List<Monster> monsters, List<Integer> vida, List<Integer> monsterLife){
        List<String[]> attack = new ArrayList<>();

        for (int i = 0; i < iniciative.size(); i++) {
            String characterName = iniciative.get(i)[0];
            int monsterIndex = -1;
            int partyIndex = -1;

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
                if(monsterLife.get(monsterIndex) > 0){
                    String[] c = new String[6];
                    c[0] = monsters.get(monsterIndex).getName();
                    int rand = setRandomCharacter(party, vida);
                    c[1] = party.get(rand).getName();
                    c[2] = attackToCharacter(monsters, monsterIndex);
                    c[3] = monsters.get(monsterIndex).getDamageType();
                    c[4] = Integer.toString(rand);
                    c[5] = checkIfCharacterDead(vida.get(rand)- Integer.parseInt(c[2]));
                  //  c[6] = "0";
                    vida.set(rand, Integer.parseInt(c[5]));
                    System.out.println(i+".El monstruo " + c[0]+ " ataca a: " + c[1]);
                    System.out.println("ataque " +c[2] +" valor de vida: " +c[1] + vida.get(rand)+ "numero iniciativa " + rand);
                    attack.add(c);
                    if(checkAllCharacters(vida)){
                        System.out.println("TODOS MUERTOS");
                        break;
                    }
                }
            } else if (partyIndex != -1) {
                if(vida.get(partyIndex) > 0){
                    String[] c = new String[6];
                    c[0] = party.get(partyIndex).getName(); // atacante
                    for (int b = 0; b < monsters.size(); b++) {
                        if (monsterLife.get(b) > 0) {
                            c[1] = monsters.get(b).getName();
                            c[2] = attackToMonster(monsters, b, partyIndex);
                            c[3] = "Sword slash";
                            c[4] = "9";
                            c[5] = Integer.toString(monsterLife.get(b) - Integer.parseInt(c[2]));
                            if(Integer.parseInt(c[5]) < 0){
                                c[5] = "0";
                                System.out.println(c[1] + " is dead");
                            }
                            //c[6]= setXPDeadMonster(c[5],c[6], monsters, b);//xp
                            monsterLife.set(b,Integer.parseInt(c[5]));
                            System.out.println(i+".El personaje " + c[0]+ " ataca a: " + c[1]);
                            System.out.println("ataque " +c[2] + " valor de vida: " +c[1] + monsterLife.get(b)+ "numero iniciativa"+ b);
                            break;
                        }
                    }
                    attack.add(c);
                    if(checkAllMonstersDead(monsterLife)){
                        System.out.println("TODOS Monstruos MUERTOS");
                        break;
                    }
                }

            }
        }
        return attack;
    }


    private int setRandomCharacter(List<Character> party, List<Integer> vida){
        Random random = new Random();
        int rand;
        do{
            rand = random.nextInt(0, party.size());
        }while (vida.get(rand) == 0);

        return rand;
    }

    private String checkIfCharacterDead(int c){
        if(c < 0){
            return "0";
        }
        else{
            return Integer.toString(c);
        }
    }

    public int setXPDeadMonster(List<Integer> vidaMonster, int xp, List<Monster> monsters){
        for (int i = 0; i < monsters.size(); i++) {
            if(vidaMonster.get(i) == 0){
                xp = xp + monsters.get(i).getExperience();
            }
        }
        return xp;
    }

    public List<String[]> checkAtributos(int xp, List<Character> characters){
        List<String[]> levelUp = new ArrayList<>();
        int auxXp;
        for (int i = 0; i < characters.size(); i++) {
            Random random = new Random();
            String[] c = new String[3];
            auxXp = xp + characters.get(i).getXp();
            if(auxXp < 99){
                c[0] = "1";
            }
            if(auxXp > 99 && auxXp < 199){
                c[0] = "2";
            }
            if(auxXp > 199 && auxXp < 299){
                c[0] = "3";
            }
            if(auxXp > 299 && auxXp < 399){
                c[0] = "4";
            }
            if(auxXp > 399 && auxXp < 499){
                c[0] = "5";
            }
            if(auxXp > 499 && auxXp < 599){
                c[0] = "6";
            }
            if(auxXp > 599 && auxXp < 699){
                c[0] = "7";
            }
            if(auxXp > 699 && auxXp < 799){
                c[0] = "8";
            }
            if(auxXp > 799 && auxXp < 899){
                c[0] = "9";
            }
            if(auxXp > 899){
                c[0] = "9";
            }
            c[1] = Integer.toString(auxXp);
            c[2] = Integer.toString(random.nextInt(1,8)+ characters.get(i).getMind());
            levelUp.add(c);
        }
        return levelUp;
    }

    public boolean checkAllMonstersDead(List<Integer> monster) {
        for (int i = 0; i < monster.size(); i++) {
            if (monster.get(i) != 0) {
                return false;
            }
        }
        return true;
    }

    private String attackToMonster(List<Monster> monsters, int posicionMonster, int posicionChar){
        Random random = new Random();
        int damage = random.nextInt(1,6)+ party.get(posicionChar).getBody();
        monsters.get(posicionMonster).hitMonster(damage);
        return Integer.toString(damage);
    }


    private String attackToCharacter(List<Monster> monsters, int posicionMonster){
        Random random = new Random();
        int damage = random.nextInt(1,Integer.parseInt(monsters.get(posicionMonster).getDamageDice().substring(1)));
        return Integer.toString(damage);
    }

    public List<Integer> setMonsterLife(List<Monster> monsters){
        List<Integer> monsterLife = new ArrayList<>();

        for (int i = 0; i < monsters.size(); i++) {
            monsterLife.add(i, monsters.get(i).getHitPoints());
        }
        return monsterLife;
    }

    public List<Integer> setLife(List<Integer> vida, List<Integer> vidaMax, List<String[]> vidaUpdate){
        for (int i = 0; i < vida.size(); i++) {
            if(vida.get(i) != 0){
                if((vida.get(i) + Integer.parseInt(vidaUpdate.get(i)[2]) < vidaMax.get(i))){
                    vida.set(i, vida.get(i) + Integer.parseInt(vidaUpdate.get(i)[2]));
                }
                else{
                    vida.set(i, vidaMax.get(i));
                }
            }
            else{
                System.out.println(i + " is unconcios");
            }
        }
        return vida;
    }

    public boolean checkAllCharacters(List<Integer> vida){
        for (int i = 0; i < vida.size(); i++) {
            if(vida.get(i) != 0){
                return false;
            }
        }
        return true;
    }

    public boolean deadMonsters(List<Integer> monster){
        int j = 0;
        for (int i = 0; i < monster.size(); i++) {
            if (monster.get(i) > 0){
                j++;
            }
        }
        if(j > 0){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deadCharacters(List<Integer> vida){
        int j = 0;
        for (int i = 0; i < vida.size(); i++) {
            if(vida.get(i) > 0){
                j++;
            }
        }
        if(j > 0){
            return false;
        }
        else{
            return true;
        }
    }

    public List<Integer> updateVida(List<String[]> attacks,List<Integer> vida, List<Character> party){
        for (int i = 0; i < attacks.size(); i++) {
            if(!attacks.get(i)[4].equals("9")){
                for (int j = 0; j < party.size(); j++) {
                    if(party.get(j).getName().equals(attacks.get(i)[0])){
                        vida.add(j, Integer.parseInt(attacks.get(i)[5]));
                    }
                }
            }
        }
        return vida;
    }


    public List<Integer> updateVidaMonster(List<String[]> attacks,  List<Monster> monsters, List<Integer> monsterLife){
        for (int i = 0; i < attacks.size(); i++) {
            if(attacks.get(i)[4].equals("9")){
                for (int j = 0; j < monsters.size(); j++) {
                    if(monsters.get(j).getName().equals(attacks.get(i)[0])){
                        monsterLife.add(j,Integer.parseInt(attacks.get(i)[5]));
                    }
                }
            }
        }
        return monsterLife;
    }





}
