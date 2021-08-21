package unsw.loopmania.Helpers;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;

import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.Buildings.TowerBuilding;

import unsw.loopmania.Enemies.BasicEnemy;
import unsw.loopmania.Enemies.ZombieEnemy;
import unsw.loopmania.Npcs.FriendlyCombatNpc;
import unsw.loopmania.Npcs.MatthiasPierre;

public class WorldHelpers {
    /**
     * Check if the character is in a campfire's radius
     * @param character - the player character
     * @param buildings - list of all buildings in the world
     * @return boolean containing answer
     */
    public static boolean inCampfireRadius(Character character, List<Building> buildings) {
        for (Building building : buildings) {
            if (building.getType().equals("campfire") && ((CampfireBuilding )building).checkCharacterInRadius(character)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all towers that the character is within radius of
     * @param character - the player character
     * @param buildings - list of all buildings in the world
     * @return list containing towers
     */
    public static List<TowerBuilding> getTowers(Character character, List<Building> buildings) {
        List<TowerBuilding> result = new ArrayList<TowerBuilding>();
        for (Building building : buildings) {
            if (building.getType().equals("tower") && ((TowerBuilding )building).checkRadius(character.getX(), character.getY())) {
                result.add((TowerBuilding )building);
            }
        }
        return result;
    }

    /**
     * Get all friendly npcs that the character is within the radius of
     * @param character - the player character
     * @param friendlys - list of all friendly npcs in the world
     * @return list containing friendly npcs
     */
    public static List<FriendlyCombatNpc> getFriendlys(Character character, List<FriendlyCombatNpc> friendlys) {
        List<FriendlyCombatNpc> result = new ArrayList<FriendlyCombatNpc>();
        for (FriendlyCombatNpc friend : friendlys) {
            if (friend.checkSupportRadius(character.getX(), character.getY())) {
                result.add(friend);
            }
        }
        return result;
    }

    /**
     * Get the oldest enemy that the character is within battle radius of
     * @param character - the player character
     * @param enemies - list of all enemies in the world
     * @return the enemy object
     */
    public static BasicEnemy getMainEnemy(int x, int y, MatthiasPierre matthiasPierre,  List<BasicEnemy> enemies) {
        for (BasicEnemy enemy : enemies) {
            if (
                enemy.checkBattleRadius(x, y) && 
                (matthiasPierre == null || !matthiasPierre.checkRadius(enemy.getX(), enemy.getY()))
            ) {
                return enemy;
            }
        }
        return null;
    }

    /**
     * Get all enemies that the character is within support radius of
     * not including the main enemy
     * @param character - the player character
     * @param mainEnemy - the main enemy being battled
     * @param enemies - list of all enemies in the world
     * @return list containing supporting enemies
     */
    public static List<BasicEnemy> getSupportingEnemies(int x, int y, MatthiasPierre matthiasPierre, BasicEnemy mainEnemy, List<BasicEnemy> enemies) {
        List<BasicEnemy> result = new ArrayList<BasicEnemy>();
        for (BasicEnemy enemy : enemies) {
            if (
                enemy.checkSupportRadius(x, y) && enemy != mainEnemy &&
                (matthiasPierre == null || !matthiasPierre.checkRadius(enemy.getX(), enemy.getY()))
            ) {
                enemy.changePosition(mainEnemy.returnPosition());
                result.add(enemy);
            }
        }
        return result;
    }

    /**
     * Replace allied soldier with zombie with the same health
     * @param character - the player character
     * @param orderedPath - the world's path
     * @param alliedSoldiers - list of allied soldiers
     * @param enemies - list of all enemies in the world
     * @return the zombie
     */
    public static ZombieEnemy turnAlliedSoldier(Character character, List<Pair<Integer, Integer>> orderedPath, List<AlliedSoldier> alliedSoldiers, List<BasicEnemy> enemies) {
        AlliedSoldier soldier = alliedSoldiers.remove(0);
        Pair<Integer, Integer> characterCoords = new Pair<Integer, Integer>(character.getX(), character.getY());
        ZombieEnemy zombieSoldier = new ZombieEnemy(new PathPosition(orderedPath.indexOf(characterCoords), orderedPath));
        zombieSoldier.setHealth(soldier.getHealth());
        enemies.add(zombieSoldier);
        return zombieSoldier;
    }
}
