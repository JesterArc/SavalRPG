public class Play {
    public static void main(String[] args) {
        int global_exp_counter = 0;
        int[] expTable = {0, 9, 99, 299, 499, 999};
        Character npc = Command.create_your_character("Borc", new Berserk());
        Character npc_2 = Command.create_your_character("Shotya", new Archer());
        Character npc_3 = Command.create_your_character("Kaladin", new Templar());
        Character npc_4 = Command.create_your_character("Megamin", new Mage());
        Character npc_5 = Command.create_your_character("Wololo", new Cleric());
        Character[] possibleAllies = {npc, npc_2, npc_3, npc_4, npc_5};
        Character player = Command.create_your_character();
        possibleAllies = Command.removeDuplicateArchetype(possibleAllies, player.archetype);
        Monster rat = new Monster("Big Rat", 10, 2, 1, 10, 2, 0,
                new Ability[]{new Bite()});
        Monster houndmaster = new Monster("Houndmaster", 90, 0, 1, 45, 5, 2,
                new Ability[]{new Shoot(), new Unleash_The_Hounds(), new Use_Item()}, new int[]{15, 20, 25, 10, 15});
        Command.give_item(houndmaster, new Dog_Treat(2));
        Command.give_item(houndmaster, new Dog_Treat(1));
        Monster temptress = new Monster("Sinister Figure", 100, 2, 1, 90, 7, 0,
                new Ability[]{new Drain_Vitality(), new Charm()}, new int[]{10, 20, 30, 30, 20});
        Monster cultistA = new Monster("Cultist", 50, 2, 1, 40, 3, 0,
                new Ability[]{new Strike(), new Eerie_Chant()}, new int[]{20, 10, 10, 25, 20});
        Monster cultistB = new Monster("Cultist", 50, 2, 1, 40, 3, 0,
                new Ability[]{new Strike(), new Eerie_Chant()}, new int[]{20, 10, 10, 25, 20});
        Monster cultistC = new Monster("Cultist", 50, 2, 1, 50, 3, 0,
                new Ability[]{new Strike(), new Eerie_Chant()}, new int[]{20, 10, 10, 20, 20});
        Monster cultistD = new Monster("Cultist", 50, 2, 1, 50, 3, 0,
                new Ability[]{new Strike(), new Eerie_Chant()}, new int[]{20, 10, 10, 20, 20});
        Monster cultistE = new Monster("Cultist", 50, 2, 1, 50, 3, 0,
                new Ability[]{new Strike(), new Eerie_Chant()}, new int[]{20, 10, 10, 20, 20});
        Monster cultistF = new Monster("Cultist", 50, 2, 1, 50, 3, 0,
                new Ability[]{new Strike(), new Eerie_Chant()}, new int[]{20, 10, 10, 20, 20});
        Monster cultistG = new Monster("Cultist", 50, 2, 1, 50, 3, 0,
                new Ability[]{new Strike(), new Eerie_Chant()}, new int[]{20, 10, 10, 20, 20});
        Monster DeadLain = new Monster("Deadlain", 500, 2,1,200,10,0,
                new Ability[]{new Claw(), new BOW_BEFORE_ME()}, new int[]{25,40,100,50,30});
        Character[] allies = {player};
        Monster[] monsters;
        Monster[] ratEncounter = {rat};
        Monster[] houndEncounter = {houndmaster, null, null};
        Monster[] temptressEncounter = {temptress, cultistA, cultistB};
        Monster[] cultistRitualEncounter = {cultistC, cultistD, cultistE, cultistF, cultistG};
        Monster[] finalEncounter = {DeadLain};
        for (Monster monster : cultistRitualEncounter){
            monster.stacks_of_protection = 1;
            monster.cooldown_table[1] = 1;
        }
        Monster[][] encounters = {ratEncounter, houndEncounter, temptressEncounter, cultistRitualEncounter, finalEncounter};
        String[] intros = new String[5];
        intros[0] = "-----\nYou wake up down in a cellar, where a big hungry rat attacks you\nCombat Start!";
        intros[1] = "-----\nWhile traveling with your new ally you have been ambushed by a hired assasin\nCombat Start!";
        intros[2] = "-----\nYou and your allies are attacked by a group of cultists led by a sinister figure\nCombat Start!";
        intros[3] = "-----\nFollowing the footsteps of these hooded figures you stumble upon a whole gathering of cultist performing" +
                " a dark ritual, seems like some kind of dark energy is protecting them from harm\nCombat Start!";
        intros[4] ="-----\nIn spite of your best efforts, the ritual succeeded and the powerful demon DeadLain appeared\nCombat Start!";
        while(player.level - 1 < encounters.length && Command.teamAlive(allies)){
            monsters = encounters[player.level-1];
            Command.Say(intros[player.level - 1]);
            boolean win = Command.fight(allies, monsters);
            Command.removeTauntsAfterCombat(allies);
            if (win){
                global_exp_counter = Command.postBattle(global_exp_counter, expTable, player, allies, monsters);
                if (possibleAllies.length > 0) {
                    allies = Command.recruit(player, allies, possibleAllies);
                    possibleAllies = Command.removeDuplicateArchetype(possibleAllies, allies[player.level - 1].archetype);
                }
            }
        }
        if (!Command.teamAlive(allies)) Command.Say("-----\nGAME OVER");
        else Command.Say("-----\nYou have beaten the game");
        Command.Say("Here is a summary of your party:");
        for (Character ally : allies){
            ally.info();
        }
    }
}