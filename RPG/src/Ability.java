public class Ability {
    String name;
    int rank;
    int cooldown;
    int slot = 0;
    Target target = Target.enemy;
    public int getSlot(){
        return slot+1;
    }
    public void setSlot(int slot) {
        this.slot = slot;
    }
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    public int getCooldown() {
        return cooldown;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getRank() {
        return rank;
    }
    public Target getTarget() {
        return target;
    }
    public void setTarget(Target target) {
        this.target = target;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public String show(Character pc){
        return this.getName();
    }
    Ability(String name, int cooldown) {
        setName(name);
        setRank(1);
        setCooldown(cooldown);
    }
    public void use(Character user){
    }
    public void use(Character user, Monster target){
    }
    public void use(Character user, Monster[] targets){}
    public void use(Character user, Character[] targets){}
    public void use(Character user, Character target){

    }
    public void use(Item item, Monster target){

    }
    public void use(Item item, Character target){

    }
    public void use(Monster user, Character target){

    }
    public void use(Monster user){

    }
    public void use(Monster user, Monster[] targets){

    }
    public void use(Monster user, Character[] targets){

    }
}
class Attack_B extends Ability {
    public Attack_B() {
        super("Attack", 0);
    }

    public void use(Character user, Monster target){
        int base_dmg = 1 + this.getRank()* 2;
        boolean crit = Command.check_for_crit(0,user.crit_chance + user.temp_crit_bonus[0]);
        int damage_val = (int)((base_dmg + user.divine_charges) * (1 + user.temp_attack_bonus[0]) * (crit ? user.getCrit_mod() : 1.0));
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val, crit);
    }
}
class Attack_A extends Ability {
    public Attack_A() {
        super("Attack", 0);
    }
    public void use(Character user, Monster target) {
        int base_dmg = 1 + this.getRank() * 2;
        boolean crit = Command.check_for_crit(0,user.crit_chance + user.temp_crit_bonus[0]);
        int damage_val = (int)((base_dmg + user.divine_charges) * (1 + user.temp_attack_bonus[0]) * (crit ? user.getCrit_mod() : 1.0));
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val, crit);
    }
}
class Attack_T extends Ability {
    public Attack_T() {
        super("Attack", 0);
    }
    public void use(Character user, Monster target) {
        int base_dmg = (user.getAttribute(2) / 5) + this.getRank();
        boolean crit = Command.check_for_crit(0,user.crit_chance + user.temp_crit_bonus[0]);
        int damage_val = (int)((base_dmg + user.divine_charges) * (1 + user.temp_attack_bonus[0]) * (crit ? user.getCrit_mod() : 1.0));
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val, crit);
    }
}
class Attack_M extends Ability {
    public Attack_M() {
        super("Attack", 0);
    }

    public void use(Character user, Monster target) {
        int base_damage = this.getRank() - 1;
        int damage_val = (((int)(user.getAttribute(3) / 10.) + base_damage + user.divine_charges));
        damage_val = (int) (damage_val * (1 + user.temp_attack_bonus[0]));
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val, false);
    }
}
class Attack_C extends Ability {
    public Attack_C() {
        super("Attack", 0);
    }
    public void use(Character user, Monster target) {
        int base_damage = this.getRank() - 1;
        int damage_val = (((int)(user.getAttribute(3) / 7.5) + base_damage + user.divine_charges));
        damage_val = (int)(damage_val * (1 + user.temp_attack_bonus[0]));
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val, false);
    }
}
class Lunge extends Ability {
    public Lunge() {
        super("Lunge", 0);
        this.setSlot(1);
    }
    public void use(Character user, Monster target) {
        double base_dmg = 4 + this.getRank() * 2;
        base_dmg = ((base_dmg + user.getAttribute(0)/5.0) * (1 + user.temp_attack_bonus[0]));
        boolean crit = Command.check_for_crit(0,user.crit_chance + user.temp_crit_bonus[0]);
        int damage_val = (int)((base_dmg + user.divine_charges) * (crit ? user.getCrit_mod() : 1.0));
        int recoil = (int) (((4 - this.rank) * 0.05) * damage_val);
        user.takeDamage(recoil,false);
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val,crit);
    }
}
class Flex extends Ability {
    public Flex() {
        super("Flex", 3);
        this.setSlot(2);
        this.setTarget(Target.self);
    }
    public void use(Character user) {
        user.gainTempCrit(-(25 - 5 * getRank()), Math.max(user.temp_crit_bonus[1], 3));
        user.gainTempAttack(0.25 + getRank() * 0.25, Math.max(user.temp_attack_bonus[1], 2));
    }
}
class Finishing_Blow extends Ability {
    public Finishing_Blow() {
        super("Finishing Blow", 4);
        this.setSlot(3);
    }
    public void use(Character user, Monster target) {
        double base_dmg = this.getRank() * 2;
        base_dmg = (base_dmg + user.getAttribute(0) / 5.0) * (1 + user.temp_attack_bonus[0]);
        if (((target.current_health * 1.0) / target.getMax_health()) < 0.5) base_dmg *= 2;
        boolean crit = Command.check_for_crit(0, user.crit_chance + user.temp_crit_bonus[0]);
        int damage_val = (int)((base_dmg + user.divine_charges) * (crit ? user.getCrit_mod() : 1));
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val, crit);
    }
}
class Volley extends Ability {
    public Volley() {
        super("Volley", 1);
        this.setSlot(1);
    }
    public void use(Character user, Monster target) {
        double base_dmg = -2;
        base_dmg = ((base_dmg + user.getAttribute(1) / 5.0) * (1 + user.temp_attack_bonus[0]));
        boolean crit;
        int stacks = target.stacks_of_protection;
        for (int i = 0; i < 2 + this.getRank(); i++){
            crit = Command.check_for_crit(0,user.crit_chance + user.temp_crit_bonus[0]);
            int damage_val = (int)((base_dmg + user.divine_charges) * (crit ? user.getCrit_mod() : 1));
            target.takeDamage(damage_val, crit);
        }
        if (stacks < 2 + this.getRank()) user.divine_charges = 0;
    }
}
class Bullseye extends Ability{
    public Bullseye() {
        super("Bullseye", 2);
        this.setSlot(2);
    }
    public void use(Character user, Monster target) {
        double base_dmg = getRank();
        base_dmg = ((base_dmg + user.getAttribute(1) / 5.0) * (1 + user.temp_attack_bonus[0]));
        boolean crit = Command.check_for_crit(50, user.crit_chance + user.temp_crit_bonus[0]);
        int damage_val = (int)((base_dmg + user.divine_charges) * (crit ? user.getCrit_mod() + 0.2 : 1));
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val,crit);
        }
}
class Steady_Aim extends Ability{
    public Steady_Aim() {
        super("Steady Aim", 3);
        this.setSlot(3);
        this.setTarget(Target.self);
    }
    public void use(Character user){
        user.gainTempCrit(5 + 5 * this.getRank(), Math.max(user.temp_crit_bonus[1], 3));
        user.gainTempAttack(0.1 + this.getRank() * 0.1, Math.max(user.temp_attack_bonus[1], 3));
    }
}
class Force_Duel extends Ability{
    public Force_Duel(){
        super("Force Duel", 2);// 3, 4 or 5 depending on rank
        this.setSlot(1);
    }
    @Override
    public int getCooldown() {
        return super.getCooldown() + getRank();
    }
    public void use(Character user, Monster target){
        user.tauntTarget(target, this.getCooldown());
        if (this.getRank() > 1) target.gainTempAttack(-0.2, this.getCooldown());
        if (this.getRank() > 2) user.gainStacksOfProtection(1);
    }
}
class Endure extends Ability{
    public Endure(){
        super("Endure", 0);
        this.setSlot(2);
        this.setTarget(Target.ally);
    }
    public void use(Character user, Character target){
        int damage_val = (int) (user.getMax_health()*(this.getRank() == 3 ? 0.2 : 0.25));
        user.reduceHealth(damage_val);
        target.gainStacksOfProtection(this.getRank());
    }
}
class Fighting_Spirit extends Ability{
    public Fighting_Spirit(){
        super("Fighting Spirit", 2);
        this.setSlot(3);
        this.setTarget(Target.self);
    }
    public void use(Character user){
        int restore_val = getRank() * user.getAttribute(2)/5;
        user.restoreHealth(restore_val, false);
    }
}
class Arcane_Bolt extends Ability{
    public Arcane_Bolt(){
        super("Arcane Bolt", 5);
        this.setSlot(1);
    }
    public void use(Character user, Monster target) {
            int base_dmg = 3 * getRank();
            base_dmg = (int) ((base_dmg + user.getAttribute(3) / 2.5) * (1 + user.temp_attack_bonus[0]));
            int damage_val = base_dmg + user.divine_charges;
            if (target.stacks_of_protection == 0) user.divine_charges = 0;
            target.takeDamage(damage_val, false);
        }

}
class EXPLOSION extends Ability{
    public EXPLOSION(){
        super("EXPLOSION", 5);
        this.setSlot(2);
        this.setTarget(Target.enemies);
    }
    public void use(Character user, Monster[] targets) {
        int base_dmg = 3 * getRank();
        base_dmg = (int) ((base_dmg + user.getAttribute(3) / 5) * (1 + user.temp_attack_bonus[0]));
        int damage_val = base_dmg + user.divine_charges;
        boolean allHaveStacks = true;
        for (Monster target : targets) {
            if (target.stacks_of_protection == 0) allHaveStacks = false;
            target.takeDamage(damage_val, false);
        }
        if (!allHaveStacks) user.divine_charges = 0;
    }
    public void use(Character user, Monster target) {
        int base_dmg = 3 * getRank();
        base_dmg = (int) ((base_dmg + user.getAttribute(3) / 5) * (1 + user.temp_attack_bonus[0]));
        int damage_val = base_dmg + user.divine_charges;
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val, false);
    }
}
class Channel_Arcana extends Ability{
    public Channel_Arcana(){
        super("Channel Arcana", 0);
        this.setSlot(3);
        this.setTarget(Target.self);
    }
    public void use(Character user){
        for (int i = 0; i < 5; i++){
            if (user.cooldown_table[i] > 0) user.cooldown_table[i]--;
        }
    }
}
class Restore extends Ability{
    Restore(){
        super("Restore", 3);
        this.setSlot(1);
        this.setTarget(Target.ally);
    }
    public void use(Character user, Character target){
        int heal_value = (int) (user.getAttribute(3)/2.5) * getRank();
        boolean crit = Command.check_for_crit(0,user.crit_chance + user.temp_crit_bonus[0]);
        int missing_health = target.getMax_health() - target.current_health;
        heal_value = (int) (heal_value * (crit ? user.getCrit_mod() : 1.0));
        target.restoreHealth(heal_value, crit);
        if (missing_health < heal_value){
            int divine_charges = (int) ((heal_value - missing_health)/2.);
            target.gainDivineCharges(divine_charges);
        }
    }
}
class Healing_Aura extends Ability{
    Healing_Aura(){
        super("Healing Aura", 3);
        this.setSlot(2);
        this.setTarget(Target.allies);
    }
    public void use(Character user, Character[] targets){
        int base_heal = user.level + (getRank() * user.getAttribute(3)/5);
        for (Character target : targets){
            int heal_value = base_heal;
            int missing_health = target.getMax_health() - target.current_health;
            boolean crit = Command.check_for_crit(0,user.crit_chance + user.temp_crit_bonus[0]);
            heal_value = (int) (heal_value * (crit ? user.getCrit_mod() : 1.0));
            target.restoreHealth(heal_value, crit);
            if (missing_health < heal_value){
                int divine_charges = (int) ((heal_value - missing_health)/2.);
                target.gainDivineCharges(divine_charges);
            }
        }
    }
    public void use(Character user, Character target){
        int heal_value = user.level + (getRank() * user.getAttribute(3)/5);
        int missing_health = target.getMax_health() - target.current_health;
        boolean crit = Command.check_for_crit(0,user.crit_chance + user.temp_crit_bonus[0]);
        heal_value = (int)(heal_value * (crit ? user.getCrit_mod() : 1.0));
        target.restoreHealth(heal_value, crit);
        if (missing_health < heal_value){
            int divine_charges = heal_value - missing_health;
            target.gainDivineCharges(divine_charges);
        }
    }
}
class Bless extends Ability{
    Bless(){
        super("Bless", 1);
        this.setSlot(3);
        this.setTarget(Target.ally);
    }
    public void use(Character user, Character target){
        int divine_charges = getRank() * (int) (user.getAttribute(3) / 10.);
        target.gainDivineCharges(divine_charges);
    }
}
class Use_Item extends Ability{
    Use_Item(){
        super("Use Item", 0);
        this.setSlot(4);
        this.setTarget(Target.depends);
    }
    public void use(Item item, Monster target){
        item.use(target);
    }
    public void use(Item item, Character target){
        item.use(target);
    }
    public String show(Character pc){
        if (pc.how_many_items < 1){
            return "Throw a Stone";
        }
        else return this.getName();
    }
}
class Bite extends Ability{
    public Bite() {
        super("Bite", 0);
    }
    public void use(Monster user, Character target) {
        int base_dmg = getRank() + user.getAttribute(0)/5;
        boolean crit = Command.check_for_crit(0,user.crit_chance + user.temp_crit_bonus[0]);
        int damage_val = (int)((base_dmg + user.divine_charges) * (1 + user.temp_attack_bonus[0]) * (crit ? user.getCrit_mod() : 1.0));
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val, crit);
    }
}
class Lick_Wounds extends Ability{
    Lick_Wounds(){
        super("Lick Wounds", 3);
        this.setSlot(1);
        this.setTarget(Target.self);
    }
    public void use(Monster user){
        int restore_val = 6;
        boolean crit = (user.current_health * 3 <= user.getMax_health());
        restore_val = (int) (restore_val * (crit ? 1.5 : 1));
        user.restoreHealth(restore_val, crit);
    }
}
class Shoot extends Ability{
    Shoot(){
        super("Shoot", 0);
    }
    public void use(Monster user, Character target){
        int base_dmg = 6;
        boolean crit = Command.check_for_crit(0,user.crit_chance + user.temp_crit_bonus[0]);
        int damage_val = (int)((base_dmg + user.divine_charges) * (1 + user.temp_attack_bonus[0]) * (crit ? user.getCrit_mod() : 1.0));
        if (target.stacks_of_protection == 0) user.divine_charges = 0;
        target.takeDamage(damage_val, crit);
    }
}
class Unleash_The_Hounds extends Ability{
    Unleash_The_Hounds(){
        super("Unleash the Hounds", -1);
        this.setSlot(1);
        this.setTarget(Target.other);
    }
    public void use(Monster user, Monster[] array){
        Command.addMonster(array, new Monster("Hound", 0, 2, 1, 25, user.getSpeed(), 0, new Ability[]{new Bite(), new Lick_Wounds()}, user.attributes),1);
        Command.addMonster(array, new Monster("Hound", 0, 2, 1, 25, user.getSpeed(), 0, new Ability[]{new Bite(), new Lick_Wounds()}, user.attributes),2);
    }
}
class Drain_Vitality extends Ability{
    Drain_Vitality(){
        super("Drain Vitality", 0);
        this.setSlot(0);
        this.setTarget(Target.enemy);
    }
    public void use(Monster user, Character target){
        boolean crit = (user.taunting == target);
        int damageVal = (target.getAttribute(2)/5 * (crit ? 2 : 1));
        target.takeDamage(damageVal, crit);
        user.restoreHealth(damageVal, crit);
    }
}
class Charm extends Ability{
    Charm(){
        super("Charm", 2);
        this.setSlot(1);
        this.setTarget(Target.enemy);
    }
    public void use(Monster user, Character target){
        user.tauntTarget(target, 3);
        target.gainTempAttack(-0.3, 5);
    }
}
class Strike extends Ability{
    Strike(){
        super("Strike", 0);
        this.setSlot(0);
        this.setTarget(Target.enemy);
    }
    public void use(Monster user, Character target){
        int base_dmg = user.getAttribute(0) / 5;
        boolean crit = Command.check_for_crit(0, user.crit_chance + user.temp_crit_bonus[0]);
        base_dmg *= (crit ? user.getCrit_mod() : 1);
        target.takeDamage(base_dmg, crit);
    }

}
class Eerie_Chant extends Ability {
    Eerie_Chant() {
        super("Eerie Chant", 2);
        this.setSlot(1);
        this.setTarget(Target.enemies);
    }

    public void use(Monster user, Character[] targets) {
        int base_dmg = user.getAttribute(3)/5;
        for (Character target : targets) {
            target.takeDamage(base_dmg, false);
        }
    }
}
class Claw extends Ability{
    Claw(){
        super("Claw", 0);
        this.setSlot(0);
        this.setTarget(Target.enemies);
    }
    public void use(Monster user, Character[] targets){
        int base_dmg = user.getAttribute(0)/5 + user.getAttribute(3)/10;
        for (Character target : targets) {
            boolean crit = Command.check_for_crit(0, user.crit_chance + user.temp_crit_bonus[0]);
            int final_dmg = base_dmg * (crit ? user.getCrit_mod() : 1);
            target.takeDamage(final_dmg, crit);
            user.restoreHealth(final_dmg/2, crit);
        }
    }
    public void use(Monster user, Character target){
        int base_dmg = user.getAttribute(0)/5 + user.getAttribute(3)/5;
        boolean crit = Command.check_for_crit(0, user.crit_chance + user.temp_crit_bonus[0]);
        int final_dmg = base_dmg * (crit ? user.getCrit_mod() : 1);
        target.takeDamage(final_dmg, crit);
        target.restoreHealth(final_dmg, crit);
    }
}
class BOW_BEFORE_ME extends Ability{
    BOW_BEFORE_ME(){
        super("BOW BEFORE ME", 2);
        this.setSlot(1);
        this.target = Target.enemies;
    }
    public void use(Monster user, Character[] targets){
        for (Character target : targets){
            target.reduceHealth(target.getMax_health() - 1);
            target.gainTempAttack(-0.5, 2);
        }
        user.gainTempAttack(0.5,4);
    }
}