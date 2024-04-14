# SavalRPG
Java project for my Java course at PJATK
To play the game, you have to run the Play.java file

The rest of instructions that i couldnt fit into the game itself
----------
If you are asked by the program to input something and numbers appear before your options, for example:
1. x
2. y
5. z
You are to input the numbers, not the things that follow them

But if there arent numbers provided beforehand:
x
y
z
Input the word you want, capitalization does not matter

Certain effects that arent self explanatory:
Taunt - forces the character to use the first ability in their abilities array on the target that is taunting them
Divine charges - flat bonus to attack damage when used, user loses half of them at the beginning of their turn
Stack of protection - instead of taking damage user losses a stack of protection when possible

Items:
Healing potion - restores health to target equal to 8 times the item level 
Chill pill - restores 2 times the item level to target and removes Taunt effect from them
Bomb - deals damage to target equal to 11 times the item level 
Holy water - gives target divine charges equal to 2 times the item level
Plushie - gives target stacks of protection equal to 1 + half the item level rounded down
Red Powder - gives target % attack bonus equal to 10 times the item level for 2 turns
Yellow Powder - gives target % crit chance bonus equal to 5 times the item level for 2 turns

Misc things:
Use item ability - allows user to use one of its items
Throw a stone ability - "replaces" Use item ability when users inventory is empty, deals damage equal to user's level, so most of the time it's not worth using
