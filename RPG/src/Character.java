public class Character {
    public int[] attributes = new int[5]; // Strength, Agility, Vitality, Arcana, Luck
    public String name;
    public int level = 1;
    public Archetype archetype;
    public int max_health; // 10 + level * Vitality/5
    public double crit_chance; // 10 + Luck/5 * 1.5
    public int crit_mod;
    public int current_health;
    public Item[] inventory = new Item[5];
    public int how_many_items = 0;
    public double[] temp_attack_bonus = {0.0,0};
    public double[] temp_crit_bonus = {0.0, 0};
    public Monster tauntedBy = null;
    public int stacks_of_protection = 0;
    public int divine_charges = 0;
    public int base_speed = 0;
    public boolean alive = true;
    public Monster taunting = null;
    public int turns_remain_taunting = 0;
    public Ability[] abilities = {null,null,null,null,new Use_Item()};
    public int[] cooldown_table = {0,0,0,0,0};
    public boolean friendly = true;
    Character(){
        setAttributes(new int[]{10,10,10,10,10});
    }
    Character(int[] arr){
        if (arr.length == 5) setAttributes(arr);
        else {
            int[] array = new int[5];
            System.arraycopy(arr, 0, array, 0, 5);
            setAttributes(array);
        }
    }
    public void setAttribute(int which_value, int value){
        attributes[which_value] = value;
    }
    public int getAttribute(int which_value){
        return attributes[which_value];
    }
    public void calc_max_health(Archetype archetype){
        max_health = archetype.getBaseHP() + level * attributes[2] / 5;
    }
    public void calc_crit_chance(Archetype archetype){
        crit_chance = 5.0 + (attributes[4]/10.0) * archetype.getCrit_chance_mod();
    }
    public int getCrit_mod() {
        return crit_mod;
    }
    public void setCrit_mod(int crit_mod) {
        this.crit_mod = crit_mod;
    }
    public int getMax_health(){
        return this.max_health;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setArchetype(Archetype archetype) {
        this.archetype = archetype;
    }
    public void setAttributes(int[] attributes) {
        this.attributes = attributes;
    }
    public void info(){
        Command.Say("-----");
        System.out.println("CHARACTER: " + this.getName() + " the " + this.archetype.getName() + " (level " + this.level + ")");
        System.out.println("HEALTH: " + this.current_health + "/" + this.getMax_health() + "\n");
        System.out.println("ATTRIBUTES: ");
        System.out.println("Strength " + this.getAttribute(0) + "\n"
                + "Agility " + this.getAttribute(1) + "\n"
                + "Vitality " + this.getAttribute(2) + "\n"
                + "Arcana " + this.getAttribute(3) + "\n"
                + "Luck " + this.getAttribute(4) + "\n");
        System.out.println("EQUIPMENT:");
        for (int i = 0; i < this.how_many_items; i++){
            Item item = this.inventory[i];
            if (item != null) System.out.println((i + 1) + ". " + item.getName() + " (Level " + item.getLevel() + ")");
        }
        System.out.println("\n" + "ABILITIES:");
        for (Ability ability : this.abilities){
            System.out.println((ability.getSlot()) + ". " + ability.getName() +
                    " (Rank: " + (ability.getRank() != 3 ? ability.getRank() : "Max") + ")");
        }
    }
    public void level_up(){
        Command.Say("-----");
        Command.Say(this.getName() + " - Level Up!");
        this.level++;
        Command.increase_stat(this,10);
        Command.increase_stat(this, 5);
        Command.ability_rank_up(this);
        this.calc_max_health(this.archetype);
        this.calc_max_health(this.archetype);
        this.current_health = this.getMax_health();
        this.alive = true;
    }
    public void level_up(int stat1, int stat2, int ability){
        this.level++;
        Command.increase_stat(this,stat1,10);
        Command.increase_stat(this,stat2, 5);
        Command.ability_rank_up(this,ability);
        this.calc_max_health(this.archetype);
        this.calc_max_health(this.archetype);
        this.current_health = this.getMax_health();
        this.alive = true;
    }
    public void takeDamage(int damage, boolean crit){
        int pre_attack = this.current_health;
        if (this.stacks_of_protection > 0){
            this.stacks_of_protection--;
            Command.Say("> " + this.getName() + " has lost a stack of protection");
        }
        else{
            this.current_health -= damage;
            Command.Say("> " + this.getName() + " has taken " + damage + " damage " + (crit ? "(CRIT)" : ""));
        }
        if (pre_attack > 0 && this.current_health <= 0){
            Command.Say("> " + this.getName() + " has been downed");
            this.alive = false;
            if (this.taunting != null) this.removeTaunt(this.taunting);
        }
    }
    public void restoreHealth(int value, boolean crit){
        int pre_heal = this.current_health;
        this.current_health = Math.min(this.current_health + value, this.getMax_health());
        Command.Say("> " + this.getName() + " has regained " + value + " health " + (crit ? "(CRIT)" : ""));
        if (pre_heal <= 0 && this.current_health > 0 ){
            Command.Say("> " + this.getName() + " has been brought back");
            this.alive = true;
        }
    }
    public void gainDivineCharges(int value){
        this.divine_charges += value;
        Command.Say("> " + this.getName() + " has gained " + value + " divine charge" + (value > 1 ? "s":""));
    }
    public void gainStacksOfProtection(int value){
        this.stacks_of_protection += value;
        Command.Say("> " + this.getName() + " has gained " + value + " " + (value > 1 ? "stacks":"stack") +" of protection");
    }
    public void reduceHealth(int damage){
        int pre_attack = this.current_health;
        this.current_health -= damage;
        Command.Say("> " + this.getName() + " has lost " + damage + " health");
        if (pre_attack > 0 && this.current_health <= 0){
            Command.Say("> " + this.getName() + " has been downed");
            this.alive = false;
            if (this.taunting != null) this.removeTaunt(this.taunting);
        }
    }
    public void gainTempAttack(double value, double turns){ // gainTemporaryAttackBonus
        this.temp_attack_bonus[0] += value;
        this.temp_attack_bonus[1] = turns;
        if (value >= 0) Command.Say("> " + this.getName() + " has their attack increased by " +
                (int)(value*100) +"% for " + (int) turns + " turns");
        else Command.Say("> " + this.getName() + " has their attack decreased by " +
                (int)(-value*100) +"% for " + (int) turns + " turns");
    }
    public void gainTempCrit(double value, double turns){ // gainTemporaryCriticalBonus
        this.temp_crit_bonus[0] += value;
        this.temp_crit_bonus[1] = turns;
        if (value >= 0) Command.Say("> " + this.getName() + " has their crit chance increased by " +
                (int)(value) +"% for " + (int) turns + " turns");
        else Command.Say("> " + this.getName() + " has their crit chance decreased by " +
                (int)(-value) +"% for " + (int) turns + " turns");
    }
    public void tauntTarget(Monster target, int turns){
        this.taunting = target;
        this.turns_remain_taunting = turns;
        target.tauntedBy = this;
        Command.Say("> " + this.getName() + " has taunted " +
                target.getName() +" for " + turns + " turns");
    }
    public void removeTaunt(Monster target){
        Command.Say("> " + target.getName() + " is no longer Taunted");
        target.tauntedBy.turns_remain_taunting = 0;
        target.tauntedBy.taunting = null;
        target.tauntedBy = null;
    }
    public void removeTaunt(Character target){
        Command.Say("> " + target.getName() + " is no longer Taunted");
        target.tauntedBy.turns_remain_taunting = 0;
        target.tauntedBy.taunting = null;
        target.tauntedBy = null;
    }
    public int getSpeed(){
        return base_speed + this.getAttribute(1) / 5;
    }
    public void useAbility(Character[] allies, Monster[] enemies){
        if (this.current_health <= 0) {
            Command.Say(this.getName() + " is unconscious");
            return;
        }
        Ability ability = Command.chooseAbility(this, this.abilities);
        checkCooldowns(ability);
        Command.Say("> " + this.getName() + " used " + ability.show(this));
        switch(ability.getTarget()){
            case enemies -> ability.use(this, enemies);
            case allies -> ability.use(this, allies);
            case self -> ability.use(this);
            case ally -> ability.use(this, Command.chooseAlly(allies));
            case enemy -> {
                if (this.tauntedBy != null) ability.use(this, this.tauntedBy);
                else ability.use(this, Command.chooseEnemy(enemies));
            }
            case depends -> {
                Item item = Command.chooseItem(this);
                if (this.how_many_items > 0){
                    Command.Say("> " + this.getName() + " used a " + item.getName());
                    this.inventory[item.getSlot()] = this.inventory[this.how_many_items - 1];
                    this.inventory[this.how_many_items - 1] = null;
                    this.how_many_items--;}
                else Command.Say("> " + this.getName() + " picked up a Stone to throw");
                Target target = item.getTarget();
                if (target == Target.enemy) ability.use(item, Command.chooseEnemy(enemies));
                else ability.use(item, Command.chooseAlly(allies));
            }
        }
    }
    public void checkCooldowns(Ability usedOne){
        for (int i = 0; i < this.abilities.length; i++){
            if (i == usedOne.getSlot()-1) this.cooldown_table[i] = usedOne.getCooldown();
            else if (this.cooldown_table[i] > 0) this.cooldown_table[i]--;
        }
    }
}