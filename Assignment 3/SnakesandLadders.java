import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SnakesandLadders {
    ArrayList<floor> floorTracker;
    player player;
    SnakesandLadders(String name){
        floorTracker = new ArrayList<>();
        floorTracker.add(new normalfloor());
        floorTracker.add(new normalfloor());
        floorTracker.add(new ladderfloor());
        floorTracker.add(new normalfloor());
        floorTracker.add(new normalfloor());
        floorTracker.add(new snakefloor());
        floorTracker.add(new normalfloor());
        floorTracker.add(new normalfloor());
        floorTracker.add(new ladderfloor());
        floorTracker.add(new normalfloor());
        floorTracker.add(new normalfloor());
        floorTracker.add(new snakefloor());
        floorTracker.add(new normalfloor());
        floorTracker.add(new normalfloor());
        player = new player(name);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the player name and hit enter: ");
        String name = sc.nextLine();
        SnakesandLadders snakesandLadders = new SnakesandLadders(name);
        snakesandLadders.gameReady();
        System.out.println("Hit enter to roll the dice");
        String enter = sc.nextLine();
    }
    private void gameReady(){
        System.out.println("The game setup is ready");
    }
}
abstract class floor{
    private int points;
    private int leadsTo;
    public int getPoints(){
        return points;
    }
    public int nextPosition(){
        return leadsTo;
    }
}

class snakefloor extends floor{

}
class normalfloor extends floor{

}
class ladderfloor extends floor{

}
abstract class snake{

}
class normalsnake extends snake{

}
class kingcobra extends snake{

}
abstract class ladders{

}
class elevators extends ladders{

}
class normalladders extends ladders{

}

class Dice {
    private final int numFaces; //maximum face value
    private int faceValue; //current value showing on the dice

    // Constructor: Sets the initial face value.
    public Dice(int _numFaces) {
        numFaces = _numFaces;
        roll();
    }

    // Rolls the dice
    public void roll() {
        Random rand = null;
        int curr_faceValue = 1 + rand.nextInt(numFaces);
        setFaceValue(curr_faceValue);
    }

    // Face value setter/mutator.
    private void setFaceValue(int value) {
        if (value <= numFaces)
            faceValue = value;
    }
}

class player{
    private final String name;
    private int position;
    player(String name) {
        this.name = name;
        this.position = 0;
    }
    public void updatePosition(int pos){
        this.position = pos;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}