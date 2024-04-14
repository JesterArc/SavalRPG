public class Item {
    private String name;
    private int level;
    public Target target;
    int slot;
    Item(String str, int lvl) {
        setName(str);
        setLevel(lvl);
    }
    void setName(String str) {
        this.name = str;
    }

    void setLevel(int lvl) {
        this.level = lvl;
    }

    String getName() {
        return this.name;
    }

    int getLevel() {
        return this.level;
    }
    public void use(Character target){

    }
    public void use(Monster target){

    }
    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Target getTarget() {
        return target;
    }
}
class Healing_Potion extends Item {
    Healing_Potion(int level) {
        super("Healing Potion", level);
        this.target = Target.ally;
    }
    public void use(Character target){
        target.restoreHealth(8 * this.getLevel(),false);
    }
}
class Chill_Pill extends Item{
    Chill_Pill(int level){
        super("Chill Pill", level);
        this.target = Target.ally;
    }
    public void use(Character target){
        target.removeTaunt(target);
        target.restoreHealth(2 * this.getLevel(),false);
    }
}
class Bomb extends Item{
    Bomb(int level){
        super("Bomb", level);
        this.target = Target.enemy;
    }
    public void use(Monster target){
        target.takeDamage(11 * this.getLevel(),false);
    }
}
class Holy_Water extends Item{
    Holy_Water(int level){
        super("Holy Water", level);
        this.target = Target.ally;
    }
    public void use(Character target){
        target.gainDivineCharges(2 * this.getLevel());
    }
}
class Plushie extends Item{
    Plushie(int level){
        super("Plushie", level);
        this.target = Target.ally;
    }
    public void use(Character target){
        int divine_charges = 1 + (int) (this.getLevel()/2.0);
        target.gainStacksOfProtection(divine_charges);
    }
}
class Stone extends Item{
    Stone(int level){
        // your inventory is empty, but there are always stones to toss
        super("Stone", level);
        this.target = Target.enemy;
    }
    public void use(Monster target){
        target.takeDamage(this.getLevel(),false);
    }
}
class Red_Powder extends Item {
    Red_Powder(int level) {
        super("Red Powder", level);
        this.target = Target.ally;
    }

    public void use(Character target) {
        target.gainTempAttack(this.getLevel() / 10.0, Math.max(target.temp_attack_bonus[1], 2));
    }
}
class Yellow_Powder extends Item {
    Yellow_Powder(int level) {
        super("Yellow Powder", level);
        this.target = Target.ally;
    }

    public void use(Character target) {
        target.gainTempCrit(5 * this.getLevel(), Math.max(target.temp_crit_bonus[1], 2));
    }
}
class Dog_Treat extends Item {
    Dog_Treat(int level){
        super("Dog Treat", level);
        this.target = Target.ally;
    }
    public void use(Monster target){
        target.gainTempAttack(this.getLevel() / 5.0 , 3);
        target.gainTempCrit(10 * this.getLevel(), 3);
        target.gainStacksOfProtection(this.getLevel() / 2);
    }
}