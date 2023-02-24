package presentation;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;


    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome(){
        System.out.println("Welcome to Simple LSRPG.\n");
        System.out.println("Loading data...");
    }

    public void showMessageLoaded(){
        showMessage("\nData was successfully loaded.\n");
    }

    public void showMessageErrorLoaded(){
        showMessage("\nError Loading\n");
    }

    public void showMessageLeave(){
        showMessage("Tavern keeper: “Are you leaving already? See you soon, adventurer.”\n");
    }

    public void showMessageTryAgain(){
        showMessage("Try another number\n\n");
    }
    public void showMenu(){
        System.out.println("The tavern keeper looks at you and says:");
        System.out.println("“Welcome adventurer! How can I help you?”\n");
        System.out.println("1) Character creation");
        System.out.println("2) List characters");
        System.out.println("3) Create an adventure");
        System.out.println("4) Start an adventure");
        System.out.println("5) Exit\n");
    }

    public void showNotLoadedMonsters(){
        System.out.println("Error: The monsters.json file can’t be accessed.");
    }
    public void showNotLoadedCharacters(){
        System.out.println("Error: The characters.json file can’t be accessed.");
    }
    public void showNotLoadedAdventure(){
        System.out.println("Error: The adventure.json file can’t be accessed.");
    }

    public int askForInt (){
        return Integer.parseInt(scanner.nextLine());
    }

    public void showMessage(String message){
            System.out.print(message);
    }

    public String askForString(){
        return scanner.nextLine();
    }
    public int askForAnOption(int max, int min) {

        while(true){
            try {
                int option = Integer.parseInt(scanner.nextLine());
                if (option < min || option > max) {
                    showMessage("\nPlease input a number between " + min + " and " + max + "\n");
                    showMessage("Again: ");
                } else {
                    return option;
                }
            } catch (InputMismatchException e) {
                showMessage("\nPlease input a number\n");
            }
        }
    }

    public void showNumberedList(List<String> items) {
        showMessage("\n");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("\t" + (i + 1) + " - " + items.get(i));
        }
        showMessage("\n");
    }

    public void characterCreationIntro(){
        showMessage("Tavern keeper: “Oh, so you are new to this land.”\n");
        showMessage("“What’s your name?”\n\n");

        showMessage("-> Enter your name: ");

    }

    public void characterCreation(String name){
        showMessage("Tavern keeper: “Hello,"+ name +", be welcome.\n");
        showMessage("“And now, if I may break the fourth wall, who is your Player?”\n\n");
        showMessage("-> Enter the player’s name: ");
    }

    public void showCharacterCreation(int[] dados, int[] stats, String name){

        showMessage("\n“Great, let me get a closer look at you...”\n");
        showMessage("\nGenerating your stats...\n");

        showMessage("-Body: You rolled "+(dados[0]+ dados[1]) +" ("+dados[0]+" and "+ dados[1]+ ").");
        showMessage("-Mind: You rolled "+(dados[2]+ dados[3])+" ("+dados[2]+" and "+ dados[3]+ ").");
        showMessage("-Spirit: You rolled "+(dados[4]+ dados[5])+" ("+dados[4]+" and "+ dados[5]+ ").\n");

        showMessage("Your stats are:");
        showMessage("  -Body:"+ stats[0]+"");
        showMessage("  -Mind:"+ stats[1]+"");
        showMessage("  -Spirit:"+ stats[2]+"");
        showMessage("\nThe new character" +name + "has been created.");

    }

    public int showListCharacters(List<String> characterName){
        showMessage("\nYou watch as some adventurers get up from their chairs and approach you.\n");
        showNumberedList(characterName);

        showMessage("\n0. Back\n");
        showMessage("Who would you like to meet [0.."+ characterName.size()+"]: ");
        return (askForAnOption(characterName.size(),0)-1);
    }

    public void showCaracterInfo(String[] info){
        showMessage("* Name:  "+ info[0]);
        showMessage("\n* Player:  "+ info[1]);
        showMessage("\n*Class:  "+ info[2]);
        showMessage("\n*Level:  "+ info[3]);
        showMessage("\n*XP:  "+ info[4]);
        showMessage("\n*Body:  "+ info[5]);
        showMessage("\n*Mind:  "+ info[6]);
        showMessage("\n*Spirit:  "+ info[7]+"\n");
    }

    public int menuEncounters(int i,int numEncounter,List <String[]> lista){
        System.out.println("* Encounter "+(i+1)+"/"+numEncounter);
        System.out.println("*Monsters in encounter");
        if(lista.isEmpty()){
            System.out.println("#Empty");
        }
        else{
            for (int j = 0; j < lista.size(); j++) {
                System.out.println(+(j+1)+". "+lista.get(j)[0]+"(x"+lista.get(j)[1]+")");
            }
        }

        System.out.println("\n1. Add monster");
        System.out.println("2. Remove monster");
        System.out.println("3. Continue");
        System.out.println("\n-> Enter an option [1..3]: ");

        return askForAnOption(3,1);
    }

    public int[] addMonsterList(List <String[]> monsters,List <String[]> encounter){
        for (int i = 0; i < monsters.size(); i++) {
            System.out.println(+(i+1)+". "+monsters.get(i)[0]+ "("+monsters.get(i)[1]+")");
        }
        System.out.println("-> Choose a monster to add [1.."+monsters.size()+"] ");
        int monster=askForAnOption(monsters.size(),1)-1;

        int numMonster=1;
        if(!monsters.get(monster)[1].equals("Boss")){
            System.out.println("-> How many" + monsters.get(monster)[0] + "(s) do you want to add: ");
            numMonster = askForAnOption(999, 1);
        }else{
            for (int i = 0; i < encounter.size(); i++) {
                if(encounter.get(i)[2].equals("Boss")){
                    numMonster=0;
                    System.out.println("There is already a boss in the encounter.");
                }
            }
        }
        return new int[]{monster,numMonster};
    }

}
