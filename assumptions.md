# Assumptions

## Combat System
1. Maximum of 4 allies soldiers

Format: Allies v Enemies
e.g. 1 v 1 = character v single enemy
Turn based system

2. 1 v 1: Character attacks first (turn 1), then enemy attacks (turn 2), repeat
3. 1 v many: Character attacks (turn 1) then all enemies individually attack (turn 2), repeat
4. many v 1: Character and allies all attack once each (turn 1) then enemy can attack once (turn 2), repeat
5. many v many: Character and allies all attack once each (turn 1) then all enemies can attack once each (turn 2), repeat
6. When a allied solider is turned into a zombie, their remaining hp is unchanged and they don't attack until next turn
7. Stronger enemies have a higher percentage chance of a critical strike
8. All enemies within the support radius of an enemy will join fight straight away. No buffer time for them to reach the path
9. Character will have a base health of 20
10. Towers will attack before enemies can
11. Enemies must kill alliedSoldiers before they can attack the main character
12. Damage multipliers will apply before critical damage

## Interface
1. Inventory can store at most 16 unequipped items
2. 8 cards can be stored in total at one time
3. Able to equip 4 items at one time
4. Potions are not stackable
5. Specific slot for weapons, armour, shield and helmet


## Interaction with map
1. To pick up loot from the ground, the character model must walk over it
2. Character moves one tile at a tile. Must complete all battles and looting on current tile before it can move to the next one.
3. For goals not including loops of map, as soon as the gold and experience goal are reached, they instantly win and do not need to return to the start to win.
4. Losing the zombie and vampire building cards will punishment the character. Lose gold, experience, items or health. This is to incentivise using them.]
5. Using enemy building cards should reward players with more/better loot
6. Multiple enemies can stand on the same pathTile
7. Supporting enemies will go to the same pathTile as the attackingEnemy when a battle starts


## Economy/Items
1. Stronger enemies will drop more loot
2. Stronger enemies will grant more XP
3. Around 1(strong)-3(weak) enemies worth of gold for an item
4. Character is able to sell items at the shop but at a price that is lower than what the item is purchased for.
5. When a card is lost due to having too many, the better the card the more gold, experience and better items received.
6. The One Ring will respawn the character on the current tile, not at the hero's castle
7. The One Ring does not need to be equipped for its effect to work
8. Enemies will drop a random amount of gold between 30-50
9. Loot from killing enemies is automatically placed within the inventory


## Keybinds
1. Press H to drink a potion
2. Press ESC to exit the shop window


## Buildings
1. Spawn radius of vampire castle is larger than zombie castle and spawns less enemies in one go (balance out vampire's superior stats)
2. Tower will attack after both character and enemy team have attacked once
3. Village also regerates health for the allied soldiers
4. Allies also deal double damage when in the radius of a campfire
5. Adjacent means a difference of 1 for one of the coordinates
6. Vampire Castle will spawn 1-3 vampires every five turns
7. Vampire will run away from the closet campfire 

## Enemy Behaviour
1. Zombie moves slowly by having a movement opportunity once every 2 turns.
2. Vampire cannot obtain more crits when it has any stored.
3. Vampire will move in the direction which maximises the L2 distance between itself and any campfires which exist.
4. When there are multiple campfires the vampire will only consider the closest.
5. If both options are equidistant from the campfire, the vampire will choose randomly.
6. If the current tile is further away from the campfire than the other two options then it will remain still.
5. Zombie Pit will spawn 1-2 zombies each turn

## Extra Assumptions
- euclidian distance is used for battle and support radii
- amount and type of item received from excess cards is semi-random
- adjacent to path means difference of 1 for either x or y (ie. no diagonal)
- vampire cannot get any more crits when it has any store
- zombie has a movement opportunity once every 2 turns
- turned allied soldiers don't attack on the turn they are turned.

- In the case of a campfire, the vampire will move in the direction which maximises the l2 distance
between itself and the nearest campfire.
- Vampire will only ever consider the current closest campfire.
- In the case where two campfires have the same distance from the vampire, the earliest one will be run away from.
- In the case of a campfire, if both directions are equidistant from the campfire then a vampire will choose
a random path to move to.
- In the case of a campfire if the current distance is further or eqidistant than both options then it will remain still.

- character has 100 health
- character has 5 base attack

- allied soldier has 35 health
- allied soldier has 10 damage

- tower does 10 damage

- trap does 20 damage

- slug has 20 health
- slug has 5 attack
- slug has battle radius of 2
- slug has support radius of 2
- slug gives 20 exp

- zombie has 30 health
- zombie has 15 damage
- zombie has a battle radius of 3
- zombie has a support radius of 3
- zombie gives 30 exp

- vampire has 50 health
- vampire has 30 damage
- vampire does 0 - 10 additional damage
- vampire has 1 - 3 crits
- vampire has battle radius of 3
- vampire has support radius of 5
- vampire gives 50 exp

- enemy drops 30-50 gold

- 2% rare

- health potion restores 40 health
- health potion costs 50 gold

- sword does 15 additional damage
- sword costs 200 gold

- stake does 5 additional damage
- stake does 35 additional damage to vampires
- stake does 200 gold

- staff does 2 additional damage
- staff has 30% chance for trance
- staff costs 200

- armour costs 300

- shield reduces damage by 20%
- shield costs 200

- helmet cost 150
- reduces damage by 5
- reduces our damage by 10%

- replaced card drops 30 - 50 gold
- replaced card drops 20 - 40 exp
- replaced card has 40% chance of giving an item
