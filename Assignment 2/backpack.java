import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

public class backpack {
    ArrayList<instructor> instructorTracker;
    ArrayList<student> studentTracker;
    HashSet<assessments> assessmentTracker;
    HashSet<LectureMaterial> lectureMaterialTracker;
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
        if (option == 3){
            System.out.println("-------------------------------------------------------------------------------------------------");
        }
        else if (option == 2){
            backpack.printStudentMenu();
        }
        else{
            backpack.showInstructors();
            System.out.print("Choose id: ");
            int instructor_number = backpack.sc.nextInt();
            instructor inst = backpack.instructorTracker.get(instructor_number);
            System.out.println("Welcome " + inst.getName());
            backpack.printInstructorMenu();
            int menu_option = backpack.sc.nextInt();
            if (menu_option == 1) {
                System.out.println("1. Add Lecture Slide\n" +
                        "2. Add Lecture Video");
                int material_type = backpack.sc.nextInt();
                backpack.sc.nextLine();
                if (material_type == 1){
                    System.out.println("Enter topic of slides: ");
                    String slideTopic = backpack.sc.nextLine();
                    int slideCount = backpack.sc.nextInt();
                    backpack.sc.nextLine();
                    System.out.println("Enter content of slides");
                    ArrayList<String> slideContent = new ArrayList<>();
                    for (int i = 0; i < slideCount; i++){
                        System.out.print("Content of slide " + (i+1) + ":");
                        slideContent.add(backpack.sc.nextLine());
                    }
                    LocalDate currentTime = LocalDate.now();
                    backpack.lectureMaterialTracker.add(new LectureSlides(slideTopic, slideContent, currentTime));
                }
                else if (material_type == 2){
                    System.out.print("Enter topic of video: ");
                    String videoTopic = backpack.sc.nextLine();
                    System.out.print("Enter filename of video: ");
                    String fileName = backpack.sc.nextLine();
                    int fileNameLength = fileName.length();
                    LocalDate currentTime = LocalDate.now();
                    if (fileName.substring(fileNameLength-4, fileNameLength-1).equals(".mp4")){
                        backpack.lectureMaterialTracker.add(new LectureRecordings(videoTopic, fileName, currentTime));
                    }
                    else{
                        System.out.println("Invalid File Format, Please only Upload Files with .mp4 Extension");
                    }
                }
            }

        }
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
interface assessments{
    public void addQuestion();
    public void setMaxMarks();
}

class quiz implements assessments{

    @Override
    public void addQuestion() {

    }

    @Override
    public void setMaxMarks() {

    }
}

class assignment implements assessments{

    @Override
    public void addQuestion() {

    }

    @Override
    public void setMaxMarks() {

    }
}

interface LectureMaterial{
    public void setTitle(String title);
    public void setTime(LocalDate currentTime);
}

class LectureSlides implements LectureMaterial{

    private String title;
    private ArrayList<String> slideContent;
    private LocalDate uploadDate;

    LectureSlides(String title, ArrayList<String> slideContent, LocalDate uploadDate){
        setTitle(title);
        setContents(slideContent);
        setTime(uploadDate);
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(ArrayList<String> content) {
        this.slideContent = content;
    }

    @Override
    public void setTime(LocalDate currentTime) {
        this.uploadDate = currentTime;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getSlideContent() {
        return slideContent;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }
}

class LectureRecordings implements LectureMaterial{

    private String title;
    private String fileName;
    private LocalDate uploadDate;

    public LectureRecordings(String videoTopic, String fileName, LocalDate uploadDate) {
        setTitle(videoTopic);
        setFileName(fileName);
        setTime(uploadDate);
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setTime(LocalDate currentTime) {
        this.uploadDate = currentTime;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
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
    private final String name;
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
    private void addClassMaterial(){

    }
    private void addAssessment(){

    }
    private void gradeAssessments(){

    }
    private void closeAssessments(){

    }
    public String getName() {
        return name;
    }
}

class student implements user{
    private final String name;
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
    private void submitAssessments(){

    }
    private void viewGrades(){

    }
    public String getName() {
        return name;
    }
}
