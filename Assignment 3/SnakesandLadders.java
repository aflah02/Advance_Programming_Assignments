import java.util.*;

final public class SnakesandLadders {
    private final String ANSI_RESET;
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
        dice = new Dice();
        points = 0;
        ANSI_RESET = "\u001B[0m";
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
        System.out.println();
        System.out.println("""
                            Would you like to gamble your current points for a chance at doubling them ? (Y or N)
                            Rules: 
                            1) If your points are > 0 then they'll be doubled else you'll get a +1 if you win if they were already less than or equal to 0
                            2) If your points are > 0 then they'll be turned to 0 if you lose else you'll get a -1 if they were already less than or equal to 0
                            You can keep playing till you lose""");
        String choice = sc.nextLine();
        if (choice.equals("Y")){
            while (choice.equals("Y")){
                int prePoints = snakesandLadders.points;
                snakesandLadders.gamblePoints(sc);
                System.out.println(snakesandLadders.player.getName() + " accumulated " + snakesandLadders.points + " points");
                if (snakesandLadders.points > prePoints){
                    System.out.println("""
                            Would you like to gamble your current points for a chance at doubling them ? (Y or N)
                            Rules: 
                            1) If your points are > 0 then they'll be doubled else you'll get a +1 if you win if they were already less than or equal to 0
                            2) If your points are > 0 then they'll be turned to 0 if you lose else you'll get a -1 if they were already less than or equal to 0
                            You can keep playing till you lose""");
                    choice = sc.nextLine();
                }
                else{
                    System.out.println("Thanks for Playing");
                    break;
                }
            }
        }
        else{
            System.out.println("Thanks for Playing");
            System.out.println();
        }
        System.out.println(snakesandLadders.player.getName() + " accumulated " + snakesandLadders.points + " points");
    }

    private void gamblePoints(Scanner sc){
        System.out.println("""
                Choose your bet:\s
                A for 1
                B for 2
                """);
        String bet = sc.nextLine();
        dice.roll();
        String rolled;
        if (dice.getFaceValue() == 1){
            rolled = "A";
        }
        else {
            rolled = "B";
        }
        if (rolled.equals(bet)){
            System.out.println("Congrats your points are now doubled");
            if (points > 0){
                points = 2*points;
            }
            else{
                points = points++;
            }
        }
        else{
            System.out.println("Better luck next time");
            if (points > 0){
                points = 0;
            }
            else{
                points = points--;
            }
        }
    }
    private void newTurn(Scanner sc, player player, Dice dice) {
        String ANSI_CYAN = "\u001B[36m";
        if (player.getPosition() == -1){
            while (player.getPosition() == -1){
                System.out.print(ANSI_CYAN + "Hit enter to roll the dice");
                String enter = sc.nextLine();
                dice.roll();
                int value = dice.getFaceValue();
                System.out.println("Dice gave " + value + ANSI_RESET);
                System.out.println();
                if (value == 1){
                    player.startGame();
                    setPoints(1);
                }
                else{
                    String ANSI_YELLOW = "\u001B[33m";
                    System.out.println(ANSI_YELLOW + "Game cannot start until you get 1" + ANSI_RESET);
                    System.out.println();
                }
            }
            printInfo();
        }
        else{
            System.out.print(ANSI_CYAN + "Hit enter to roll the dice" + ANSI_RESET);
            String enter = sc.nextLine();
            dice.roll();
            int value = dice.getFaceValue();
            if (player.getPosition() + value <= 13){
                System.out.println(ANSI_CYAN + "Dice gave " + value + ANSI_RESET);
                System.out.println();
            }
            String ANSI_PURPLE = "\u001B[35m";
            if (player.getPosition() + value > 13){
                System.out.println(ANSI_PURPLE + "Dice gave 2\n" +
                        "Player cannot move" + ANSI_RESET);
                System.out.println();
            }
            else{
                player.move(value);
                points = floorTracker[player.getPosition()].changePoints(points);
                printInfo();
                if (!floorTracker[player.getPosition()].floorType().equals("Empty")){
                    if (player.getPosition() + floorTracker[player.getPosition()].jumpBy() > 13){
                        System.out.println(ANSI_PURPLE + "Dice gave 2\n" +
                                "Player cannot move" + ANSI_RESET);
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
        System.out.println();
    }

    private void printInfo(){
        if (floorTracker[player.getPosition()].floorType().equals("Normal Snake") ||
                floorTracker[player.getPosition()].floorType().equals("King Cobra")){
            String ANSI_RED = "\u001B[31m";
            System.out.println(ANSI_RED + "Player position Floor-" + player.getPosition());
            System.out.println(player.getName() + " has reached an " +
                    floorTracker[player.getPosition()].floorType() + " Floor");
            System.out.println("Total points " + getPoints());
            System.out.println(ANSI_RESET);
        }
        else if (floorTracker[player.getPosition()].floorType().equals("Elevator") ||
                floorTracker[player.getPosition()].floorType().equals("Ladder")){
            String ANSI_BLUE = "\u001B[34m";
            System.out.println(ANSI_BLUE + "Player position Floor-" + player.getPosition());
            System.out.println(player.getName() + " has reached an " +
                    floorTracker[player.getPosition()].floorType() + " Floor");
            System.out.println("Total points " + getPoints());
            System.out.println(ANSI_RESET);
        }
        else{
            String ANSI_GREEN = "\u001B[32m";
            System.out.println(ANSI_GREEN + "Player position Floor-" + player.getPosition());
            System.out.println(player.getName() + " has reached an " +
                    floorTracker[player.getPosition()].floorType() + " Floor");
            System.out.println("Total points " + getPoints());
            System.out.println(ANSI_RESET);
        }

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
    protected int decreasePointsBy;
    protected int decreaseLocationBy;
    snakeFloor(int location, int decreasePointsBy, int decreaseLocationBy) {
        super(location);
        this.decreasePointsBy = decreasePointsBy;
        this.decreaseLocationBy = decreaseLocationBy;
    }
    public abstract int reducePoints(int currPoints, int reduceBy);


}

final class cobraFloor extends snakeFloor{

    cobraFloor(int location) {
        super(location, -4, -8);
    }

    @Override
    public int reducePoints(int currPoints, int reduceBy) {
        return currPoints + reduceBy;
    }

    @Override
    public void move(player P) {
        P.setPosition(P.getPosition() + this.getDecreaseLocationBy());
    }

    @Override
    public String floorType() {
        return "King Cobra";
    }

    @Override
    public int changePoints(int currPoints) {
        return reducePoints(currPoints, this.getDecreasePointsBy());
    }

    @Override
    public int jumpBy() {
        return this.getDecreaseLocationBy();
    }

    public int getDecreasePointsBy() {
        return decreasePointsBy;
    }

    public int getDecreaseLocationBy() {
        return decreaseLocationBy;
    }
}

final class normalSnakeFloor extends snakeFloor{

    normalSnakeFloor(int location) {
        super(location, -2, -4);
    }

    @Override
    public int reducePoints(int currPoints, int reduceBy) {
        return currPoints + reduceBy;
    }

    @Override
    public void move(player P) {
        P.setPosition(P.getPosition() + this.getDecreaseLocationBy());
    }

    @Override
    public String floorType() {
        return "Normal Snake";
    }

    @Override
    public int changePoints(int currPoints) {
        return reducePoints(currPoints, this.getDecreasePointsBy());
    }

    @Override
    public int jumpBy() {
        return this.getDecreaseLocationBy();
    }

    public int getDecreasePointsBy() {
        return decreasePointsBy;
    }

    public int getDecreaseLocationBy() {
        return decreaseLocationBy;
    }
}

final class normalFloor extends floor{

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
    protected int increasePointsBy;
    protected int increaseLocationBy;
    ladderFloor(int location, int increasePointsBy, int increaseLocationBy) {
        super(location);
        this.increasePointsBy = increasePointsBy;
        this.increaseLocationBy = increaseLocationBy;
    }
    public abstract int increaseBy(int currPoints, int reduceBy);
}

final class elevatorFloor extends ladderFloor{

    elevatorFloor(int location) {
        super(location, 4, 8);
    }

    @Override
    public int increaseBy(int currPoints, int increasePointsBy) {
        return currPoints + increasePointsBy;
    }

    @Override
    public void move(player P) {
        P.setPosition(P.getPosition() + this.getIncreaseLocationBy());
    }

    @Override
    public String floorType() {
        return "Elevator";
    }

    @Override
    public int changePoints(int currPoints) {
        return increaseBy(currPoints, this.getIncreasePointsBy());
    }

    @Override
    public int jumpBy() {
        return 4;
    }

    public int getIncreasePointsBy() {
        return increasePointsBy;
    }

    public int getIncreaseLocationBy() {
        return increaseLocationBy;
    }
}

final class normalLadderFloor extends ladderFloor{

    normalLadderFloor(int location) {
        super(location, 2, 4);
    }

    @Override
    public void move(player P) {
        P.setPosition(P.getPosition()+this.getIncreaseLocationBy());
    }

    @Override
    public String floorType() {
        return "Ladder";
    }

    @Override
    public int changePoints(int currPoints) {
        return increaseBy(currPoints, this.getIncreasePointsBy());
    }

    @Override
    public int jumpBy() {
        return this.getIncreaseLocationBy();
    }

    @Override
    public int increaseBy(int currPoints, int increasePointsBy) {
        return currPoints + increasePointsBy;
    }

    public int getIncreasePointsBy() {
        return increasePointsBy;
    }

    public int getIncreaseLocationBy() {
        return increaseLocationBy;
    }
}

final class Dice {
    private final int numFaces;
    private int faceValue;

    public Dice() {
        numFaces = 2;
    }

    public void roll() {
        Random r = new Random();
        int curr_faceValue = 1 + r.nextInt(numFaces);
        setFaceValue(curr_faceValue);
    }

    private void setFaceValue(int value) {
        faceValue = value;
    }

    public int getFaceValue() {
        return faceValue;
    }
}

final class player{
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