package business;

public class Adventure {
    private String adventureName;
    private Combat[] combats;

    public Adventure(String adventureName, Combat[] combats) {
        this.adventureName = adventureName;
        this.combats = combats;
    }
}
