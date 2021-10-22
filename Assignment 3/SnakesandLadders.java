import java.util.*;

public class SnakesandLadders {
    final private floor[] floorTracker;
    final private player player;
    final private Dice dice;
    private int points;
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
        points = 0;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the player name and hit enter: ");
        String name = sc.nextLine();
        SnakesandLadders snakesandLadders = new SnakesandLadders(name);
        snakesandLadders.gameReady();
        while (snakesandLadders.player.getPosition() != 13){
            snakesandLadders.newTurn(sc, snakesandLadders.player, snakesandLadders.dice);
        }
        System.out.println("Game over");
        System.out.println(snakesandLadders.player.getName() + " accumulated " + snakesandLadders.points + " points");
    }

    private void newTurn(Scanner sc, player player, Dice dice) {
        if (player.getPosition() == -1){
            while (player.getPosition() == -1){
                System.out.print("Hit enter to roll the dice");
                String enter = sc.nextLine();
                dice.roll();
                int value = dice.getFaceValue();
                System.out.println("Dice gave " + value);
                if (value == 1){
                    player.startGame();
                    setPoints(1);
                }
                else{
                    System.out.println("Game cannot start until you get 1");
                }
            }
            printInfo();
        }
        else{
            System.out.print("Hit enter to roll the dice");
            String enter = sc.nextLine();
            dice.roll();
            int value = dice.getFaceValue();
            if (player.getPosition() + value <= 13){
                System.out.println("Dice gave " + value);
            }
            if (player.getPosition() + value > 13){
                System.out.println("Dice gave 2\n" +
                        "Player cannot move");
            }
            else{
                player.move(value);
                points = floorTracker[player.getPosition()].changePoints(points);
                printInfo();
                if (!floorTracker[player.getPosition()].floorType().equals("Empty")){
                    if (player.getPosition() + floorTracker[player.getPosition()].jumpBy() > 13){
                        System.out.println("Dice gave 2\n" +
                                "Player cannot move");
                    }
                    else{
                        floorTracker[player.getPosition()].move(player);
                        points = floorTracker[player.getPosition()].changePoints(points);
                        printInfo();
                    }
                }
            }
        }
    }
    private int getPoints() {
        return points;
    }
    private void setPoints(int points){
        this.points = points;
    }
    private void gameReady(){
        System.out.println("The game setup is ready");
    }
    private void printInfo(){
        System.out.println("Player position Floor-" + player.getPosition());
        System.out.println(player.getName() + " has reached an " +
                floorTracker[player.getPosition()].floorType() + " Floor");
        System.out.println("Total points " + getPoints());
        System.out.println();
    }
}
abstract class floor{
    protected int location;
    floor(int location){
        this.location = location;
    }
    public abstract void move(player P);
    public abstract String floorType();
    public abstract int changePoints(int currPoints);
    public abstract int jumpBy();
}

abstract class snakeFloor extends floor{
    snakeFloor(int location) {
        super(location);
    }
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
    public String floorType() {
        return "King Cobra";
    }

    @Override
    public int changePoints(int currPoints) {
        return currPoints-4;
    }

    @Override
    public int jumpBy() {
        return -4;
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
    public String floorType() {
        return "Normal Snake";
    }

    @Override
    public int changePoints(int currPoints) {
        return currPoints - 2;
    }

    @Override
    public int jumpBy() {
        return -2;
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

    @Override
    public String floorType() {
        return "Empty";
    }

    public int changePoints(int currPoints) {
        return currPoints + 1;
    }

    @Override
    public int jumpBy() {
        return 1;
    }

}

abstract class ladderFloor extends floor{
    ladderFloor(int location) {
        super(location);
    }
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
    public String floorType() {
        return "Elevator";
    }

    @Override
    public int changePoints(int currPoints) {
        return currPoints + 4;
    }

    @Override
    public int jumpBy() {
        return 4;
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
    public String floorType() {
        return "Ladder";
    }

    @Override
    public int changePoints(int currPoints) {
        return currPoints + 2;
    }

    @Override
    public int jumpBy() {
        return 2;
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
        Random r = new Random();
        int curr_faceValue = 1 + r.nextInt(numFaces);
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
    player(String name) {
        this.name = name;
        this.position = -1;
    }
    public void setPosition(int pos){
        this.position = pos;
    }
    public void move(int by){
        this.position += by;
    }
    public void startGame(){
        position = 0;
    }
    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}