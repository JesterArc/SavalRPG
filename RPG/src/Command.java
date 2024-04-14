import java.util.Objects;
public class Command {
    static java.util.Scanner scan = new java.util.Scanner(System.in);
    public static boolean isValueInArray(int value, int[] array){
        for (int number : array){
            if (value == number) return true;
        }
        return false;
    }
    public static boolean check_for_crit(double base_val, double bonus){
        double roll = (Math.random() * 100);
        double crit_chance = base_val + bonus;
        return crit_chance >= roll;
    }
    public static void Say(String text){
        System.out.println(text);
    }
    public static void swap(Character[] arr, int where, int where_2){
        Character temp = arr[where_2];
        arr[where_2] = arr[where];
        arr[where] = temp;
    }
    public static void archetype_info(String archetype) {
        Say("-----");
        switch (archetype) {
            case "Berserk" -> Say("""
                    Berserk is an Archetype focusing on Strength
                    + Slightly higher base health and higher critical hit multiplier
                    Abilities besides attack and use item:
                    Lunge (no cooldown) - deals more damage than attack but has slight recoil to it
                    Flex (3 turns)- gives user temporary bonus to damage but lowers chance to crit
                    Finishing Blow (4 turns) - deals extra damage to targets below 50% max hp""");
            case "Archer" -> Say("""
                    Archer is an Archetype focusing on Agility
                    + Higher base speed and higher crit chance
                    Abilities besides attack and use item:
                    Volley (1 turn) - attacks multiple times but with lower base damage
                    Bullseye (2 turns) - has lower damage but base 50% crit chance with higher crit damage multiplier
                    Steady aim (3 turns) - raises attack and crit chance""");
            case "Templar" -> Say("""
                    Templar is an Archetype focusing on Vitality
                    + Highest base health and access to Taunting ability
                    Abilities besides attack and use item:
                    Force Duel (3/4/5 turns) - forces an enemy to attack templar
                    Endure (no cooldown) - takes about 25% of max health and gives target stack(s) of protection
                    Fighting Spirit (2 turns) - restores health to templar (based on his vitality)""");
            case "Mage" -> Say("""
                    Mage is an Archetype focusing on Arcana
                    + High base attack spells, one of which is an aoe
                    Abilities besides attack and use item:
                    Arcane Bolt (5 turns) - deals big damage to target
                    EXPLOSION (5 turns) - deals big damage to all enemies
                    Channel Arcana (no cooldown) - reduces cooldown of other abilities""");
            case "Cleric" -> Say("""
                    Cleric is an Archetype focusing on Arcana
                    + Has access to healing spells and buffs
                    Abilities besides attack and use item:
                    Recover (3 turns) - restores health to target, excess health turns into divine charges
                    Healing Aura (3 turns) - restores health to all allies, excess health turns into divine charges
                    Bless (1 turn) - gives target divine charges""");
            default -> Say("This class doesn't exist");
        }
    }
    public static String choose_name(){
        Say("-----");
        Say("What will you call this Adventurer?");
        String name = scan.next();
        Say("They shall be known as " + name + " ?" + "\nTo confirm type Yes");
        if (scan.next().equalsIgnoreCase("yes")){
            return name;
        }
        else{
            return choose_name();
        }
    }
    public static void randomize_archetype(Character pc){
        Say("The dice has spoken");
        int fate = (int) (Math.random() * 5);
        switch (fate) {
            case 0 -> pc.setArchetype(new Berserk());
            case 1 -> pc.setArchetype(new Archer());
            case 2 -> pc.setArchetype(new Templar());
            case 3 -> pc.setArchetype(new Mage());
            case 4 -> pc.setArchetype(new Cleric());
            }
        Say(pc.name + " has become a fearsome " + pc.archetype.getName());
        archetype_info(pc.archetype.getName());
    }
    public static void increase_stat(Character character, int value){
        Say("-----");
        Say("Which stat to increase by " + value + " ?");
        Say("Strength " + character.attributes[0] + "\n"
        + "Agility " + character.attributes[1] + "\n"
        + "Vitality " + character.attributes[2] + "\n"
        + "Arcana " + character.attributes[3] + "\n"
        + "Luck " + character.attributes[4]);
        String stat = scan.next();
        stat = stat.toLowerCase();
        switch(stat){
            case "strength" -> character.setAttribute(0, Math.min(character.attributes[0] + value, 100));
            case "agility" -> character.setAttribute(1, Math.min(character.attributes[1] + value, 100));
            case "vitality"  -> character.setAttribute(2, Math.min(character.attributes[2] + value, 100));
            case "arcana"  -> character.setAttribute(3, Math.min(character.attributes[3] + value, 100));
            case "luck" -> character.setAttribute(4, Math.min(character.attributes[4] + value, 100));
            default -> increase_stat(character, value);
        }
    }
    public static void increase_stat(Character character,int stat, int value){
        switch(stat){
            case 0 -> character.setAttribute(0, Math.min(character.attributes[0] + value, 100));
            case 1 -> character.setAttribute(1, Math.min(character.attributes[1] + value, 100));
            case 2  -> character.setAttribute(2, Math.min(character.attributes[2] + value, 100));
            case 3  -> character.setAttribute(3, Math.min(character.attributes[3] + value, 100));
            case 4 -> character.setAttribute(4, Math.min(character.attributes[4] + value, 100));
        }
    }
    public static void give_item(Character character, Item item, boolean speak){
        if (speak) Say("> " + character.getName() + " has found an item : " + item.getName() + " (level " + item.getLevel() + ")");
        int val = character.how_many_items;
        if (val < character.inventory.length){
            character.inventory[val] = item;
            item.setSlot(val);
            character.how_many_items += 1;
        }
        else{
            Say(character.getName() + "'s inventory is full!");
            int slot = 0;
            Say("-----\nCurrently Stored Items:");
            for (Item stored_item : character.inventory){
                System.out.println(++slot + ". " + stored_item.getName() + " (Level " + stored_item.getLevel() + ")");
            }
            Say("\nType the number of the item to replace with " + item.getName());
            Say("Type 0 if you wish to discard the item instead");
            int choice = scan.nextInt();
            if (choice == 0) Say(item.getName() + " has been discarded");
            else if (choice <= character.inventory.length){
                character.inventory[--choice] = item;
            }
            else give_item(character, item, speak);
        }
    }
    public static void give_item(Character character){
        int level = character.level;
        Item[] arr = {new Holy_Water(level), new Chill_Pill(level), new Bomb(level), new Healing_Potion(level),
        new Plushie(level), new Red_Powder(level), new Yellow_Powder(level)};
        int which = (int) (Math.random() * 7);
        give_item(character, arr[which], true);
    }
    public static void give_item(Monster monster, Item item){
        if (monster.how_many_items < monster.inventory.length){
            monster.inventory[monster.how_many_items++] = item;
        }
    }
    public static Character create_your_character(){
        Character character = new Character();
        character.setName(choose_name());
        randomize_archetype(character);
        increase_stat(character, 10);
        increase_stat(character, 5);
        character.calc_max_health(character.archetype);
        character.calc_crit_chance(character.archetype);
        String archetype = character.archetype.getName();
        give_item(character, new Healing_Potion(character.level), true);
        character.setCrit_mod(2);
        character.current_health = character.getMax_health();
        switch(archetype) {
            case "Berserk" : {
                character.abilities[0] = new Attack_B();
                character.abilities[1] = new Lunge();
                character.abilities[2] = new Flex();
                character.abilities[3] = new Finishing_Blow();
                character.setCrit_mod(3);
                break;
            }
            case "Archer" : {
                character.abilities[0] = new Attack_A();
                character.abilities[1] = new Volley();
                character.abilities[2] = new Bullseye();
                character.abilities[3] = new Steady_Aim();
                break;
            }
            case "Templar" : {
                character.abilities[0] = new Attack_T();
                character.abilities[1] = new Force_Duel();
                character.abilities[2] = new Endure();
                character.abilities[3] = new Fighting_Spirit();
                break;
            }
            case "Mage" : {
                character.abilities[0] = new Attack_M();
                character.abilities[1] = new Arcane_Bolt();
                character.abilities[2] = new EXPLOSION();
                character.abilities[3] = new Channel_Arcana();
                break;
            }
            case "Cleric" : {
                character.abilities[0] = new Attack_C();
                character.abilities[1] = new Restore();
                character.abilities[2] = new Healing_Aura();
                character.abilities[3] = new Bless();
                give_item(character, new Bomb(character.level), true);
                break;
            }
        }
        return character;
    }
    public static void ability_rank_up(Character character){
        Ability[] arr = character.abilities;
        Say("Type the number of the ability you want to rank up");
        for (int i = 0; i < 4; i++){
            if (arr[i].getRank() < 3){
                Say(arr[i].getSlot() + ". " + arr[i].getName() + " (Rank: " + arr[i].getRank() + ")");
            }
        }
        int number = scan.nextInt();
            switch(number){
                case 1,2,3,4 :{
                    if (arr[number-1].getRank() == 3){
                        Say("This ability has already reached its maximum level");
                        ability_rank_up(character);
                    }
                    else{
                        Say("-----");
                        Say(arr[number-1].getName() + " has been upgraded!");
                        character.abilities[number-1].setRank(character.abilities[number-1].getRank() + 1);
                        Say("Rank " + (arr[number-1].getRank() - 1) + " -> Rank " +
                                (arr[number-1].getRank() != 3 ? arr[number-1].getRank() : "Max"));
                    }
                    break;
                }
                default: ability_rank_up(character);
        }
    }
    public static void ability_rank_up(Character character, int ability){
        character.abilities[ability-1].setRank(character.abilities[ability].getRank()+1);
    }
    public static Character create_your_character(String name, Archetype archetype){
        Character character = new Character();
        character.setName(name);
        character.setArchetype(archetype);
        String archetype_name = archetype.getName();
        character.setCrit_mod(2);
        switch(archetype_name){
            case "Berserk": {
                character.abilities[0] = new Attack_B();
                character.abilities[1] = new Lunge();
                character.abilities[2] = new Flex();
                character.abilities[3] = new Finishing_Blow();
                character.setCrit_mod(3);
                give_item(character, new Healing_Potion(character.level), false);
                increase_stat(character, 0, 10);
                increase_stat(character, 2, 5);
                break;
            }
            case "Archer": {
                character.abilities[0] = new Attack_A();
                character.abilities[1] = new Volley();
                character.abilities[2] = new Bullseye();
                character.abilities[3] = new Steady_Aim();
                give_item(character, new Healing_Potion(character.level), false);
                increase_stat(character, 1, 10);
                increase_stat(character, 4, 5);
                character.base_speed = 2;
                break;
            }
            case "Templar" : {
                character.abilities[0] = new Attack_T();
                character.abilities[1] = new Force_Duel();
                character.abilities[2] = new Endure();
                character.abilities[3] = new Fighting_Spirit();
                give_item(character, new Healing_Potion(character.level), false);
                increase_stat(character, 2, 10);
                increase_stat(character, 1, 5);
                break;
            }
            case "Mage" : {
                character.abilities[0] = new Attack_M();
                character.abilities[1] = new Arcane_Bolt();
                character.abilities[2] = new EXPLOSION();
                character.abilities[3] = new Channel_Arcana();
                give_item(character, new Healing_Potion(character.level), false);
                increase_stat(character, 3, 10);
                increase_stat(character, 3, 5);
                break;
            }
            case "Cleric" : {
                character.abilities[0] = new Attack_C();
                character.abilities[1] = new Restore();
                character.abilities[2] = new Healing_Aura();
                character.abilities[3] = new Bless();
                give_item(character, new Bomb(character.level), false);
                increase_stat(character, 3, 10);
                increase_stat(character, 4, 5);
                break;
            }
        }
        character.calc_crit_chance(archetype);
        character.calc_max_health(archetype);
        character.current_health = character.getMax_health();
        return character;
    }
    public static Monster chooseEnemy(Monster[] targets){
        Say("Type the number of the target you want to choose");
        int slot = 1;
        int[] arr = new int[targets.length];
        int arr_slot = 0;
        for (Monster target : targets){
            if (target != null){
                Say(slot + ". " + target.getName() + " " + target.current_health + "/" + target.getMax_health());
                arr[arr_slot++] = slot;
            }
            slot++;
        }
        int choice = scan.nextInt();
        if (isValueInArray(choice, arr) && choice > 0) return targets[choice-1];
        else return chooseEnemy(targets);
    }
    public static Character chooseAlly(Character[] targets){
        Say("Type the number of the target you want to choose");
        int slot = 1;
        int[] arr = new int[targets.length];
        int arr_slot = 0;
        for (Character target : targets){
            Say(slot + ". " + target.getName() + " " + target.current_health + "/" + target.getMax_health());
            arr[arr_slot++] = slot;
            slot++;
        }
        int choice = scan.nextInt();
        if (isValueInArray(choice, arr) && choice > 0) return targets[choice-1];
        else return chooseAlly(targets);
    }
    public static Character chooseEnemyNotDead(Character[] targets){
        for (Character character : targets){
            if (character.alive) return character;
        }
        return targets[0];
    }
    public static Character chooseEnemyWeakest(Character[] targets){
        int lowest_hp = -1;
        int which = 0;
        for (int i = 0; i < targets.length; i++) {
            Character target = targets[i];
            if ((target.alive && lowest_hp < 0) || (target.alive && target.current_health < lowest_hp)) {
                lowest_hp = target.current_health;
                which = i;
            }
        }
        return targets[which];
    }
    public static Character chooseEnemyTaunted(Monster user){
        return user.taunting;
    }
    public static Character chooseEnemyHealthiest(Character[] targets){
        Character currentTarget = new Character();
        int health = 0;
        for (Character target : targets){
            if (target.current_health > health){
                currentTarget = target;
                health = target.current_health;
            }
        }
        return currentTarget;
    }
    public static Character chooseEnemyRandomly(Character[] targets){
        int target = (int) (Math.random() * targets.length);
        return targets[target];
    }
    public static Ability chooseAbility(Character user, Ability[] abilities){
        Say("-----");
        if (user.tauntedBy != null) {
            Say("User has been provoked to attack");
            return abilities[0];
        }
        Say("Type the number of the Ability you want to use");
        int slot = 0;
        int[] arr = new int[user.abilities.length];
        for (int i = 0; i < abilities.length; i++){
            if (user.cooldown_table[i] == 0){
                Ability ability = abilities[i];
                Say(ability.getSlot()  + ". " + ability.show(user));
                arr[slot++] = i+1;
            }
        }
        int choice = scan.nextInt();
        if (isValueInArray(choice, arr) && choice > 0) return abilities[choice-1];
        else return chooseAbility(user, abilities);
    }
    public static Ability chooseAbilityHoundmaster(Monster user, Ability[] abilities, Monster[] allies){
        if (user.tauntedBy != null) {
            Say(user.getName() + " has been provoked to attack");
            return abilities[0];
        }
        if (user.cooldown_table[1] == 0) return abilities[1];
        if ((allies[1].alive || allies[2].alive) && user.how_many_items > 0) return abilities[2];
        return abilities[0];
    }
    public static Ability chooseAbilityHound(Monster user, Ability[] abilities){
        if (user.tauntedBy != null) {
            Say(user.getName() + " has been provoked to attack");
            return abilities[0];
        }
        if (user.cooldown_table[1] == 0 && (user.current_health * 2 < user.getMax_health())) return abilities[1];
        else return abilities[0];
    }
    public static Ability chooseAbilitySinister(Monster user, Ability[] abilities){
        if (user.tauntedBy != null){
            Say(user.getName() + " has been provoked to attack");
            return abilities[0];
        }
        else if (user.taunting != null || user.cooldown_table[1] > 0){
            return abilities[0];
        }
        else return abilities[1];
    }
    public static Ability chooseAbilityCultist(Monster user, Ability[] abilities){
        if (user.tauntedBy != null){
            Say(user.getName() + " has been provoked to attack");
            return abilities[0];
        }
        else if (user.cooldown_table[1] == 0) return abilities[1];
        else return abilities[0];
    }
    public static Ability chooseAbilityDeadlain(Monster user, Ability[] abilities){
        if (user.tauntedBy != null){
            Say(user.getName() + " has been provoked to attack");
            return abilities[0];
        }
        else if (user.cooldown_table[1] == 0) return abilities[1];
        else return abilities[0];
    }
    public static Item chooseItem(Character user) {
        if (user.how_many_items == 0) {
            return new Stone(user.level);
        }
        int slot = 0;
        int arr_slot = 0;
        int[] arr = new int[user.how_many_items];
        Say("Type the number of the item you want to use:");
        for (Item item : user.inventory) {
            slot++;
            if (item != null) {
                System.out.println(slot + ". " + item.getName() + " (Level " + item.getLevel() + ")");
                arr[arr_slot] = slot;
                arr_slot++;
            }
        }
        int choice = scan.nextInt();
        if (isValueInArray(choice, arr) && choice > 0) return user.inventory[--choice];
        else return chooseItem(user);
    }
    public static void roundStart(Character target){
        target.divine_charges = (int) (target.divine_charges / 2.);
        if ((int) target.temp_crit_bonus[1] == 1) {
            target.temp_crit_bonus[0] = 0;
            Say("> " + target.getName() + " has lost crit bonus");
        }
        if (target.temp_crit_bonus[1] > 0) target.temp_crit_bonus[1]--;
        if ((int) target.temp_attack_bonus[1] == 1){
            target.temp_attack_bonus[0] = 0;
            Say("> " + target.getName() + " has lost attack bonus");
        }
        if (target.temp_attack_bonus[1] > 0) target.temp_attack_bonus[1]--;
        if (target.turns_remain_taunting == 1){
            target.removeTaunt(target.taunting);
        }
        if (target.turns_remain_taunting == 1) target.removeTaunt(target);
        else if (target.turns_remain_taunting != 0) target.turns_remain_taunting--;
    }
    public static void roundStart(Monster target){
        if (target == null) return;
        target.divine_charges = (int) (target.divine_charges / 2.);
        if ((int) target.temp_crit_bonus[1] == 1) {
            target.temp_crit_bonus[0] = 0;
            Say("> " + target.getName() + " has lost crit bonus");
        }
        if (target.temp_crit_bonus[1] > 0) target.temp_crit_bonus[1]--;
        if ((int) target.temp_attack_bonus[1] == 1){
            target.temp_attack_bonus[0] = 0;
            Say("> " + target.getName() + " has lost attack bonus");
        }
        if (target.temp_attack_bonus[1] > 0) target.temp_attack_bonus[1]--;
        if (target.turns_remain_taunting == 1){
            target.removeTaunt(target.taunting);
        }
        if (target.turns_remain_taunting == 1) target.removeTaunt(target);
        else if (target.turns_remain_taunting != 0) target.turns_remain_taunting--;
    }
    public static void roundStart(Character[] targets){
        for (Character target : targets){
            roundStart(target);
        }
    }
    public static void roundStart(Monster[] targets){
        for (Monster target : targets){
            roundStart(target);
        }
    }
    public static Character[] expand(Character[] arr){
        Character[] newArr = new Character[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }
    public static Monster[] expand(Monster[] arr){
        Monster[] newArr = new Monster[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }
    public static Monster[] expandThenAdd(Monster[] arr, Monster newMonster){
        Monster[] newArr = expand(arr);
        newArr[newArr.length - 1] = newMonster;
        return newArr;
    }
    public static Character[] expandThenAdd(Character[] arr, Character newCharacter){
        Character[] newArr = expand(arr);
        newArr[newArr.length - 1] = newCharacter;
        return newArr;
    }
    public static void addMonster(Monster[] arr, Monster newMonster, int where){
        arr[where] = newMonster;
    }
    public static boolean teamAlive(Character[] team){
        for (Character character : team){
            if (character.alive) return true;
        }
        return false;
    }
    public static boolean teamAlive(Monster[] team){
        for (Monster monster : team){
            if (monster.alive) return true;
        }
        return false;
    }
    public static int giveExp(Monster[] enemies, int globalExp){
        for(Monster monster : enemies){
            globalExp += monster.getExpToGive();
        }
        return globalExp;
    }
    public static Character[] removeDuplicateArchetype(Character[] arr, Archetype archetype){
        for (int i = 0; i < arr.length; i++){
            if (Objects.equals(arr[i].archetype.getName(), archetype.getName())){
                swap(arr, i, arr.length-1);
                Character[] array = new Character[arr.length-1];
                System.arraycopy(arr, 0, array, 0, array.length);
                return array;
            }
        }
        return arr;
    }
    public static Character randomCharacterFromArray(Character[] arr){
        return arr[(int)(Math.random() * arr.length)];
    }
    public static void resetCooldowns(Character[] arr){
        for (Character character : arr){
            for (int i = 0; i < character.cooldown_table.length; i++){
                character.cooldown_table[i] = 0;
            }
        }
    }
    public static void resetCooldowns(Monster[] arr){
        for (Monster monster : arr){
            if (monster != null) for (int i = 0; i < monster.cooldown_table.length; i++){
                monster.cooldown_table[i] = 0;
            }
        }
    }
    public static void sortBySpeed(Character[] arr){
        for (int i = 1; i < arr.length; i++){
            while(i >= 1 && arr[i].getSpeed() > arr[i-1].getSpeed()){
                if (arr[i].getSpeed() > arr[i - 1].getSpeed()) {
                    swap(arr, i, i - 1);
                }
                i--;
            }
        }
    }
    public static void sortBySpeed(Monster[] arr){
        for (int i = 1; i < arr.length; i++){
            while(i >= 1 && (arr[i] != null) && (arr[i-1] != null) && arr[i].getSpeed() > arr[i-1].getSpeed()){
                if (arr[i].getSpeed() > arr[i - 1].getSpeed()) {
                    swap(arr, i, i - 1);
                }
                i--;
            }
        }
    }
    public static void removeTauntsAfterCombat(Character[] allies){
        for (Character ally : allies){
            if (ally.tauntedBy != null){
                ally.tauntedBy.removeTaunt(ally);
            }
            if (ally.taunting != null){
                ally.removeTaunt(ally.taunting);
            }
        }
    }
    public static boolean fight(Character[] allies, Monster[] monsters){
        int turnCounter = 1;
        resetCooldowns(allies);
        resetCooldowns(monsters);
        sortBySpeed(allies);
        sortBySpeed(monsters);
        int ally_slot;
        int monster_slot;
        while (teamAlive(monsters) && teamAlive(allies)) {
            Say("-----\nTurn " + turnCounter++);
            roundStart(allies);
            roundStart(monsters);
            ally_slot = 0;
            monster_slot = 0;
            for (int i = 0; i < allies.length + monsters.length; i++) {
                Say("-----");
                if (ally_slot == allies.length) {
                    Say("> " + monsters[monster_slot].getName() + "'s turn");
                    Say("-----");
                    monsters[monster_slot].useAbility(monsters, allies);
                    monster_slot++;
                } else if (monster_slot == monsters.length) {
                    Say("> " + allies[ally_slot].getName() + "'s turn");
                    allies[ally_slot].useAbility(allies, monsters);
                    ally_slot++;
                } else if (allies[ally_slot].getSpeed() >= monsters[monster_slot].getSpeed()) {
                    Say("> " + allies[ally_slot].getName() + "'s turn");
                    allies[ally_slot].useAbility(allies, monsters);
                    ally_slot++;
                } else {
                    Say("> " + monsters[monster_slot].getName() + "'s turn");
                    Say("-----");
                    monsters[monster_slot].useAbility(monsters, allies);
                    monster_slot++;
                }
            }
        }
        return (teamAlive(allies));
    }
    public static int postBattle(int globalExp, int[] expTable, Character player, Character[] allies, Monster[] monsters) {
        globalExp = giveExp(monsters, globalExp);
        Command.Say("-----\nYou won");
        if (expTable[player.level] <= globalExp) {
            for (Character character : allies) {
                character.level_up();
                Command.give_item(character);
            }
        }
        return globalExp;
    }
    public static Character[] recruit(Character player, Character[] allies, Character[] possibleAllies){
        if (player.level > allies.length) {
            Character newFace = Command.randomCharacterFromArray(possibleAllies);
            Say("-----\nA new recruit joins your party");
            Say("They are " + newFace.getName() + " the " + newFace.archetype.getName());
            archetype_info(newFace.archetype.getName());
            allies = expandThenAdd(allies, newFace);
            do {
                newFace.level_up();
                give_item(newFace);
            } while (newFace.level < player.level);
        }
        return allies;
    }
}