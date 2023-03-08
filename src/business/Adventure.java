package business;

public class Adventure {
    private String adventureName;
    private Combat[] combats;


    public Adventure(String adventureName, Combat[] combats) {
        this.adventureName = adventureName;
        this.combats = combats;
    }


    public Combat[] getCombats() {
        return combats;
    }

    public int getNumEncounter(){
        return combats.length;
    }

    public String getAdventureName() {
        return adventureName;
    }

    public void setAdventureName(String adventureName) {
        this.adventureName = adventureName;
    }

    public void setCombats(Combat[] combats) {
        this.combats = combats;
    }
}
