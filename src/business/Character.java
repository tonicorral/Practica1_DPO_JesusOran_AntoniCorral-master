package business;

import java.util.Random;

public class Character {
    private String name;
    private String player;
    private String clase;

    private int level;

    private int body ;
    private int mind ;
    private int spirit ;
    private int xp;


    public Character(String name, String player, int Level) {
        this.name = name;
        this.player = player;
        this.xp = LevelToXp(Level);
        this.clase = "Adventurer";
        this.level = Level;
    }
    public String getName() {
        return name;
    }

    public int[] tiradaDados(){
        int b,m,s;
        int[] dados = new int[6];
        Random random = new Random();

        for(int i=0;i<6;i++){
            dados[i]= random.nextInt(1,7);
        }

        b = dados[0]+ dados[1];
        m = dados[2]+dados[3];
        s = dados[4]+dados[5];

        this.mind=getStat(m);
        this.body=getStat(b);
        this.spirit=getStat(s);

        return dados;
    }

    private int getStat(int value){
        int stat;

        switch (value){
            case 2:
                stat=-1;
                break;
            case 3,4,5:
                stat=0;
                break;
            case 6,7,8,9:
                stat=1;
                break;
            case 10,11:
                stat=2;
                break;
            case 12:
                stat=3;
                break;
            default:
                stat=-1;

        }
        return stat;
    }

    private int LevelToXp(int Level){
        return Level*100-99;
    }
    public int getLevel(){return level;}
    public void setLevel(int level){ this.level = level;}

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public int getMind() {
        return mind;
    }

    public void setMind(int mind) {
        this.mind = mind;
    }

    public int getSpirit() {
        return spirit;
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
