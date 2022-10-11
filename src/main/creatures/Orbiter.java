package main.creatures;

import main.world.World;

import java.util.ArrayList;
import java.util.Random;

public class Orbiter extends Creature{
    /**
     * Assigns the Orbiter to a random position within anywhere in the Test.world that is on an edge (not center space)
     * @param numLevels: number of floors in the Test.world
     * @param width: number of rooms in the x axis
     * @param depth: number of rooms in teh y axis
     */
    public Orbiter(int numLevels, int width, int depth) {
        super(numLevels, width, depth);                 // Need to call super to instantiate variables
        setLevel(new Random().nextInt(numLevels) + 1); // Need to add 1 so that 0 never gets chosen
        setY(new Random().nextInt(depth));
        setX(new Random().nextInt(width));
        if(getX() == 1 && getY() == 1){
            setX(new Random().nextInt(2));
            setY(new Random().nextInt(2));
        }
        setName("Orbiter");
    }

    /**
     * The orbiter's move:
     * @param w - this is the Test.world so they can make sure they move along edges per their description. They will choose to either move clockwise
     *          or counterclockwise at random but will never move to the middle of their level.
     */
    @Override
    public void move(World w){

        //TODO: Add check that we are not currently in room with an Adventurer
        w.getRoom(getLevel(), getY(), getX()).remove(this);
        ArrayList<Integer> possMoves = new ArrayList<Integer>();
        Boolean atWall = false;
        if((getX() < getWidth() - 1 && getY() == getDepth() - 1) || (getX() < getWidth() -1  && getY() == 0)){
            possMoves.add(2); //Go East
        }
        else{  //Furthest East possible
            atWall = true;
        }
        if((getX() > 0 && getY() == getDepth() - 1) || (getX() > 0 && getY() == 0)){
            possMoves.add(3); //Go West
        }
        else{ // Furthest West possible
            atWall = true;
        }

        if(atWall){
            if(getY() < getDepth() - 1){ //Go North
                possMoves.add(1);
            }
            else{ //Go south
                possMoves.add(0);
            }
        }
        // N = 0 , S = 1 ; E = 2, W = 3 ; UP = 4, DOWN = 5
        int randomMoveIndex = new Random().nextInt(possMoves.size()); //Chooses random direction to move from possible
        int randomMove = possMoves.get(randomMoveIndex);
        if(randomMove == 0){ //Move North
            setY(getY() - 1);
        }
        if(randomMove == 1){ //Move South
            setY(getY() + 1);
        }
        if(randomMove == 2){ //Move East
            setX(getX() + 1);
        }
        if(randomMove == 3){ //Move West
            setX(getX() - 1);
        }
        w.getRoom(getLevel(), getY(), getX()).addCreature(this);
        w.observer.move_event(this, this.getPos());
    }
}
