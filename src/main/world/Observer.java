package main.world;
import main.creatures.*;
import main.adventurers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import main.world.Logger;

import javax.swing.*;
import main.world.Tracker;
import main.world.object.Treasure;

/**
 * This class obsereves the world
 */
public class Observer {
    /*
    Observer Class with push implementation
    - Will be instantiated by World and publish on changes so that the subscribers
      can access these changes without querying the Test.world.
     */

    ArrayList<Adventurer> advMove = new ArrayList<Adventurer>();
    ArrayList<int[]> advPos = new ArrayList<>();

    ArrayList<Creature> creMove = new ArrayList<>();
    ArrayList<int[]> crePos = new ArrayList<>();
    public ArrayList<Logger> loggers = new ArrayList<>();
    ArrayList<Adventurer> celebrations = new ArrayList<Adventurer>();
    private Tracker tracker;
    public Observer(){
        this.tracker = new Tracker();

    }
    public Logger createLogger(int turnCount){
        Logger logger = new Logger(turnCount);
        this.loggers.add(logger);
        return logger;
    }
    public void updateTracker(World w){
        ArrayList<Adventurer> a = w.getAdventurers();
        ArrayList<String> aNames = new ArrayList<>();
        ArrayList<Integer> hp = new ArrayList<>();
        ArrayList<int[]> aPos = new ArrayList<>();

        ArrayList<ArrayList<String>> treasures = new ArrayList<>();

        ArrayList<Creature> c = w.getCreatures();
        ArrayList<String> cNames = new ArrayList<>();
        ArrayList<int[]> cPos = new ArrayList<>();

        for(int i = 0; i < a.size(); i++){
            if(a.get(i).getHealth() > 0) {
                aNames.add(a.get(i).getName());
                hp.add(a.get(i).getHealth());
                aPos.add(a.get(i).getPos());
                Hashtable<String, Treasure> tempTreasure = a.get(i).getTreasure();
                ArrayList<String> advTreasure = new ArrayList<>();
                for (String key : tempTreasure.keySet()) {
                    if (tempTreasure.get(key).isObtained()) {
                        advTreasure.add(key);
                    }
                }
                if(advTreasure.size() < 1) {
                    advTreasure.add("");
                }
                treasures.add(advTreasure);
            }
        }
        for(int i = 0; i < c.size(); i++){
            if(c.get(i).isAlive()) {
                cNames.add(c.get(i).getName());
                cPos.add(c.get(i).getPos());
            }

        }
//        tracker.treasures = w.getAdventurers().get(0).getTreasure().toString();
        tracker.setTurn(w.getTurn());
//        Updating adventurer info
        tracker.setAdventurers(aNames);
        tracker.setAdventurerHealths(hp);
        tracker.setAdventurerPositions(aPos);
        tracker.setTreasures(treasures);
//        Updating Creature info
        tracker.setCreatures(cNames);
        tracker.setCreaturePositions(cPos);
        tracker.log();
    }
    public void deleteLogger(Logger l){
        loggers.remove(l);
    }
    public void notify_subs(int eventNumber) {
        if(eventNumber == 0){
            for (int i = 0; i < loggers.size(); i++){
                loggers.get(i).movement(advMove.get(advMove.size()-1), advPos.get(advPos.size()-1));
            }
            //Character Moves
        }
        else if(eventNumber == 6){
            //Creature Moves
            for (int i = 0; i < loggers.size(); i++) {
                loggers.get(i).movement(creMove.get(creMove.size()-1), crePos.get(crePos.size()-1));
            }
        }
    }
    public void move_event(Adventurer a,int[] p) {
        advMove.add(a);
        advPos.add(p);
        notify_subs(0);
    }
    public void move_event(Creature c,int[] p) {
        creMove.add(c);
        crePos.add(p);
        notify_subs(6);
    }
    public void notifyCelebration(Adventurer a) {
        celebrations.add(a);
        notify_subs(2);
    }
    public void notifyCombat(Adventurer a, Creature c, boolean advWon){
        for (int i = 0; i < loggers.size(); i++) {
            loggers.get(i).combat(a.getName(), c.getName(), advWon);
        }
    }
    public void notifyHealthChange(Adventurer a, int h) {
        for (int i = 0; i < loggers.size(); i++) {
            loggers.get(i).healthChange(a, h);
        }
        if(a.getHealth() < 1){
            for (int i = 0; i < loggers.size(); i++) {
                loggers.get(i).defeated(a.getName());
            }
        }
    }
    public void treasureFound(Adventurer a, String treasure){
        for (int i = 0; i < loggers.size(); i++) {
            loggers.get(i).treasureFound(a.getName(), treasure);
        }
    }
}