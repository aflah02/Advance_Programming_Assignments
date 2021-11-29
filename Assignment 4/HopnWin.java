import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class HopnWin {
    private final Bucket bucket;
    private final TileCarpet tileCarpet;
    private final Scanner sc1;
    private final Scanner sc2;
    private final GenericCalculator<String> stringGenericCalculator;
    private final GenericCalculator<Integer> integerGenericCalculator;
    HopnWin(){
        sc1 = new Scanner(System.in);
        sc2 = new Scanner(System.in);
        String[] names = new String[]{"Pikachu", "Ash", "Naruto", "Batman", "Superman", "Ninja Hattori",
                "Charizard", "Sasuke", "Kakashi", "Spongebob", "Master Chief", "Jonesy", "Shadow Monarch",
                "Aladdin", "Superman", "Ironman", "Hawkeye", "Bruce Lee", "Jackie Chan", "Ben Ten", "Shang Chi", "Peter Parker", "Flash"};
        ArrayList<String> arr = new ArrayList<String>(Arrays.asList(names));
        bucket = new Bucket();
        tileCarpet = new TileCarpet();
        for (int i = 0; i < 20; i++){
            String name = getRandomElement(arr);
            arr.remove(name);
            tileCarpet.addTile(new Tile(i,new SoftToy(name + " soft toy")));
        }
        this.stringGenericCalculator = new GenericCalculator<String>() {
            @Override
            public String solve(String itemOne, String itemTwo) {
                return itemOne+itemTwo;
            }
        };
        this.integerGenericCalculator = new GenericCalculator<Integer>() {
            @Override
            public Integer solve(Integer itemOne, Integer itemTwo) throws divisionByZero{
                return itemOne/itemTwo;
            }
        };

    }
    public static void main(String[] args) throws Exception {
        HopnWin hopnWin = new HopnWin();
        System.out.println("Hit enter to initialize the game");
        String enter = hopnWin.sc2.nextLine();
        System.out.println("Game is ready");
        System.out.println("Hit enter for your first hop");
        String next = hopnWin.sc2.nextLine();
        int hop = 1;
        while (hop <= 5){
            int jumpedTo = hopnWin.getRandomHop();
            System.out.println("You landed on tile " + jumpedTo);
            try{
                if ((jumpedTo)%2==0){
                    SoftToy won = hopnWin.tileCarpet.getTileSoftToyName(jumpedTo-1);
                    hopnWin.bucket.addSoftToy(won);
                    System.out.println("You won a " + won.getName());
                }
                else{
                    boolean done = false;
                    String choice = "";
                    while (!done){
                        System.out.println("Question answer round. Integer or strings?");
                        try{
                            choice = hopnWin.sc2.nextLine();
                            hopnWin.isChoiceValid(choice);
                            done = true;
                        } catch (InputMismatchException inp) {
                            System.out.println("Wrong Input Type, please enter a valid String");
                            System.out.println("Try Again");
                        } catch (choiceNotValid ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    boolean isDone = false;
                    while (!isDone){
                        if (choice.equals("integer")){
                            try{
                                int num1 = hopnWin.getRandomNumber();
                                int num2 = hopnWin.getRandomNumber();
                                isValid(num1, num2);
                                isDone = true;
                                System.out.println("Calculate the result of " + num1 + " divided by " + num2);
                                int ans = hopnWin.sc1.nextInt();
                                int calculatorAns = hopnWin.integerGenericCalculator.solve(num1,num2);
                                if (ans==calculatorAns){
                                    SoftToy won = hopnWin.tileCarpet.getTileSoftToyName(jumpedTo-1);
                                    hopnWin.bucket.addSoftToy(won);
                                    System.out.println("You won a " + won.getName());
                                }
                                else{
                                    System.out.println("Incorrect answer\n" +
                                            "You did not win any soft toy");
                                }
                            }
                            catch (divisionByZero ignored){
                            }
                            catch (CloneNotSupportedException cloneNotSupportedException){
                                cloneNotSupportedException.printStackTrace();
                            }
                    }
                        else if (choice.equals("string")){
                            try{
                                String s1 = hopnWin.genRandomString();
                                String s2 = hopnWin.genRandomString();
                                isDone = true;
                                System.out.println("Calculate the concatenation of strings " + s1 + " and " + s2);
                                String ans = hopnWin.sc2.nextLine();
                                String calculatorAns = hopnWin.stringGenericCalculator.solve(s1,s2);
                                if (ans.equals(calculatorAns)){
                                    SoftToy won = hopnWin.tileCarpet.getTileSoftToyName(jumpedTo-1);
                                    hopnWin.bucket.addSoftToy(won);
                                    System.out.println("You won a " + won.getName());
                                }
                                else{
                                    System.out.println("Incorrect answer\n" +
                                            "You did not win any soft toy");
                                }
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                }
            } catch (jumpedToMuddyPool jumpedToMuddyPool) {
                System.out.println(jumpedToMuddyPool.getMessage());
            }
            catch(CloneNotSupportedException cloneNotSupportedException){
                cloneNotSupportedException.printStackTrace();
            }
            hop++;
            if (hop == 2){
                System.out.println("Hit enter for your second hop");
                next = hopnWin.sc2.nextLine();
            }
            else if (hop == 3){
                System.out.println("Hit enter for your third hop");
                next = hopnWin.sc2.nextLine();
            }
            else if (hop == 4){
                System.out.println("Hit enter for your fourth hop");
                next = hopnWin.sc2.nextLine();
            }
            else if (hop == 5){
                System.out.println("Hit enter for your fifth hop");
                next = hopnWin.sc2.nextLine();
            }
        }
        try{
            System.out.println("Game over");
            hopnWin.bucket.printBucketContents();
        } catch (emptyBucketException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void isValid(int num1, int num2) throws divisionByZero{
        if (num2 == 0){
            throw new divisionByZero();
        }
    }

    public static <T> T getRandomElement(ArrayList<T> arr){
        return arr.get(ThreadLocalRandom.current().nextInt(arr.size()));
    }
    public int getRandomNumber(){
        Random random = new Random();
        return random.nextInt();
    }
    public int getRandomHop(){
        Random random = new Random();
        return 1+random.nextInt(21);
    }
    public String genRandomString(){
        String choiceofChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZqwertyuiopasdfghjklzxcvbnm";
        StringBuilder toReturn = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            toReturn.append(choiceofChars.charAt(random.nextInt(choiceofChars.length())));
        }
        return toReturn.toString();
    }
    public void isChoiceValid(String s) throws Exception{
        if (s.equals("integer")||s.equals("string")){
            return;
        }
        throw new choiceNotValid("Please enter a valid choice, the options are string or integer");
    }
}

final class Tile{
    private final int Pos;
    private final SoftToy softToy;

    public Tile(int pos, SoftToy softToy) {
        Pos = pos;
        this.softToy = softToy;
    }
    public SoftToy getSoftToyClone() throws CloneNotSupportedException {
        return softToy.clone();
    }
}

final class SoftToy implements Cloneable{
    private final String name;
    SoftToy(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public SoftToy clone() {
        try {
            return (SoftToy) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

final class TileCarpet{
    private final ArrayList<Tile> Tiles = new ArrayList<>();
    public void addTile(Tile tile){
        Tiles.add(tile);
    }
    public SoftToy getTileSoftToyName(int pos) throws jumpedToMuddyPool, CloneNotSupportedException {
        if (pos >= Tiles.size()){
            throw new jumpedToMuddyPool("You are too energetic and zoomed past all the tiles. Muddy Puddle Splash!");
        }
        return Tiles.get(pos).getSoftToyClone();
    }
}

final class Bucket{
    private final ArrayList<SoftToy> toysWon = new ArrayList<>();
    public void printBucketContents() throws emptyBucketException{
        System.out.println("Soft toys won by you are:");
        for (SoftToy softToy: toysWon){
            System.out.print(softToy.getName() + ", ");
        }
        if (toysWon.size()==0){
            throw new emptyBucketException("Bucket is Empty, No Prizes Won");
        }
    }
    public void addSoftToy(SoftToy softToy){
        toysWon.add(softToy);
    }
}
class emptyBucketException extends Exception{
    public emptyBucketException(String message){
        super(message);
    }
}
class jumpedToMuddyPool extends Exception{
    public jumpedToMuddyPool(String message){
        super(message);
    }
}
class choiceNotValid extends Exception{
    public choiceNotValid(String message){
        super(message);
    }
}
class divisionByZero extends Exception{
    public divisionByZero(){
        super();
    }
}
abstract class GenericCalculator<T>{
    public abstract T solve(T itemOne, T itemTwo) throws Exception;
}
