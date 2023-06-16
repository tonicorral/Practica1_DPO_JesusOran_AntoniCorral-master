package business;


import java.util.List;
import java.util.Random;

public class Monster {
    private String name;
    private String challenge;
    private String damageType;
    private int experience;
    private int hitPoints;
    private int initiative;
    private String damageDice;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public String getDamageDice() {
        return damageDice;
    }

    public void setDamageDice(String damageDice) {
        this.damageDice = damageDice;
    }

    public int rollInitiative(){
        Random random = new Random();
        return initiative+random.nextInt(1,13);
    }
    public int hitMonster(int damage){
        return hitPoints - damage;
    }

}

