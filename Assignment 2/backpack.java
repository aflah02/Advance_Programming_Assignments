import java.util.ArrayList;
import java.util.Scanner;

public class backpack {
    ArrayList<instructor> instructorTracker;
    ArrayList<student> studentTracker;
    Scanner sc;
    backpack(){
        this.instructorTracker = new ArrayList<>();
        this.studentTracker = new ArrayList<>();
        this.sc = new Scanner(System.in);
        instructorTracker.add(new instructor("I0"));
        instructorTracker.add(new instructor("I1"));
        studentTracker.add(new student("S0"));
        studentTracker.add(new student("S1"));
        studentTracker.add(new student("S2"));
    }
    public static void main(String[] args) {
        backpack backpack = new backpack();
        backpack.printStartMenu();
        int option = backpack.sc.nextInt();
        backpack.showInstructors();
        backpack.showStudents();
    }
    private void showInstructors(){
        System.out.println("Instructors");
        for (int i = 0; i < instructorTracker.size(); i++){
            System.out.println(i + " - " + instructorTracker.get(i).getName());
        }
    }
    private void showStudents(){
        System.out.println("Students");
        for (int i = 0; i < studentTracker.size(); i++){
            System.out.println(i + " - " + studentTracker.get(i).getName());
        }
    }
    private void printStartMenu(){
        System.out.println("""
                Welcome to Backpack
                1. Enter as instructor
                2. Enter as student
                3. Exit""");
    }
    private void printInstructorMenu(){
        System.out.println("""
                INSTRUCTOR MENU
                1. Add class material
                2. Add assessments
                3. View lecture materials
                4. View assessments
                5. Grade assessments
                6. Close assessment
                7. View comments
                8. Add comments
                9. Logout
                """);
    }
    private void printStudentMenu(){
        System.out.println("""
                STUDENT MENU
                1. View lecture materials
                2. View assessments
                3. Submit assessment
                4. View grades
                5. View comments
                6. Add comments
                7. Logout""");
    }
}

interface user{
    public void login();
    public void logout();
    public void viewComments();
    public void addComments();
    public void viewLectureMaterials();
    public void viewAssessments();
}

class instructor implements user{
    private String name;
    private boolean isLoggedin;
    instructor(String name){
        this.isLoggedin = false;
        this.name = name;
    }
    @Override
    public void login() { this.isLoggedin = true; }
    @Override
    public void logout() { this.isLoggedin = false; }
    @Override
    public void viewComments() {

    }
    @Override
    public void addComments() {

    }
    @Override
    public void viewLectureMaterials() {

    }
    @Override
    public void viewAssessments() {

    }

    public String getName() {
        return name;
    }
}

class student implements user{
    private String name;
    private boolean isLoggedin;
    student(String name){
        this.isLoggedin = false;
        this.name = name;
    }
    @Override
    public void login() { this.isLoggedin = true; }
    @Override
    public void logout() { this.isLoggedin = false; }
    @Override
    public void viewComments() {

    }
    @Override
    public void addComments() {

    }
    @Override
    public void viewLectureMaterials() {

    }
    @Override
    public void viewAssessments() {

    }

    public String getName() {
        return name;
    }
}
