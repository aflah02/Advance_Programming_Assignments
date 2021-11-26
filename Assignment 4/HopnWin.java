import java.math.BigInteger;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class HopnWin {
    private final Bucket bucket;
    private final TileCarpet tileCarpet;
    private final Scanner sc1;
    private final Scanner sc2;
    HopnWin(){
        sc1 = new Scanner(System.in);
        sc2 = new Scanner(System.in);
        String[] names = {"Pikachu", "Ash", "Naruto", "Batman", "Superman", "Ninja Hattori",
        "Charizard", "Sasuke", "Kakashi", "Spongebob", "Master Chief", "Jonesy", "Shadow Monarch",
        "Aladdin", "Superman", "Ironman", "Hawkeye", "Bruce Lee"};
        bucket = new Bucket();
        tileCarpet = new TileCarpet();
        for (int i = 0; i < 21; i++){
            tileCarpet.addTile(new Tile(i,new SoftToy(getRandomElement(names) + " Toy")));
        }
    }
    public static void main(String[] args) throws Exception {
        HopnWin hopnWin = new HopnWin();
        System.out.println("Hit enter to initialize the game");
        String enter = hopnWin.sc2.nextLine();
        System.out.println("Game is ready");
        String next = hopnWin.sc2.nextLine();
        int hop = 0;
        while (hop<5){
            int jumpedTo = hopnWin.getRandomNumber(1,21);
            try{
                if ((jumpedTo-1)%2==0){
                    SoftToy won = hopnWin.tileCarpet.getTileSoftToyName(jumpedTo-1);
                    hopnWin.bucket.addSoftToy(won);
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
                    if (choice.equals("integer")){
                        int num1 = hopnWin.getRandomNumber(Integer.MIN_VALUE, Integer.MAX_VALUE);
                        int num2 = hopnWin.getRandomNumber(Integer.MIN_VALUE, Integer.MAX_VALUE);
                        System.out.println("Calculate the result of " + num1 + " divided by " + num2);
                        int ans = hopnWin.sc1.nextInt();
                    }
                    else if (choice.equals("string")){
                        String s1 = hopnWin.genRandomString();
                        String s2 = hopnWin.genRandomString();
                        System.out.println("Calculate the concatenation of strings " + s1 + " and " + s2);
                        String ans = hopnWin.sc2.nextLine();
                    }


                }


            } catch (jumpedToMuddyPool jumpedToMuddyPool) {
                System.out.println(jumpedToMuddyPool.getMessage());
            }
            hop++;
        }
        try{
            hopnWin.bucket.printBucketContents();
        } catch (emptyBucketException e) {
            System.out.println(e.getMessage());
        }

    }
    public static <T> T getRandomElement(T[] arr){
        return arr[ThreadLocalRandom.current().nextInt(arr.length)];
    }
    public int getRandomNumber(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min) + min;
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
    public boolean isChoiceValid(String s) throws Exception{
        if (s.equals("integer")||s.equals("string")){
            return true;
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

    public SoftToy getSoftToy() {
        return softToy;
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
    public SoftToy getTileSoftToyName(int pos) throws jumpedToMuddyPool {
        if (pos >= Tiles.size()){
            throw new jumpedToMuddyPool("You are too energetic and zoomed past all the tiles. Muddy Puddle Splash!");
        }
        return Tiles.get(pos).getSoftToy().clone();
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
//final class GenericCalculator<T>{
//    GenericCalculator(){
//    }
//    public int divide(Object itemOne, Object itemTwo){
//        return itemOne/itemTwo;
//    }
//    public String concat(T itemOne, T itemTwo){
//        return (String)itemOne+(String)itemTwo;
//    }
//
//}