public class Monster extends Character{
    private int expToGive;
    public Character tauntedBy = null;
    public Character taunting = null;
    Monster(String enemyName, int exp, int critMod, int critChanceMod,
            int max_health, int speed, int maxItems, Ability[] abilities){
        super();
        this.base_speed = speed;
        this.name = enemyName;
        this.setExpToGive(exp);
        this.setCrit_mod(critMod);
        this.calc_crit_chance(critChanceMod);
        this.max_health = max_health;
        this.inventory = new Item[maxItems];
        this.abilities = abilities;
        this.cooldown_table = new int[abilities.length];
        this.friendly = false;
        this.current_health = this.getMax_health();

    }
    Monster(String enemyName, int exp, int critMod, int critChanceMod,
            int max_health, int speed, int maxItems, Ability[] abilities, int[] stats){
        this(enemyName, exp, critMod, critChanceMod, max_health, speed, maxItems, abilities);
        this.attributes = stats;
    }
    public int getExpToGive() {
        return expToGive;
    }
    public void setExpToGive(int expToGive) {
        this.expToGive = expToGive;
    }
    public void calc_crit_chance(int critChanceMod) {
        crit_chance = 5.0 + (attributes[4] / 10.0) * critChanceMod;
    }
    public void tauntTarget(Character target, int turns){
        this.taunting = target;
        this.turns_remain_taunting = turns;
        target.tauntedBy = this;
        Command.Say("> " + this.getName() + " has taunted " +
                target.getName() +" for " + turns + " turns");
    }

    public void useAbility(Monster[] allies, Character[] enemies) {
        if (this.current_health <= 0) {
            Command.Say(this.getName() + " is unconscious");
            return;
        }
        switch (this.name) {
            case ("Big Rat") -> {
                Ability ability = this.abilities[0];
                Command.Say("> " + this.getName() + " used " + ability.show(this));
                if (this.tauntedBy != null){
                    this.abilities[0].use(this, this.tauntedBy);
                }
                else ability.use(this, Command.chooseEnemyNotDead(enemies));
            }
            case ("Houndmaster") -> {
                Ability ability = Command.chooseAbilityHoundmaster(this, this.abilities, allies);
                checkCooldowns(ability);
                Command.Say("> " + this.getName() + " used " + ability.show(this));
                if (ability.getTarget() == Target.enemy){
                    if (this.tauntedBy != null) ability.use(this, this.tauntedBy);
                    else ability.use(this, Command.chooseEnemyWeakest(enemies));
                }
                else if (ability.getTarget() == Target.depends){
                    Character target = Command.chooseEnemyRandomly(enemies);
                    if (allies[1].alive && allies[1].temp_attack_bonus[0] == 0) {
                        ability.use(this.inventory[this.how_many_items - 1], allies[1]);
                        allies[1].abilities[0].use(allies[1], target);
                        this.inventory[this.how_many_items - 1] = null;
                        this.how_many_items--;
                    }
                    else if (allies[2].alive && allies[2].temp_attack_bonus[0] == 0){
                        ability.use(this.inventory[this.how_many_items - 1], allies[2]);
                        allies[2].abilities[0].use(allies[1], target);
                        this.inventory[this.how_many_items - 1] = null;
                        this.how_many_items--;
                    }
                    else this.abilities[0].use(this, target);
                }
                else ability.use(this, allies);
            }
            case ("Hound") -> {
                Ability ability = Command.chooseAbilityHound(this, this.abilities);
                checkCooldowns(ability);
                Command.Say("> " + this.getName() + " used " + ability.show(this));
                if (ability.getTarget() == Target.self) ability.use(this);
                else if (this.tauntedBy == null) ability.use(this, Command.chooseEnemyRandomly(enemies));
                else ability.use(this, this.tauntedBy);
            }
            case ("Sinister Figure") -> {
                Ability ability = Command.chooseAbilitySinister(this, this.abilities);
                checkCooldowns(ability);
                Command.Say("> " + this.getName() + " used " + ability.show(this));
                if (this.tauntedBy != null) ability.use(this, this.tauntedBy);
                else if (this.taunting != null) ability.use(this, this.taunting);
                else ability.use(this, Command.chooseEnemyHealthiest(enemies));
            }
            case ("Cultist") -> {
                Ability ability = Command.chooseAbilityCultist(this, this.abilities);
                checkCooldowns(ability);
                Command.Say("> " + this.getName() + " used " + ability.show(this));
                if (ability.getTarget() == Target.enemies){
                    ability.use(this, enemies);
                }
                else if(this.tauntedBy != null){
                    ability.use(this, this.tauntedBy);
                }
                else ability.use(this, Command.chooseEnemyRandomly(enemies));
            }
            case "Deadlain" -> {
                Ability ability = Command.chooseAbilityDeadlain(this, this.abilities);
                checkCooldowns(ability);
                Command.Say("> " + this.getName() + " used " + ability.show(this));
                if (this.tauntedBy != null){
                    ability.use(this, this.tauntedBy);
                }
                else{
                    ability.use(this, enemies);
                }
            }
        }
    }
}