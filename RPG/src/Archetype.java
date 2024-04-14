public class Archetype {
    private String name;
    private int BaseHP = 10;
    private int crit_chance_mod = 1;
    public int getCrit_chance_mod() {
        return crit_chance_mod;
    }
    public void setCrit_chance_mod(int crit_chance_mod) {
        this.crit_chance_mod = crit_chance_mod;
    }
    public int getBaseHP() {
        return BaseHP;
    }
    public void setBaseHP(int baseHP) {
        this.BaseHP = baseHP;
    }
    Archetype(String name){
        setName(name);
    }
    void setName(String name){
        this.name = name;
    }
    String getName(){
        return this.name;
    }
}

class Berserk extends Archetype {
    Berserk(){
        super("Berserk");
        setBaseHP(15);
    }

}
class Archer extends Archetype {
    Archer() {
        super("Archer");
        setCrit_chance_mod(2);
    }
}
class Templar extends Archetype {
    Templar(){
        super("Templar");
        setBaseHP(20);
    }
}
class Mage extends Archetype {
    Mage(){
        super("Mage");
    }
}
class Cleric extends Archetype {
    Cleric(){
        super("Cleric");
    }
}