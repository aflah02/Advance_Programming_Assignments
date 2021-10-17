import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class backpack {
    private final ArrayList<instructor> instructorTracker;
    private final ArrayList<student> studentTracker;
    private final ArrayList<assessments> assessmentTracker;
    private final ArrayList<LectureMaterial> lectureMaterialTracker;
    private final ArrayList<comments> commentTracker;
    private int assessmentID;
    Scanner sc;
    backpack(){
        this.commentTracker = new ArrayList<>();
        this.instructorTracker = new ArrayList<>();
        this.studentTracker = new ArrayList<>();
        this.assessmentTracker = new ArrayList<>();
        this.lectureMaterialTracker = new ArrayList<>();
        this.assessmentID = 0;
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
        while (option != 3) {
            if (option == 2) {
                backpack.printStudentMenu();
            } else {
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
                    if (material_type == 1) {
                        System.out.print("Enter topic of slides: ");
                        String slideTopic = backpack.sc.nextLine();
                        System.out.print("Enter number of slides: ");
                        int slideCount = backpack.sc.nextInt();
                        backpack.sc.nextLine();
                        System.out.println("Enter content of slides");
                        ArrayList<String> slideContent = new ArrayList<>();
                        for (int i = 0; i < slideCount; i++) {
                            System.out.print("Content of slide " + (i + 1) + ":");
                            slideContent.add(backpack.sc.nextLine());
                        }
                        Date currentTime = new java.util.Date(System.currentTimeMillis());
                        backpack.addLectureSlides(slideTopic, slideContent, currentTime, inst);
                    } else if (material_type == 2) {
                        System.out.print("Enter topic of video: ");
                        String videoTopic = backpack.sc.nextLine();
                        System.out.print("Enter filename of video: ");
                        String fileName = backpack.sc.nextLine();
                        Date currentTime = new java.util.Date(System.currentTimeMillis());
                        backpack.addLectureRecording(videoTopic, fileName, currentTime, inst);
                    }
                } else if (menu_option == 2) {
                    System.out.println("1. Add Assignment\n" +
                            "2. Add Quiz");
                    int assessmentType = backpack.sc.nextInt();
                    backpack.sc.nextLine();
                    if (assessmentType == 1) {
                        System.out.print("Enter problem statement: ");
                        String problemStatement = backpack.sc.nextLine();
                        System.out.print("Enter max marks: ");
                        int maxMarks = backpack.sc.nextInt();
                        backpack.assessmentTracker.add(new assignment(problemStatement, maxMarks, backpack.assessmentID));
                        backpack.incrementAssessmentID();
                    } else if (assessmentType == 2) {
                        System.out.print("Enter quiz question: ");
                        String quizQuestion = backpack.sc.nextLine();
                        backpack.assessmentTracker.add(new quiz(quizQuestion, backpack.assessmentID));
                        backpack.incrementAssessmentID();
                    }
                }
                else if (menu_option == 3) {
                    for (LectureMaterial material : backpack.lectureMaterialTracker) {
                        if (material instanceof LectureSlides) {
                            backpack.printLectureSlide(material);
                        } else if (material instanceof LectureRecordings) {
                            backpack.printLectureRecording(material);
                        }
                        System.out.println();
                    }
                }
                else if (menu_option == 4){
                    backpack.showAssessments();
                }
                else if (menu_option == 5){
                    // TODO
                }
                else if (menu_option == 6){
                    System.out.println("List of Open Assignments:");
                    backpack.showAssessments();
                    System.out.print("Enter id of assignment to close: ");
                    int assignment_id = backpack.sc.nextInt();
                    for (assessments assessment: backpack.assessmentTracker) {
                        if (assessment instanceof assignment){
                            if (assignment_id == ((assignment) assessment).getAssessmentID()){
                                backpack.assessmentTracker.remove(assessment);
                                break;
                            }
                        }
                        else if (assessment instanceof quiz){
                            if (assignment_id == ((quiz) assessment).getAssessmentID()){
                                backpack.assessmentTracker.remove(assessment);
                                break;
                            }
                        }
                    }
                }
                else if (menu_option == 7){
                    for (comments comment: backpack.commentTracker){
                        if (comment.getUser() instanceof instructor){
                            System.out.println(comment.getComment() + " - " + ((instructor) comment.getUser()).getName());
                            System.out.println(comment.getUploadDate());
                        }
                        else if (comment.getUser() instanceof student){
                            System.out.println(comment.getComment() + " - " + ((student) comment.getUser()).getName());
                            System.out.println(comment.getUploadDate());
                        }
                        System.out.println();
                    }
                }
                else if (menu_option == 8){
                    System.out.print("Enter Comment: ");
                    String commentText = backpack.sc.nextLine();
                    Date currentTime = new java.util.Date(System.currentTimeMillis());
                    backpack.commentTracker.add(new comments(inst, currentTime, commentText));
                }
            }
            backpack.printStartMenu();
            option = backpack.sc.nextInt();
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
    }
    private void showAssessments(){
        for (assessments assessment: assessmentTracker) {
            if (assessment instanceof assignment){
                System.out.println("ID: " + ((assignment) assessment).getAssessmentID() + " Assignment: " + ((assignment) assessment).getProblemStatement() + " Max Marks: " + ((assignment) assessment).getMaxMarks());
            }
            else if (assessment instanceof quiz){
                System.out.println("ID: " + ((quiz) assessment).getAssessmentID() + " Assignment: " + ((quiz) assessment).getQuizQuestion());
            }
        }
    }
    private void incrementAssessmentID(){
        this.assessmentID++;
    }
    private void printLectureSlide(LectureMaterial material){
        System.out.println("Title: " + ((LectureSlides) material).getTitle());
        for (int i = 0; i < ((LectureSlides) material).getSlideContent().size(); i++){
            System.out.println("Slide " + (i+1) + ": " + ((LectureSlides) material).getSlideContent().get(i));
        }
        System.out.println("Date of upload: " + ((LectureSlides) material).getUploadDate());
        System.out.println("Uploaded by: " + ((LectureSlides) material).getUploader().getName());
    }
    private void printLectureRecording(LectureMaterial material){
        System.out.println("Title of video: " + ((LectureRecordings) material).getTitle());
        System.out.println("Video file: " + ((LectureRecordings) material).getFileName());
        System.out.println("Date of upload: " + ((LectureRecordings) material).getUploadDate());
        System.out.println("Uploaded by: " + ((LectureRecordings) material).getUploader().getName());
    }
    private void addLectureSlides(String slideTopic, ArrayList<String> slideContent, Date currentTime, instructor inst){
        lectureMaterialTracker.add(new LectureSlides(slideTopic, slideContent, currentTime, inst));
    }
    private void addLectureRecording(String videoTopic, String fileName, Date currentTime, instructor inst){
        int fileNameLength = fileName.length();
        if (fileNameLength < 4){
            System.out.println("Invalid file name");
        }
        else if (fileName.substring(fileNameLength-4, fileNameLength).equals(".mp4")){
            lectureMaterialTracker.add(new LectureRecordings(videoTopic, fileName, currentTime, inst));
        }
        else{
            System.out.println("Invalid File Format, Please only Upload Files with .mp4 Extension");
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
    public void addQuestion(String s);
    public void setMaxMarks(int maxMarks);
    public void setAssessmentID(int id);
}

class quiz implements assessments{
    private String quizQuestion;
    private int maxMarks;
    private int assessmentID;
    quiz(String quizQuestion, int assessmentID){
        addQuestion(quizQuestion);
        setMaxMarks(1);
        setAssessmentID(assessmentID);
    }
    @Override
    public void addQuestion(String quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    @Override
    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    @Override
    public void setAssessmentID(int id) {
        this.assessmentID = id;
    }

    public String getQuizQuestion() {
        return quizQuestion;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public int getAssessmentID() {
        return assessmentID;
    }
}

class assignment implements assessments{
    private String problemStatement;
    private int maxMarks;
    private int assessmentID;
    assignment(String problemStatement, int maxMarks, int assessmentID){
        addQuestion(problemStatement);
        setMaxMarks(maxMarks);
        setAssessmentID(assessmentID);
    }
    @Override
    public void addQuestion(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    @Override
    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    @Override
    public void setAssessmentID(int id) {
        this.assessmentID = id;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public int getAssessmentID() {
        return assessmentID;
    }
}

interface LectureMaterial{
    public void setTitle(String title);
    public void setTime(Date currentTime);
    public void setInstructor(instructor uploader);
}

class LectureSlides implements LectureMaterial{

    private String title;
    private ArrayList<String> slideContent;
    private Date uploadDate;
    private instructor uploader;

    LectureSlides(String title, ArrayList<String> slideContent, Date uploadDate, instructor uploader){
        setTitle(title);
        setContents(slideContent);
        setTime(uploadDate);
        setInstructor(uploader);
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(ArrayList<String> content) {
        this.slideContent = content;
    }

    @Override
    public void setTime(Date currentTime) {
        this.uploadDate = currentTime;
    }

    @Override
    public void setInstructor(instructor uploader) {
        this.uploader = uploader;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getSlideContent() {
        return slideContent;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public instructor getUploader() {
        return uploader;
    }
}

class LectureRecordings implements LectureMaterial{

    private String title;
    private String fileName;
    private Date uploadDate;
    private instructor uploader;

    public LectureRecordings(String videoTopic, String fileName, Date uploadDate, instructor uploader) {
        setTitle(videoTopic);
        setFileName(fileName);
        setTime(uploadDate);
        setInstructor(uploader);
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setTime(Date currentTime) {
        this.uploadDate = currentTime;
    }

    @Override
    public void setInstructor(instructor uploader) {
        this.uploader = uploader;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public instructor getUploader() {
        return uploader;
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

class comments{
    private user user;
    private Date uploadDate;
    private String comment;
    comments(user user, Date uploadDate, String comment){
        this.comment =comment;
        this.uploadDate = uploadDate;
        this.user = user;
    }

    public user getUser() {
        return user;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public String getComment() {
        return comment;
    }
}