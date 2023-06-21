package presentation;
import business.*;
import business.Character;

import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    private Menu menu;
    private CharacterManager cm;
    private CombatManager ctm;

    private AdventureManager am;

    private List<Integer> vidaMax;

    private List<Integer> vida;

    private List<Integer> vidaMonster;

    private int xp;

    public Controller(Menu menu, CharacterManager cm, AdventureManager am, CombatManager ctm) {
        this.menu = menu;
        this.cm = cm;
        this.am = am;
        this.ctm = ctm;
        vida = new ArrayList<>();
        vidaMax = new ArrayList<>();
        vidaMonster = new ArrayList<>();
    }

    public void run() {
        int option;
        boolean stop;

        menu.showWelcome();
        if (true) {
            am.getMonstersInfo();
            menu.showMessageLoaded();
            do {
                if(cm.countCharacter()>3){
                    menu.showMenuOK();
                }else{
                    menu.showMenuBAD(cm.countCharacter());
                }
                menu.showMessage("Your answer: ");
                option = menu.askForAnOption(5, 1);

                stop = optionRun(option);
            } while (stop);

        } else {
            menu.showMessageErrorLoaded();
        }
    }


    private boolean optionRun(int option){
        switch (option){
            case 1:
                return characterCreation();
            case 2:
                return listCharacters();
            case 3:
                return createAdventure();
            case 4:
                return startAdventure();
            case 5:
                menu.showMessageLeave();
                return false;
            default:
                menu.showMessageTryAgain();
                return true;

        }
    }

    private boolean characterCreation(){
        String name;
        String playerName;
        int level;
        int[] dados;
        int[] stats;

        menu.characterCreationIntro();
        name = menu.askForString();

        if(cm.checkName(name)) {
            System.out.println("The name was already taken");
            return true;
        }
        if(!cm.checkCorrectName(name)){
            System.out.println("The name is incorrect");
            return true;
        }
        name = cm.buildName(name);
        menu.characterCreation(name);
        playerName = menu.askForString();
        menu.showMessage(playerName);
        menu.showMessage("\nTavern keeper: “I see, I see...”");
        menu.showMessage("\n“Now, are you an experienced adventurer?”\n");
        menu.showMessage("\n-> Enter the character’s level [1..10]: ");
        level = menu.askForAnOption(10, 1);

        menu.showMessage("\nTavern keeper: “Oh, so you are level "+level+"!”");

        dados = cm.createCharacter(name,playerName,level);
        stats = cm.getStats(name);
        menu.showCharacterCreation(dados,stats, name);


        return true;
    }

    private boolean listCharacters(){
        int number = 0;
        int character;
        boolean out = false;
        List<String> characterName;
        menu.showMessage("\nTavern keeper: “Lads! They want to see you!”");
        menu.showMessage("“Who piques your interest?”\n");
        menu.showMessage("\n-> Enter the name of the Player to filter: ");
        String playerName = menu.askForString();
        characterName = cm.showCharacters(playerName);
        if(characterName.size()==0){
            menu.showMessage("Nobody approaches you\n");
            return true;
        }
        number = menu.showListCharacters(characterName);
        if(number == -1){
            return true;
        }
        menu.showCaracterInfo(cm.showInformation(characterName.get(number)));
        do{

            menu.showMessage("\n[Enter name to delete, or press enter to cancel]\n");
            menu.showMessage("Do you want to delete "+characterName.get(number)+"?");

            String input = menu.askForString();

            if(input.equalsIgnoreCase(characterName.get(number))){
                cm.eliminateCharacter(characterName.get(number));
                menu.showMessage("\n\nTavern keeper: “I’m sorry kiddo, but you have to leave.”\n");
                menu.showMessage("Character "+characterName.get(number)+" left the Guild.");
                break;
            }
            if(input.isEmpty()){
                break;
            }
            else{
                System.out.printf("Do you want to try again?");
            }
        }while (!out);


        return true;
    }


    private boolean createAdventure(){
        String adventure;

        menu.showMessage("Tavern keeper: “Planning an adventure? Good luck with that!”\n");
        menu.showMessage("-> Name your adventure: ");
        adventure = menu.askForString();
        if(am.checkAdventure(adventure)){
            menu.showMessageErrorLoaded();
            return true;
        }
        menu.showMessage("Tavern keeper: “You plan to undertake "+adventure+", really?”\n" +
                "“How long will that take?””\n");
        menu.showMessage("-> How many encounters do you want [1..4]: ");
        int encounters = menu.askForAnOption(4,1);
        menu.showMessage("Tavern keeper: "+encounters+" encounters? That is too much for me...”\n");

        List <List<String[]>> encounterlist = new ArrayList<>();
        List <String[]> monsterInfo=am.getMonstersInfo();
        for (int i = 0; i < encounters; i++) {
            List <String[]> encounter = new ArrayList<>();
            boolean next=false;
            do{
                switch (menu.menuEncounters(i,encounters,encounter)) {
                    case 1:
                        int[] monster=menu.addMonsterList(monsterInfo,encounter);
                        boolean f=false;
                        if(monster[1]!=0){
                            for (int j = 0; j < encounter.size(); j++) {
                                if(encounter.get(j)[0].equals(monsterInfo.get(monster[0])[0])){
                                    String[] m=encounter.get(j);
                                    m[1]= String.valueOf(Integer.parseInt(m[1])+monster[1]);
                                    encounter.set(j,m);
                                    f=true;
                                }
                            }
                            if(!f){
                                encounter.add(new String[]{monsterInfo.get(monster[0])[0], String.valueOf(monster[1]),monsterInfo.get(monster[0])[1]});
                              //  vidaMonster.add(monsterInfo.ge);
                            }
                        }
                        break;
                    case 2:
                        menu.showMessage("-> Which monster do you want to delete: ");
                        int numDelete= menu.askForAnOption(encounter.size(),1)-1;
                        encounter.remove(numDelete);
                        break;
                    case 3:
                        if(!encounter.isEmpty()){
                            encounterlist.add(encounter);
                            next = true;
                        }
                        break;
                }
            }while(!next);
        }
        am.saveAdventure(adventure,encounterlist);
        return true;
    }

    private boolean startAdventure() {
        int number;
        int j = 1;
        menu.menuAdventures();
        // HAY QUE MIRAR ANTES QUE SI HAY MENOS DE 3 NO PUEDE MOSRAR ESTO
        List<String> info = am.getAllAdventureName();
        for (int i = 0; i < am.getAllAdventureName().size(); i++) {
            menu.showName(info.get(i), i);
        }
        number  = menu.showNumberAdventure();
        int numCharacters = menu.showNameAdv(am.getAdventureName(number));
        List<String> character = cm.showCharacters("");
        List<String> party = new ArrayList<>();
        List<Character> partyGroup;

        for (int i = 0; i < numCharacters; i++) {
            int selection = menu.selectPartyAdv(numCharacters, i, party, character);
            party.add(character.get(selection));
            partyGroup = cm.getPartyName(party);
            System.out.println(partyGroup.get(i).getName());
            int life = (10 + partyGroup.get(i).getBody())*partyGroup.get(i).getLevel();
            vida.add(life);
            vidaMax.add(life);
            character.remove(selection);
        }

        menu.yourParty(numCharacters, party, info.get(number)); //seleccion de jugadores
        int encounters = am.getNumEncounters(number);

        for (int i = 0; i < encounters; i++) {
            List<String[]> monstersList = ctm.encountersList(am.getMonsterList(number, i));
            List<Integer> monsterLife = ctm.setMonsterLife(am.getMonsterList(number, i));
            menu.printMonstersCombat(monstersList, i);
            HashMap<String,String[]> partyActions = ctm.preparationPhaseAction(cm.getPartyName(party));
            List<String[]> initiative= ctm.preparationPhaseInitiative(am.getMonsterList(number,i));
            menu.preparationStage(party,initiative,partyActions);
            menu.printCombatStage();

            do{
                menu.printPartyRound(party, vidaMax,vida, j);
                List<String[]> attacks = ctm.Attacks(initiative,am.getMonsterList(number, i), vida, monsterLife);
                vida =  ctm.updateVida(attacks, vida, cm.getPartyName(party));
                monsterLife = ctm.updateVidaMonster(attacks, am.getMonsterList(number, i), monsterLife);
                menu.printBattle(attacks);
                j++;

                if (ctm.checkAllMonstersDead(monsterLife)){
                    System.out.println("MONSTERS DEAD");
                    xp = ctm.setXPDeadMonster(monsterLife,xp, am.getMonsterList(number, i));
                    System.out.println("print xp"+ xp);

                }
               // System.out.println(vida);!ctm.deadMonsters(am.getMonsterList(number, i))
                // ||!ctm.deadCharacters(vida)
            }while (!ctm.deadMonsters(monsterLife));
            //menu.printPartyRound(party,vidaMax,1);
            j = 1;
            List<String[]> atributos = ctm.checkAtributos(xp, cm.getPartyName(party));
            List<String> level = cm.setNewLevel(cm.getPartyName(party), atributos);

            menu.printShortRest(party, atributos, level, xp, vida);
            vida = ctm.setLife(vida, vidaMax, atributos);
            xp = 0;

            if(ctm.checkAllCharacters(vida)){
                System.out.println("ALL DEAD");
                menu.printFinalMessage(false, am.getAdventureName(number));
                break;
            } else if (ctm.checkAllMonstersDead(monsterLife) && (i+1) == encounters){
                System.out.println("ALL MONSTERS DEAD");
                System.out.println();
                menu.printFinalMessage(true, am.getAdventureName(number));
            }
        }


        return true;


    }
}

