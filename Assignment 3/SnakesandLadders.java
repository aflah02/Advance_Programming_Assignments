import java.util.*;

public class SnakesandLadders {
    final floor[] floorTracker;
    final player player;
    final Dice dice;
    SnakesandLadders(String name){
        floorTracker = new floor[14];
        floorTracker[0] = new normalFloor(0);
        floorTracker[1] = new normalFloor(1);
        floorTracker[2] = new elevatorFloor(2);
        floorTracker[3] = new normalFloor(3);
        floorTracker[4] = new normalFloor(4);
        floorTracker[5] = new normalSnakeFloor(5);
        floorTracker[6] = new normalFloor(6);
        floorTracker[7] = new normalFloor(7);
        floorTracker[8] = new normalLadderFloor(8);
        floorTracker[9] = new normalFloor(9);
        floorTracker[10] = new normalFloor(10);
        floorTracker[11] = new cobraFloor(11);
        floorTracker[12] = new normalFloor(12);
        floorTracker[13] = new normalFloor(13);
        player = new player(name);
        dice = new Dice(2);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the player name and hit enter: ");
        String name = sc.nextLine();
        SnakesandLadders snakesandLadders = new SnakesandLadders(name);
        snakesandLadders.gameReady();
        snakesandLadders.newTurn(sc, snakesandLadders.dice);
    }
    private void gameReady(){
        System.out.println("The game setup is ready");
    }
    private void newTurn(Scanner sc, Dice dice){
        System.out.println("Hit enter to roll the dice");
        String enter = sc.nextLine();
        dice.roll();
        int value = dice.getFaceValue();

    }
}
abstract class floor{
    protected int location;
    floor(int location){
        this.location = location;
    }
    public abstract void move(player P);
}

abstract class snakeFloor extends floor{
    snakeFloor(int location) {
        super(location);
    }

    public abstract void decreasePoints(player p);
}

class cobraFloor extends snakeFloor{

    cobraFloor(int location) {
        super(location);
    }

    @Override
    public void move(player P) {
        P.setPosition(P.getPosition()-8);
    }

    @Override
    public void decreasePoints(player P) {
        P.setPoints(P.getPoints()-4);
    }
}

class normalSnakeFloor extends snakeFloor{

    normalSnakeFloor(int location) {
        super(location);
    }

    @Override
    public void move(player P) {
        P.setPosition(P.getPosition()-4);
    }

    @Override
    public void decreasePoints(player P) {
        P.setPoints(P.getPoints()-2);
    }
}

class normalFloor extends floor{

    normalFloor(int location) {
        super(location);
    }

    @Override
    public void move(player P) {
        P.setPosition(P.getPosition()+1);
    }

    public void increasePoints(player P) {
        P.setPoints(P.getPoints()+1);
    }

}

abstract class ladderFloor extends floor{
    ladderFloor(int location) {
        super(location);
    }

    public abstract void increasePoints(player p);
}

class elevatorFloor extends ladderFloor{

    elevatorFloor(int location) {
        super(location);
    }

    @Override
    public void move(player P) {
        P.setPosition(P.getPosition()+8);
    }

    @Override
    public void increasePoints(player P) {
        P.setPoints(P.getPoints()+4);
    }
}

class normalLadderFloor extends ladderFloor{

    normalLadderFloor(int location) {
        super(location);
    }

    @Override
    public void move(player P) {
        P.setPosition(P.getPosition()+4);
    }

    @Override
    public void increasePoints(player P) {
        P.setPoints(P.getPoints()+2);
    }
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

    public int getFaceValue() {
        return faceValue;
    }
}

class player{
    private final String name;
    private int position;
    private int points;
    player(String name) {
        this.name = name;
        this.position = 0;
        this.points = 1;
    }
    public void setPosition(int pos){
        this.position = pos;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}